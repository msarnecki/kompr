package pl.pw.edu.ee.kompresja.service.compressor;

import pl.pw.edu.ee.kompresja.model.CompressInfoFile;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */
public interface CompressorService {

    public CompressInfoFile compressWithLZMA();

    public CompressInfoFile compressWithLZW();

}
