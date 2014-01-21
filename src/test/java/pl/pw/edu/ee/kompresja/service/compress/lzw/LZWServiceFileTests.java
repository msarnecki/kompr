package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import pl.pw.edu.ee.kompresja.KomprApplication;
import pl.pw.edu.ee.kompresja.model.CompressInfoFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */

@Test
@ContextConfiguration(classes = KomprApplication.class)
public class LZWServiceFileTests extends AbstractTestNGSpringContextTests {
    @Autowired
    LZWService lzwService;

    @Value("classpath:test_lzw.txt")
    Resource testLzw;

    @Value("classpath:empty.txt")
    Resource empty;

    @Value("classpath:pan-tadeusz-czyli-ostatni-zajazd-na-litwie.txt")
    Resource pantadeusz;

    @Value("classpath:progit.en.pdf")
    Resource gitPdf;

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldGetFileNotFoundExceptionOnEmptyText() throws IOException {
        CompressInfoFile compressInfoFile = lzwService.compressFile(new File("im_not_exists.txt"));
    }

    @Test
    public void shouldCompressTestLZWFile() throws IOException {
        // given
        File testFile = testLzw.getFile();
        List<Integer> expected = Lists.newArrayList(77, 121, 347, 108, 97, 322, 101, 109, 44, 32, 380, 101, 32, 100, 122, 105, 97,322, 97);

        // when
        CompressInfoFile compressInfoFile = lzwService.compressFile(testFile);

        // then
        assertNotNull(compressInfoFile);
        assertNotNull(compressInfoFile.getCompressedResult());
        for (Integer compressed : compressInfoFile.getCompressedResult()) {
            assertNotNull(compressed);
        }
        assertEquals(compressInfoFile.getCompressedResult(), expected);
    }

    @Test
    public void shouldCompressTestEmptyFile() throws IOException {
        // given
        File emptyFile = empty.getFile();
        ArrayList<Integer> expected = Lists.newArrayList();

        // when
        CompressInfoFile compressInfoFile = lzwService.compressFile(emptyFile);

        // then
        assertNotNull(compressInfoFile);
        assertNotNull(compressInfoFile.getCompressedResult());
        assertEquals(compressInfoFile.getCompressedResult(), expected);
    }

    @Test(timeOut = 30000)
    public void shouldCompressPanTadeuszTxt() throws IOException {
        // given
        File pantadeuszFile = pantadeusz.getFile();

        // when
        CompressInfoFile compressInfoFile = lzwService.compressFile(pantadeuszFile);

        // then
        assertNotNull(compressInfoFile);
        assertNotNull(compressInfoFile.getCompressedResult());
        for (Integer compressed : compressInfoFile.getCompressedResult()) {
            assertNotNull(compressed);
        }
    }

    @Test(timeOut = 60000)
    public void shouldCompressGitPdf() throws Exception {
        // given
        File gitPdfFole = gitPdf.getFile();

        // when
        CompressInfoFile compressInfoFile = lzwService.compressFile(gitPdfFole);

        // then
        assertNotNull(compressInfoFile);
        assertNotNull(compressInfoFile.getCompressedResult());
        for (Integer compressed : compressInfoFile.getCompressedResult()) {
            assertNotNull(compressed);
        }
    }
}
