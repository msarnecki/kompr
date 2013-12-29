package pl.pw.edu.ee.kompresja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class KomprApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(KomprApplication.class, args);
    }

    @Bean(name = "multipartConfigElement")
    MultipartConfigElement multipartConfigElement() throws IOException {

        Path uploadedDir = Paths.get("uploaded/");
        cleanDirectory(uploadedDir);
        Path directories = Files.createDirectories(Paths.get("uploaded/"));

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(directories.toAbsolutePath().toString());

        return multipartConfigElement;
    }

    private void cleanDirectory(Path uploadedDir) {
        if(!uploadedDir.toFile().exists())
            return;

        for (File file : uploadedDir.toFile().listFiles()) {
            file.delete();
        }
    }
}