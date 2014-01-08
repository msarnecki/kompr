package pl.pw.edu.ee.kompresja.service.compressor;

import pl.pw.edu.ee.kompresja.model.CompressInfoFile;

import java.io.File;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>  ,
 * @author msarneck@gmail.com
 */
public interface CompressorService {

    public CompressInfoFile compressWithLZ77();

    public CompressInfoFile decompressWithLZ7();

    public CompressInfoFile compressWithLZW();

    public CompressInfoFile decompressWithLZW();
}
