package uk.co.whitbread.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.whitbread.sample.TestConfiguration;
import uk.co.whitbread.sample.exception.AbstractMALException;
import uk.co.whitbread.sample.model.SampleResponse;
import uk.co.whitbread.sample.service.SampleService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
@ContextConfiguration(classes = TestConfiguration.class)
@WebMvcTest(SampleController.class)
@RunWith(SpringRunner.class)
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SampleService mockSampleService;

    @Test
    public void shouldGetOkResponse() throws Exception {
        //Given
        when(mockSampleService.getResponse(anyString(), anyString(), anyBoolean())).thenReturn(creteOkResponse());

        //When
        mockMvc.perform(get("/sample/endpoint")
                .param("sampleString", "test string")
                .param("sampleDate", "2016-11-20")
                .param("sampleBoolean", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(equalTo("OK"))))
                .andExpect(jsonPath("$.response", is(equalTo("test string - 2016-11-20 - true"))));
    }


    @Test
    public void shouldGetNokResponse() throws Exception {
        //Given
        when(mockSampleService.getResponse(anyString(), anyString(), anyBoolean()))
                .thenThrow(new AbstractMALException("This is an error message") {
                    @Override
                    public String getErrorCode() {
                        return "001";
                    }
                });

        //When
        mockMvc.perform(get("/sample/endpoint")
                .param("sampleString", "test string")
                .param("sampleDate", "2016-11-20")
                .param("sampleBoolean", "true"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(equalTo("001"))))
                .andExpect(jsonPath("$.details", hasItem("This is an error message")));
    }

    @Test
    public void shouldHandleInternalServerError() throws Exception {
        //Given
        when(mockSampleService.getResponse(anyString(), anyString(), anyBoolean()))
                .thenThrow(new RuntimeException("This is an internal error message"));

        //When
        mockMvc.perform(get("/sample/endpoint")
                .param("sampleString", "test string")
                .param("sampleDate", "2016-11-20")
                .param("sampleBoolean", "true"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code", is(equalTo("999"))))
                .andExpect(jsonPath("$.details", hasItem("This is an internal error message")));
    }

    @Test
    public void shouldHandleValidationError() throws Exception {
        //Given
        when(mockSampleService.getResponse(anyString(), anyString(), anyBoolean()))
                .thenThrow(new RuntimeException("This is an internal error message"));

        //When
        mockMvc.perform(get("/sample/endpoint")
                .param("sampleDate", "2016-NOV-20"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(equalTo("001"))))
                .andExpect(jsonPath("$.details", hasSize(3)))
                .andExpect(jsonPath("$.details", hasItem("sampleDate must be in correct date format")))
                .andExpect(jsonPath("$.details", hasItem("sampleBoolean may not be null")))
                .andExpect(jsonPath("$.details", hasItem("sampleString may not be empty")));
    }

    private SampleResponse creteOkResponse() {
        SampleResponse sampleResponse = new SampleResponse();
        sampleResponse.setStatus("OK");
        sampleResponse.setResponse("test string - 2016-11-20 - true");
        return sampleResponse;
    }
}