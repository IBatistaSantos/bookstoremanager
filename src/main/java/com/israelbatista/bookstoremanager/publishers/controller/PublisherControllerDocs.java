package com.israelbatista.bookstoremanager.publishers.controller;


import com.israelbatista.bookstoremanager.publishers.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Publishers Management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Publisher creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success publisher creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or user already registered on system")
    })
    PublisherDTO create(PublisherDTO publisherDTO);

    @ApiOperation(value = "Find publisher by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success publisher found"),
            @ApiResponse(code = 404, message = "Publisher not found error")
    })
    PublisherDTO findById(@PathVariable Long id);

    @ApiOperation(value = "List all registered publishers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered publishers"),
    })
    List<PublisherDTO> findAll();
}