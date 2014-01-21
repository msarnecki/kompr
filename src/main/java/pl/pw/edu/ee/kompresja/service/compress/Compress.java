package pl.pw.edu.ee.kompresja.service.compress;

import pl.pw.edu.ee.kompresja.model.CompressInfoFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com> ,
 * @author msarneck@gmail.com
 */
public interface Compress {

    CompressInfoFile compressFile(File fileToCompress) throws IOException;

    CompressInfoFile decompressFile(File fileToDecompress) throws IOException;

}
