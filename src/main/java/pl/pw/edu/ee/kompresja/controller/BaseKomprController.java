package pl.pw.edu.ee.kompresja.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */
@Controller
public class BaseKomprController {
    private Logger log = LoggerFactory.getLogger(BaseKomprController.class);

    @RequestMapping("/")
    String home() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    String postFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File uploadedFile = new File(fileName);
        file.transferTo(uploadedFile);

        return "index";
    }
}
