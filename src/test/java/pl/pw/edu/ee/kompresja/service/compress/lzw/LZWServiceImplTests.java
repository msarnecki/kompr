package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
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
    @Autowired
    LZWService lzwService;

    @DataProvider(name = "text-provider")
    public Object[][] getSampleTexts() {
        return new Object[][] { { "TOBEORNOTTOBEORTOBEORNOT", Lists.newArrayList(84, 79, 66, 69, 79, 82, 78, 79, 84, 256, 258, 260, 265, 259, 261, 263) },
                                { "ABRACADABRA!", Lists.newArrayList(65, 66, 82, 65, 67, 65, 68, 256, 258, 33) },
                                { "Kompresja jest Spoko", Lists.newArrayList(75, 111, 109, 112, 114, 101, 115, 106, 97, 32, 106, 261, 116, 32, 83, 112, 111, 107, 111) } };
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldGetIllegalArgumentExceptionOnNullText() {
        List<Integer> compressResult = lzwService.compress(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldGetIllegalArgumentExceptionOnEmptyText() {
        List<Integer> compressResult = lzwService.compress("");
    }

    @Test(dataProvider = "text-provider")
    public void shouldFailCompress(String text, List<Integer> expectedResultList) throws Exception {
        List<Integer> compressResult = lzwService.compress(text + "#");

        assertNotNull(compressResult);
        assertNotEquals(compressResult, expectedResultList);
        assertNotSame(compressResult, expectedResultList);
    }

    @Test(dataProvider = "text-provider")
    public void shouldCompressTextsFromDataProvider(String text, List<Integer> expectedResultList) {
        List<Integer> compressResult = lzwService.compress(text);
        System.out.println("compressResult = " + compressResult);

        assertNotNull(compressResult);
        assertEquals(compressResult, expectedResultList);
    }

}
