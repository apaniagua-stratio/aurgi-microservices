package com.stratio.microservice.ZipFileWatcher.service;

import com.stratio.microservice.ZipFileWatcher.service.model.ServiceInput;
import com.stratio.microservice.ZipFileWatcher.service.model.ServiceOutput;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardWatchEventKinds.*;

@Service
public class ServiceImpl implements com.stratio.microservice.ZipFileWatcher.service.Service{

  private boolean isRunning=false;
  private String testPath="/home/apaniagua/Documentos/watched";

  @Override
  public ServiceOutput doSomething(ServiceInput input) {

    return null;

  }

  @Override
  //public ServiceOutput getSomething(ServiceInput input) {
  public String getSomething(ServiceInput input) {

    //log.info("GET call received");

    return "Watcher running: " + isRunning;

  }

  public boolean watcherIsRunning() {
    return true;
  }

  @Async
  public void startWatcher()  {

    //log.info("STARTING WATCHER ON PATH: ");

    try {


      Path dir = Paths.get(testPath);

      new WatchDir(dir, false).processEvents();


    }

    catch (Exception e) {
      //log.error("file error");
      System.out.println(" ---> PATH ERROR");
    }
    //System.exit(0);

  }



}
