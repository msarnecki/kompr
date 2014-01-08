package pl.pw.edu.ee.kompresja.service.compress.lz77;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;
import pl.pw.edu.ee.kompresja.model.CompressInfoFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author msarnecki@gmail.com
 */

@Service("LZ77Service")
public class LZ77ServiceImpl implements LZ77Service {
    private static final Logger log = LoggerFactory.getLogger(LZ77ServiceImpl.class);

    @Override
    public List<Integer> compress(String uncompressedText) {
        return null;
    }

    @Override
    public List<Integer> compress(File fileToCompress) {
        return null;
    }

    @Override
    public CompressInfoFile compressFile(final File fileToCompress) throws IOException {
        return null;
    }

    @Override
    public CompressInfoFile decompressFile(final File fileToDecompress) throws IOException {
        return null;
    }


}
