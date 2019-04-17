package com.stratio.microservice.motortownimport.service.mapper;

import com.stratio.microservice.motortownimport.generated.rest.model.MicroserviceRequest;
import com.stratio.microservice.motortownimport.service.model.ServiceInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceRequestMapper {

  ServiceInput mapInput(MicroserviceRequest request);
}
