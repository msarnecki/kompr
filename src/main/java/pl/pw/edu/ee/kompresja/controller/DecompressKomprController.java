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

@Controller
@RequestMapping("/decompress")
public class DecompressKomprController {
    private Logger log = LoggerFactory.getLogger(DecompressKomprController.class);

    @Autowired
    private CompressorService compressorService;

    @RequestMapping(method = RequestMethod.GET)
    String home() {
        return "decompress";
    }

    @RequestMapping(method = RequestMethod.POST)
    String postFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String fileName = file.getOriginalFilename();
        File uploadedFile = new File(fileName);
        file.transferTo(uploadedFile);

        File fileToCompress = new File("uploaded/" + fileName);
        CompressInfoFile lz77CompressInfo = compressorService.decompressWithLZ7(fileToCompress);

        model.addAttribute("compressed", true);
        model.addAttribute("lz77", lz77CompressInfo);

        return "decompress";
    }
}
