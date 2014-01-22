package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pw.edu.ee.kompresja.model.CompressInfo;
import pl.pw.edu.ee.kompresja.model.CompressInfoFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */

@Service("lzwService")
public class LZWServiceImpl implements LZWService {
    private Logger log = LoggerFactory.getLogger(LZWServiceImpl.class);
    public static final int START_DICT_SIZE = 65536;

    private Map<String, Integer> dictionary = Maps.newTreeMap();

    @Override
    public CompressInfoFile compressFile(File fileToCompress) throws IOException {
        List<Integer> compressResult = compress(fileToCompress);
        CompressInfo compressInfo = new CompressInfo();

        compressInfo.setSizeAfter(compressResult.size());
        compressInfo.setSizeBefore(fileToCompress.length());

        BigDecimal sizeBefore = new BigDecimal(compressInfo.getSizeBefore());
        BigDecimal sizeAfter = new BigDecimal(compressInfo.getSizeAfter() * 100);
        if (!sizeBefore.equals(BigDecimal.ZERO)) {
            BigDecimal ratio = sizeAfter.divide(sizeBefore, 2, RoundingMode.HALF_UP);

            compressInfo.setCompressRatio(ratio.toString());
        } else {
            compressInfo.setCompressRatio("0");
        }

        CompressInfoFile compressInfoFile = new CompressInfoFile(compressInfo, null);
        compressInfoFile.setCompressInfo(compressInfo);
        compressInfoFile.setCompressedResult(compressResult);


        return compressInfoFile;
    }

    public List<Integer> compress(File fileToCompress) throws IOException {
        List<Integer> compressResult = Lists.newArrayList();

        createDictionary();
        int dictSize = dictionary.keySet().size();


        try (Reader inputFile = new BufferedReader(new FileReader(fileToCompress));) {
            String w = "";
            int nextChar;

            while (( nextChar = inputFile.read() ) != -1) {
                char c = (char) nextChar;
                String wc = w + c;
                if (dictionary.containsKey(wc))
                    w = wc;

                else {
                    Integer wCode = dictionary.get(w);
                    compressResult.add(wCode);
                    dictionary.put(wc, dictSize++);
                    w = Character.toString(c);
                }
            }

            if (StringUtils.isNotEmpty(w))
                compressResult.add(dictionary.get(w));
        }
        return compressResult;
    }

    @Override
    public CompressInfoFile decompressFile(final File fileToDecompress) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void createDictionary() {
        dictionary = Maps.newTreeMap();
        for (int i = 0; i < START_DICT_SIZE; i++) {
            dictionary.put(Character.toString((char) i), i);
        }
    }

    private FileChannel getFileChannel(File fileToCompress) throws IOException {
        RandomAccessFile rac = new RandomAccessFile(fileToCompress, "r");

        return rac.getChannel();
    }

    private char[] getBufferedText(ByteBuffer buffer) {
        int readedTextLength = buffer.position();
        byte[] buff = new byte[readedTextLength];
        int i = 0;

        buffer.flip();
        while (buffer.hasRemaining()) {
            buff[i] = buffer.get();
            ++i;
        }
        buffer.clear();
        String s = new String(buff, Charset.forName("UTF-8"));

        return s.toCharArray();

    }
}
