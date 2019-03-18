package com.stratio.microservice.ZipFileWatcher.service;

import com.stratio.microservice.ZipFileWatcher.service.model.ServiceInput;
import com.stratio.microservice.ZipFileWatcher.service.model.ServiceOutput;

public interface Service {

  ServiceOutput doSomething(ServiceInput input);

  String getSomething(ServiceInput input);

  void startWatcher();

  boolean watcherIsRunning();


}
