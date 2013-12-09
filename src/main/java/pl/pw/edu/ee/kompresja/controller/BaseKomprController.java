package pl.pw.edu.ee.kompresja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */
@Controller
public class BaseKomprController {

    @RequestMapping("/")
    String home() {
        return "index";
    }
}
