package pl.pw.edu.ee.kompresja.service.compress.lzma;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author msarnecki@gmail.com
 */

@Service("LZMAService")
public class LZMAServiceImpl implements LZMAService {

    @Override
    public List<Integer> compress(String uncompressedText) {
        return null;
    }

    @Override
    public List<Integer> compress(File fileToCompress) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
