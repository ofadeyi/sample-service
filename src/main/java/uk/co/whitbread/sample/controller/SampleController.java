package uk.co.whitbread.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
@RequestMapping("/sample")
@RestController
public class SampleController {

    private static final Logger LOG = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping("/hello")
    public String hello(){

        LOG.debug(" DEBUG = Called /sample/hello endpoint");
        String aaa = null;
        return aaa.toString();
    }
}
