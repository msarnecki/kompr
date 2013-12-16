package pl.pw.edu.ee.kompresja.service.compress;

import java.util.List;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */
public interface Compress {

    List<Integer> compress(String uncompressedText);

}
