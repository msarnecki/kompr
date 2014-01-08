package pl.pw.edu.ee.kompresja.service.compress.lz77;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import pl.pw.edu.ee.kompresja.KomprApplication;

import java.io.*;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author msarnecki@gmail.com
 */

@Test
@ContextConfiguration(classes = KomprApplication.class)
public class LZ77ServiceImplTest extends AbstractTestNGSpringContextTests {

    @Value("classpath:simple")
    Resource simpleFile;

    @Value("classpath:polishChars")
    Resource fileWithPolishChars;

    @Value("classpath:empty.txt")
    Resource emptyFile;


    @Rule
    ExpectedException expectedException = ExpectedException.none();


    @Test
    public void shouldCompressAndDecompressSample() throws IOException {
        //given
        File file = simpleFile.getFile();
        String expected = getFileContentAsString(file);
        LZ77T lz77T = new LZ77T();

        //when
        File compressedFile = lz77T.compress(file);
        File decompressedFile = lz77T.decompress(compressedFile);
        String result = getFileContentAsString(decompressedFile);

        //then
        assertThat(expected, is(result));
    }

    @Test
    public void shouldCompressAndDecompressFileWithPolishChars() throws IOException {
        //given
        File file = fileWithPolishChars.getFile();
        String expected = getFileContentAsString(file);
        LZ77T lz77T = new LZ77T();

        //when
        File compressedFile = lz77T.compress(file);
        File decompressedFile = lz77T.decompress(compressedFile);
        String result = getFileContentAsString(decompressedFile);

        //then
        assertThat(expected, is(result));
    }

    @Test
    public void shouldCompressAndDecompressEmptyFile() throws IOException {
        //given
        File file = emptyFile.getFile();
        String expected = getFileContentAsString(file);
        LZ77T lz77T = new LZ77T();

        //when
        File compressedFile = lz77T.compress(file);
        File decompressedFile = lz77T.decompress(compressedFile);
        String result = getFileContentAsString(decompressedFile);

        //then
        assertThat(expected, is(result));
        assertThat(expected, isEmptyString());
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldThrowFileNotFoundException() throws IOException {
        //given
        LZ77T lz77T = new LZ77T();

        //when
        File resultCompressFile = lz77T.compress(new File("notExistingFile"));

        //then
    }

    private String getFileContentAsString(File file) throws IOException {
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }


}
