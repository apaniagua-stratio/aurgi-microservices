package com.stratio.microservice.motortownimport.service;

import com.stratio.microservice.motortownimport.service.model.Productcsv;
import com.stratio.microservice.motortownimport.service.model.ServiceInput;
import com.stratio.microservice.motortownimport.service.model.ServiceOutput;

import java.util.List;

public interface Service {

  ServiceOutput doSomething(ServiceInput input);

  Productcsv[] getCsvExample();

  List<Productcsv> getMotortownCsv(String filepath);



}
