package pl.pw.edu.ee.kompresja.service.compress.lzw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import pl.pw.edu.ee.kompresja.KomprApplication;

import java.io.File;
import java.util.List;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */

@Test
@ContextConfiguration(classes = KomprApplication.class)
public class LZWServiceFileTests extends AbstractTestNGSpringContextTests {
    @Autowired
    LZWService lzwService;

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldGetIllegalArgumentExceptionOnEmptyText() {
        List<Integer> compressResult = lzwService.compress(new File("im_not_exists.txt"));
    }

}
