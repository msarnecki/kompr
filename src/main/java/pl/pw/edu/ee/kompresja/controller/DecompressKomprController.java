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

@Controller
@RequestMapping("/decompress")
public class DecompressKomprController {
    private Logger log = LoggerFactory.getLogger(DecompressKomprController.class);

    @RequestMapping(method = RequestMethod.GET)
    String home() {
        return "decompress";
    }

    @RequestMapping(method = RequestMethod.POST)
    String postFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File uploadedFile = new File(fileName);
        file.transferTo(uploadedFile);

        return "decompress";
    }
}
