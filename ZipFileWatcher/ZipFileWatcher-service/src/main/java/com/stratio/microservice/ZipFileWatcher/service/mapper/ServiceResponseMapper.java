package com.stratio.microservice.ZipFileWatcher.service.mapper;

import com.stratio.microservice.ZipFileWatcher.generated.rest.model.MicroserviceResponse;
import com.stratio.microservice.ZipFileWatcher.service.model.ServiceOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceResponseMapper {

  MicroserviceResponse mapOutput(ServiceOutput output);
}
