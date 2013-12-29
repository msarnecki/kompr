package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.pw.edu.ee.kompresja.KomprApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldGetIllegalArgumentExceptionOnEmptyText() {
        List<Integer> compressResult = lzwService.compress(new File("im_not_exists.txt"));
    }

    @Test
    public void shouldCompresTestLZWFile() throws Exception {
        // given
        File testFile = testLzw.getFile();
        ArrayList<Integer> expected = Lists.newArrayList(75, 111, 109, 112, 114, 101, 115, 106, 97, 32, 106, 261, 116, 32, 83, 112, 111, 107, 111);

        // when
        List<Integer> compressResult = lzwService.compress(testFile);

        // then
        Assert.assertNotNull(compressResult);
        Assert.assertEquals(compressResult, expected);
    }
}
