package com.stratio.microservice.ZipFileWatcher.service;

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Slf4j
public class WatchDir {

    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private final boolean recursive;
    private boolean trace = false;

    private String[] extensionesAceptadas = {"zip"};
    private final String unzipPath;
    private String filesRead ="";

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Creates a WatchService and registers the given directory
     */
    WatchDir(Path dir, boolean recursive, String outputDir) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        this.recursive = recursive;
        this.unzipPath=outputDir;

        if (recursive) {
            //System.out.format("Scanning %s ...\n", dir);
            log.info("FILEWATCHER Scanning %s ...\n", dir);
            registerAll(dir);
            //System.out.println("Done.");
            log.info("FILEWATCHER Scanning. Done.");
        } else {
            register(dir);
        }

        // enable trace after initial registration
        this.trace = true;
    }

    /**
     * Process all events for keys queued to the watcher
     */


    @Async("threadPoolExecutor")
    public void processEvents() throws InterruptedException {
        for (;;) {

            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                //System.err.println("WatchKey not recognized!!");
                log.info("FILEWATCHER WatchKey not recognized!!");
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);



                // print out event
                log.info(event.kind().name() + " " +  child);
                //System.out.format("%s: %s\n", event.kind().name(), child);
                Date date = new Date();

                if (kind==ENTRY_CREATE && !Files.isDirectory(child)) {


                    filesRead += "{\"Name\":\"" + child.getFileName();

                    if (Arrays.stream(extensionesAceptadas).anyMatch(FilenameUtils.getExtension(child.getFileName().toString())::equals)) {

                        //System.out.println("New " + extensionesAceptadas[0].toString() + " File:" + child.getFileName().toString());
                        log.info("ENTRY Discovered new " + extensionesAceptadas[0].toString() + " file:" + child.getFileName().toString());

                        try {

                            //wait a bit
                            Thread.sleep(5000);
                            String result=unzip(child.toString(),unzipPath);

                            filesRead += "\",\"Status\": \"" + result + "\",\"timestamp\":" + "\"" + date.getTime() + "\"};";

                        }
                        catch (IOException e) {
                            filesRead += "\",\"Status\": \"KO\"" + "\",\"timestamp\":" + "\"" + date.getTime() + "\"};";
                            //System.out.println(e.getMessage());
                            log.error(e.getMessage());
                            e.printStackTrace();

                        }


                    }
                    else {
                        filesRead += "\",\"Status\": \"NA\"};";

                    }


                }

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

    String unzip(final String zipFilePath, final String unzipLocation) throws IOException {



        if (!(zipIsValid(new File(zipFilePath)))) {
            //System.out.format(zipFilePath + " is a bad zip file \n");
            log.info("ENTRY " + zipFilePath + " is a bad zip file \n");

            return "KO";
        }

        if (!(Files.exists(Paths.get(unzipLocation)))) {
            Files.createDirectories(Paths.get(unzipLocation));
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {


            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                Path filePath = Paths.get(unzipLocation, entry.getName());
                if (!entry.isDirectory()) {
                    unzipFiles(zipInputStream, filePath);
                } else {
                    Files.createDirectories(filePath);
                }

                zipInputStream.closeEntry();

                log.info("ENTRY File " + entry.getName() + " unzipped.");
                entry = zipInputStream.getNextEntry();
            }
        }
        return "OK";
    }

    void unzipFiles(final ZipInputStream zipInputStream, final Path unzipFilePath) throws IOException {

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
            byte[] bytesIn = new byte[1024];
            int read = 0;
            while ((read = zipInputStream.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }

        }

    }

    boolean zipIsValid(final File file)  {


        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(file);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (zipfile != null) {
                    zipfile.close();
                    zipfile = null;
                }
            } catch (IOException e) {
            }
        }

    }

    public String getFilesRead() {
        return filesRead;
    }

    public String retryFile(String filename) throws IOException {

        return unzip(filename.toString(),unzipPath);



    }

    /*
    //uses hadoop imports, better to check api if available

    public static void putToHDFS() {

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path localPath = new Path("path/to/local/file");
        Path hdfsPath = new Path("/path/in/hdfs");
        fs.copyFromLocalFile(localPath, hdfsPath);

    }

    public static void main(String[] args) throws IOException {
        // parse arguments
        if (args.length == 0 || args.length > 2)
            usage();
        boolean recursive = false;
        int dirArg = 0;
        if (args[0].equals("-r")) {
            if (args.length < 2)
                usage();
            recursive = true;
            dirArg++;
        }

        // register directory and process its events
        Path dir = Paths.get(args[dirArg]);
        new WatchDir(dir, recursive).processEvents();
    }
    */

}