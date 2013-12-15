package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.pw.edu.ee.kompresja.KomprApplication;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */

@Test
@ContextConfiguration(classes = KomprApplication.class)
public class LZWServiceImplTests extends AbstractTestNGSpringContextTests {
    private static String TO_BE_TEXT;
    private static List<Integer> COMPRESSED_TEXT;

    @Autowired
    LZWService lzwService;

    @BeforeMethod
    public void setUp() throws Exception {
        TO_BE_TEXT = "TOBEORNOTTOBEORTOBEORNOT";
        COMPRESSED_TEXT = Lists.newArrayList(84, 79, 66, 69, 79, 82, 78, 79, 84, 256, 258, 260, 265, 259, 261, 263);

    }

    public void shouldCompressToBeText() throws Exception {
        List<Integer> compressResult = lzwService.compress(TO_BE_TEXT);

        assertNotNull(compressResult);
        assertEquals(compressResult, COMPRESSED_TEXT);
    }
    public void shouldFailCompressToBeText() throws Exception {
        List<Integer> compressResult = lzwService.compress(TO_BE_TEXT+"#");

        assertNotNull(compressResult);
        assertNotEquals(compressResult, COMPRESSED_TEXT);
        assertNotSame(compressResult, COMPRESSED_TEXT);
    }
}
