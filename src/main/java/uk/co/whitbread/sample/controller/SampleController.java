package uk.co.whitbread.sample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
@RequestMapping("/sample")
@RestController
public class SampleController {

    private static final Logger LOG = LogManager.getLogger(SampleController.class);

    @RequestMapping("/hello")
    public String hello(){
        LOG.debug(" DEBUG = Called /sample/hello endpoint");
        return "Hello";
    }
}
