package pl.pw.edu.ee.kompresja.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.pw.edu.ee.kompresja.model.CompressInfoFile;
import pl.pw.edu.ee.kompresja.service.compressor.CompressorService;

import java.io.File;
import java.io.IOException;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */
@Controller
public class BaseKomprController {
    private Logger log = LoggerFactory.getLogger(BaseKomprController.class);

    @Autowired
    private CompressorService compressorService;

    @RequestMapping("/")
    String home() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    String postFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String fileName = file.getOriginalFilename();

        // its fuckin magic - dont know what this is doing  but it works :D
        File uploadedFile = new File(fileName);
        file.transferTo(uploadedFile);

        File fileToCompress = new File("uploaded/" + fileName);
        CompressInfoFile lz77CompressInfo = compressorService.compressWithLZ77(fileToCompress);
        CompressInfoFile lzwCompressInfo = compressorService.compressWithLZW(fileToCompress);

        model.addAttribute("compressed", true);

        model.addAttribute("lz77", lz77CompressInfo);
        model.addAttribute("lzw", lzwCompressInfo);


        return "index";
    }
}
