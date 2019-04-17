package com.stratio.microservice.ZipFileWatcher.service;

import com.stratio.microservice.ZipFileWatcher.service.model.ServiceInput;
import com.stratio.microservice.ZipFileWatcher.service.model.ServiceOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.io.FileUtils;


import static java.nio.file.StandardWatchEventKinds.*;

@Service
@Slf4j

public class ServiceImpl implements com.stratio.microservice.ZipFileWatcher.service.Service{

  private boolean isRunning=false;

  @Value("${filespath}")
  private String testPath;
  @Value("${outpath}")
  private String outPath;


  @Value("${ftphost}")
  private String ftpHost;
  @Value("${ftpUser}")
  private String ftpUser;
  @Value("${ftpPass}")
  private String ftpPass;
  @Value("${ftpPath}")
  private String ftpPath;

  private WatchDir  watch;




  @Override
  public ServiceOutput doSomething(ServiceInput input) {


    log.info("AURGI GET CALL RECEIVED" );

    try {
      watch.retryFile("example");

    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;


  }

  @Override
  //public ServiceOutput getSomething(ServiceInput input) {
  public String getSomething(ServiceInput input) {

    log.info("AURGI GET CALL RECEIVED" );

    return watch.getFilesRead();


  }

  public boolean watcherIsRunning() {
    return true;
  }



  @Async
  public void startWatcher()  {

    try {


      //Path dir = Paths.get(testPath);
      //log.info("AURGI FILEWATCHER STARTING ON PATH: " + dir);
      //log.info("AURGI FILEWATCHER OUTPUT PATH WILL BE: " + outPath);

      //just list ftp files


      //String server="ftps.anjana.local";
      //int port=21;
      //String ftpFilePath="/anjana";
      //String ftpFilter="";
      //String user="motortown_watcher";
      //String pass="Q_7C<p2wGnh4_e{D";

      log.info("AURGI FTP TRY CONNECT ");

      //FTPSClient ftpClient = new FTPSClient(false);
      FTPClient ftpClient = new FTPClient();

      ftpClient.connect(ftpHost);
      ftpClient.login(ftpUser, ftpPass);

      //ftpClient.enterLocalPassiveMode();

      log.info("AURGI PWD: " + ftpClient.printWorkingDirectory());
      ftpClient.cwd(ftpPath + "/test2");
      log.info("AURGI CWD: " + ftpClient.printWorkingDirectory());

      ftpClient.cwd(ftpPath);
      log.info("AURGI PWD: " + ftpClient.printWorkingDirectory());

      log.info("AURGI FILES ON PATH: ");
      String[] ficheros=ftpClient.listNames();
      log.info("AURGI FILES: " + ficheros.length);

      //FTPFile[] files = ftpClient.listFiles(ftpPath);
      //log.info("AURGI FTPWATCHER FILES IN PATH: " + ftpClient.printWorkingDirectory() + " : " + files.length);

      /* ------------------


      ftpClient.setControlEncoding("UTF-8");
      ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
      String remoteFileName = "/anjana/2019_04_03_00_27.zip";//this file in the rootdir
      FileOutputStream fos = new FileOutputStream("/tmp/bulshit.zip");
      ftpClient.setBufferSize(1024);
      //ftpClient.enterLocalPassiveMode();
      ftpClient.enterLocalActiveMode();
      ftpClient.retrieveFile(remoteFileName, fos);
      System.out.println("retrieveFile?"+ftpClient.getReplyCode());
      log.info("AURGI FILEWATCHER retrieveFile?" + ftpClient.getReplyCode());
      fos.close();
      ftpClient.logout();
      ftpClient.disconnect();

    -------------- */


      /*
      FTPSClient ftpClient = new FTPSClient(false);
      ftpClient.connect(server);

      log.info("AURGI FTPWATCHER CONNECT");

      ftpClient.login(user, pass);
      */

      //log.info("AURGI FTPWATCHER AUTH: " + ftpClient.getNeedClientAuth() + ftpClient.getAuthValue());



      // lists files and directories in the current working directory

      //ftpClient.enterLocalActiveMode();
      //FTPFile[] files = ftpClient.listFiles(ftpFilePath);
      //log.info("AURGI FTPWATCHER FILES IN PATH: " + ftpClient.printWorkingDirectory() + " : " + files.length);



// iterates over the files and prints details for each

      FTPFile[] files = ftpClient.listFiles(ftpPath);
      DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      for (FTPFile file : files) {

        log.info("AURGI FTPWATCHER READING : ");

        String details = file.getName();
        if (file.isDirectory()) {
          details = "[" + details + "]";
        }
        details += "\t\t" + file.getSize();
        details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());

        //System.out.println(details);
        log.info("AURGI FTPWATCHER FILE DETAILS: " + details);

      }

      ftpClient.logout();
      ftpClient.disconnect();


      //just list files

      /*
      try
      {
        DirectoryStream<Path> stream;
        stream = Files.newDirectoryStream(dir);
        for (Path entry : stream)
        {
          log.info("AURGI FILE: " + entry.getFileName());
        }
        stream.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      */


      //start a listerner on path
      /*
      watch=new WatchDir(dir, false,outPath);
      watch.processEvents();

       */


    }

    catch (Exception e) {
      log.error("AURGI FILE ERROR " +  e.getMessage());
      e.printStackTrace();
    }
    //System.exit(0);

  }



}
