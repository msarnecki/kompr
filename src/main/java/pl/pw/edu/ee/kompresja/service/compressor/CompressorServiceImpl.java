package pl.pw.edu.ee.kompresja.service.compressor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.pw.edu.ee.kompresja.model.CompressInfoFile;
import pl.pw.edu.ee.kompresja.service.compress.lz77.LZ77Service;
import pl.pw.edu.ee.kompresja.service.compress.lzw.LZWService;

import java.io.File;
import java.io.IOException;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 * @author msarneck@gmail.com
 */
@Service("compressor")
@Scope("prototype")
public class CompressorServiceImpl implements CompressorService {
    private Logger log = LoggerFactory.getLogger(CompressorServiceImpl.class);

    @Autowired
    private LZWService lzwService;

    @Autowired
    private LZ77Service lz77Service;


    @Override
    public CompressInfoFile compressWithLZ77(File fileToCompress) throws IOException {
        return lz77Service.compressFile(fileToCompress);
    }

    @Override
    public CompressInfoFile decompressWithLZ7() {
        return null;
    }

    @Override
    public CompressInfoFile compressWithLZW(File fileToCompress) throws IOException {
        return lzwService.compressFile(fileToCompress);
    }

    @Override
    public CompressInfoFile decompressWithLZW() {
        return null;
    }
}
