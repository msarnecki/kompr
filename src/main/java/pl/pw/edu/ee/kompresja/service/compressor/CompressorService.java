package pl.pw.edu.ee.kompresja.service.compressor;

import pl.pw.edu.ee.kompresja.model.CompressInfoFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>  ,
 * @author msarneck@gmail.com
 */
public interface CompressorService {

    public CompressInfoFile compressWithLZ77(File fileToCompress) throws IOException;

    public CompressInfoFile decompressWithLZ7(File fileToDecompress) throws IOException;

    public CompressInfoFile compressWithLZW(File fileToCompress) throws IOException;

    public CompressInfoFile decompressWithLZW(File fileToDecompress) throws IOException;
}
