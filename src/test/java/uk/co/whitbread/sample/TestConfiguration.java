package uk.co.whitbread.sample;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
@SpringBootApplication
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        value = {
                                SampleServiceApplication.class
                        })
        })
public class TestConfiguration {
}
