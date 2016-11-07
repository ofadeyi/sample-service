package uk.co.whitbread.sample.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Oleksandr Murha on 04/11/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DateFormatValidatorTest {

    @Mock
    private DateFormat mockDateFormatAnnotation;

    private DateFormatValidator sut;

    @Before
    public void setUp() throws Exception {
        when(mockDateFormatAnnotation.value()).thenReturn("yyyy-MM-dd");
        sut = new DateFormatValidator();
        sut.initialize(mockDateFormatAnnotation);
    }

    @Test
    public void shouldAcceptDateInCorrectFormat() throws Exception {
        //Given
        String date = "2016-10-22";

        //When
        boolean valid = sut.isValid(date, null);

        //Then
        assertThat(valid, is(true));
    }

    @Test
    public void shouldFailValidationOfDateInWrongFormat() throws Exception {
        //Given
        String date = "2016-aa-22";

        //When
        boolean valid = sut.isValid(date, null);

        //Then
        assertThat(valid, is(false));
    }

    @Test
    public void shouldIgnoreNullValue() throws Exception {
        //Given
        String date = null;

        //When
        boolean valid = sut.isValid(date, null);

        //Then
        assertThat(valid, is(true));
    }
}