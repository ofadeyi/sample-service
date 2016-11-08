package uk.co.whitbread.sample.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.whitbread.sample.model.ErrorResponse;
import uk.co.whitbread.sample.model.SampleRequest;
import uk.co.whitbread.sample.model.SampleResponse;
import uk.co.whitbread.sample.service.SampleService;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
@RequestMapping("/sample")
@RestController
@Api(tags = "Sample Controller", description = "Sample operations")
public class SampleController {

    private static final Logger LOG = LoggerFactory.getLogger(SampleController.class);

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @ApiOperation(value = "getResponse", notes = "Retrieves Sample Response")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SampleResponse.class),
            @ApiResponse(code = 400, message = "Error Occurred ", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Resource Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sampleString", value = "Sample String", dataType = "string",
                    required = true, defaultValue = "sample", paramType = "query"),
            @ApiImplicitParam(name = "sampleDate", value = "Sample Date in format yyyy-mm-dd",
                    required = true, dataType = "string", paramType = "query", defaultValue="2017-02-20"),
            @ApiImplicitParam(name = "sampleBoolean", value = "Sample Boolean",
                    required = true, dataType = "boolean", paramType = "query", defaultValue="true")
    })
    @RequestMapping(value = "/endpoint", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SampleResponse getResponse(@Validated SampleRequest sampleRequest){
        LOG.debug("Called /sample/endpoint with " + sampleRequest.toString());
        return sampleService.getResponse(sampleRequest.getSampleString(), sampleRequest.getSampleDate(),
                sampleRequest.getSampleBoolean());
    }
}
