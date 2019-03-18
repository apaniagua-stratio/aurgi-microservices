package com.stratio.microservice.ZipFileWatcher.service.mapper;

import com.stratio.microservice.ZipFileWatcher.generated.rest.model.MicroserviceRequest;
import com.stratio.microservice.ZipFileWatcher.service.model.ServiceInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceRequestMapper {

  ServiceInput mapInput(MicroserviceRequest request);
}
