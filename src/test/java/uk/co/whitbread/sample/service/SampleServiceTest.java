package uk.co.whitbread.sample.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.co.whitbread.sample.model.SampleResponse;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
public class SampleServiceTest {

    private SampleService sut;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        sut = new SampleService();
    }

    @Test
    public void shoulReturnOkResponse() throws Exception {
        //Given
        String sampleString = "Sample String";
        String sampleDate = "2017-11-20";
        boolean sampleBoolean = true;

        //When
        SampleResponse response = sut.getResponse(sampleString, sampleDate, sampleBoolean);

        //Then
        assertThat(response, is(not(nullValue())));
        assertThat(response.getStatus(), is(equalTo("OK")));
        assertThat(response.getResponse(), is(equalTo("Sample String - 2017-11-20 - true")));
    }

    @Test
    public void shouldReturnNokResponse() throws Exception {
        //Given
        String sampleString = "Sample String";
        String sampleDate = "2017-11-20";
        boolean sampleBoolean = false;

        //When
        SampleResponse response = sut.getResponse(sampleString, sampleDate, sampleBoolean);

        //Then
        assertThat(response, is(not(nullValue())));
        assertThat(response.getStatus(), is(equalTo("NOK")));
        assertThat(response.getResponse(), is(equalTo("Sample Error Message")));
    }

    @Test
    public void shouldThrowException() throws Exception {
        //Given
        String sampleString = "exception";
        String sampleDate = "2017-11-20";
        boolean sampleBoolean = true;

        //Then
        exception.expect(RuntimeException.class);
        exception.expectMessage("Sample Client Exception");

        // When
        sut.getResponse(sampleString, sampleDate, sampleBoolean);
    }
}
