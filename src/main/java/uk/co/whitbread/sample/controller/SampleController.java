package uk.co.whitbread.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.whitbread.sample.service.SampleService;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
@RequestMapping("/sample")
@RestController
public class SampleController {

    private static final Logger LOG = LoggerFactory.getLogger(SampleController.class);

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @RequestMapping("/hello")
    public String hello(){

        LOG.debug(" DEBUG = Called /sample/hello endpoint");
        String aaa = null;
        return aaa.toString();
    }
}
