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
import uk.co.whitbread.sample.model.SampleResponse;
import uk.co.whitbread.sample.service.SampleService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
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
                .param("sampleString", "test strin")
                .param("sampleDate", "2016-11-20")
                .param("sampleBoolean", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(equalTo("OK"))))
                .andExpect(jsonPath("$.response", is(equalTo("test string - 2016-11-20 - true"))));
    }

    private SampleResponse creteOkResponse() {
        SampleResponse sampleResponse = new SampleResponse();
        sampleResponse.setStatus("OK");
        sampleResponse.setResponse("test string - 2016-11-20 - true");
        return sampleResponse;
    }
}