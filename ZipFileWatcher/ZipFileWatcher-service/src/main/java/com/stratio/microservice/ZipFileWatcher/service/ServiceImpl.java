package com.stratio.microservice.ZipFileWatcher.service;

import com.stratio.microservice.ZipFileWatcher.service.model.ServiceInput;
import com.stratio.microservice.ZipFileWatcher.service.model.ServiceOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardWatchEventKinds.*;

@Service
@Slf4j

public class ServiceImpl implements com.stratio.microservice.ZipFileWatcher.service.Service{

  private boolean isRunning=false;
  private String testPath="/home/apaniagua/Documentos/watched";
  private String outPath="/home/apaniagua/Documentos/watched/unzipped";
  private WatchDir  watch;

  @Override
  public ServiceOutput doSomething(ServiceInput input) {


    log.info("GET CALL RECEIVED" );

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

    log.info("GET CALL RECEIVED" );

    return watch.getFilesRead();


  }

  public boolean watcherIsRunning() {
    return true;
  }

  @Async
  public void startWatcher()  {


    try {


      Path dir = Paths.get(testPath);
      log.info("FILEWATCHER STARTING ON PATH: " + dir);
      log.info("FILEWATCHER OUTPUT PATH WILL BE: " + outPath);


      watch=new WatchDir(dir, false,outPath);
      watch.processEvents();


    }

    catch (Exception e) {
      log.error("File error " +  e.getMessage());
      e.printStackTrace();;
    }
    //System.exit(0);

  }



}
