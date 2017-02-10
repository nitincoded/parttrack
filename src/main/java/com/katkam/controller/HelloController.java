package com.katkam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// import org.springframework.web.servlet.mvc.AbstractController;
// import org.springframework.validation.BindingResult;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//For JSON. See:  https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
//Also see:  https://spring.io/guides/tutorials/bookmarks/

/**
 * Created by Developer on 12/23/16.
 */
//@RestController
@Controller
public class HelloController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping({"/", "/home"})
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String hello() throws Exception {
        log.info("hello");
        return new ObjectMapper().writeValueAsString("hello");
    }

    @RequestMapping(value = "/hi", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    //in the param, set required=true
    public String hi(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format("[\"hi %s\"]", name);
    }
}
