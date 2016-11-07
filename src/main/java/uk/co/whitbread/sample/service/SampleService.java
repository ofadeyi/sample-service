package uk.co.whitbread.sample.service;

import uk.co.whitbread.sample.model.SampleResponse;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
public class SampleService {

    public SampleResponse getResponse(String sampleString, String sampleDate, boolean sampleBoolean) {
        if (sampleString.equals("exception")) throw new RuntimeException("Sample Client Exception");
        return sampleBoolean ? createOkResponse(sampleString, sampleDate, sampleBoolean) : createNokResponse();
    }

    private SampleResponse createOkResponse(String sampleString, String sampleDate, boolean sampleBoolean){
        SampleResponse sampleResponse = new SampleResponse();
        sampleResponse.setStatus("OK");
        sampleResponse.setResponse(
                Stream.of(sampleString, sampleDate, Boolean.toString(sampleBoolean)).collect(joining(" - "))
        );
        return sampleResponse;
    }

    private SampleResponse createNokResponse(){
        SampleResponse sampleResponse = new SampleResponse();
        sampleResponse.setStatus("NOK");
        sampleResponse.setResponse("Sample Error Message");
        return sampleResponse;
    }
}
