package pl.pw.edu.ee.kompresja.service.compressor;

import org.springframework.stereotype.Component;
import pl.pw.edu.ee.kompresja.model.CompressInfoFile;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 * @author msarneck@gmail.com
 */
@Component("compressor")
public class CompressorServiceImpl implements CompressorService {
    @Override
    public CompressInfoFile compressWithLZMA() {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }

    @Override
    public CompressInfoFile compressWithLZW() {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }
}
