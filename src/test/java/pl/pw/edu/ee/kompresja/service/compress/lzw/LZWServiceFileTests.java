package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import pl.pw.edu.ee.kompresja.KomprApplication;

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

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldGetFileNotFoundExceptionOnEmptyText() throws IOException {
        List<Integer> compressResult = lzwService.compress(new File("im_not_exists.txt"));
    }

    @Test
    public void shouldCompresTestLZWFile() throws IOException {
        // given
        File testFile = testLzw.getFile();
        List<Integer> expected = Lists.newArrayList(77, 121, 347, 108, 97, 322, 101, 109, 44, 32, 380, 101, 32, 100, 122, 105, 1028, 97);

        // when
        List<Integer> compressResult = lzwService.compress(testFile);

        // then
        assertNotNull(compressResult);
        for (Integer compressed : compressResult) {
            assertNotNull(compressed);
        }
        assertEquals(compressResult, expected);
    }

    @Test
    public void shouldCompresTestEmptyFile() throws IOException {
        // given
        File emptyFile = empty.getFile();
        ArrayList<Integer> expected = Lists.newArrayList();

        // when
        List<Integer> compressResult = lzwService.compress(emptyFile);

        // then
        assertNotNull(compressResult);
        assertEquals(compressResult, expected);
    }

    @Test
    public void shouldCompresPanTadeusz() throws IOException {
        // given
        File pantadeuszFile = pantadeusz.getFile();

        // when
        List<Integer> compressResult = lzwService.compress(pantadeuszFile);
        // then
        assertNotNull(compressResult);
        for (Integer compressed : compressResult) {
            assertNotNull(compressed, compressResult.toString());
        }
        assertEquals(compressResult.size(), 109185);
    }
}
