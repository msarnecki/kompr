package pl.pw.edu.ee.kompresja.service.compress;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */
public interface Compress {

    List<Integer> compress(String uncompressedText);

    List<Integer> compress(File fileToCompress) throws IOException;




}
