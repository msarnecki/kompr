package pl.pw.edu.ee.kompresja.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */
@Controller
public class BaseKomprController {
    private Logger log = LoggerFactory.getLogger(BaseKomprController.class);

    @RequestMapping("/")
    String home() {
        log.info("asd");
        return "index";
    }
}
