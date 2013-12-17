package pl.pw.edu.ee.kompresja.service.compress.lzma;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.stereotype.Service;

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
}
