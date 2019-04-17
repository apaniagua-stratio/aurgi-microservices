package com.stratio.microservice.motortownimport.service.mapper;

import com.stratio.microservice.motortownimport.generated.rest.model.MicroserviceResponse;
import com.stratio.microservice.motortownimport.service.model.ServiceOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceResponseMapper {

  MicroserviceResponse mapOutput(ServiceOutput output);
}
