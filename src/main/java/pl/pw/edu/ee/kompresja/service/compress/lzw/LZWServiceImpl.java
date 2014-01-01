package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */

@Scope("prototype")
@Service("lzwService")
public class LZWServiceImpl implements LZWService {
    private Logger log = LoggerFactory.getLogger(LZWServiceImpl.class);
    public static final int START_DICT_SIZE = 65536;
    private static final int BUFFER_SIZE = 4096;

    private Map<String, Integer> dictionary = Maps.newTreeMap();

    @Override
    public List<Integer> compress(File fileToCompress) throws IOException {
        List<Integer> compressResult = Lists.newArrayList();
        createDictionary();

        try (FileChannel fileChannel = getFileChannel(fileToCompress)) {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (fileChannel.read(buffer) != -1) {
                char[] uncompressed = getBufferedText(buffer);
                List<Integer> compressedText = compress(uncompressed);
                compressResult.addAll(compressedText);

            }
        }

        return compressResult;
    }

    @Override
    public List<Integer> compress(String uncompressedText) {
        if (StringUtils.isEmpty(uncompressedText))
            throw new IllegalArgumentException();

        createDictionary();

        return compress(uncompressedText.toCharArray());
    }

    private List<Integer> compress(char[] uncompressed) {

        int dictSize = dictionary.keySet().size();
        String w = "";
        List<Integer> compressResult = Lists.newArrayList();

        for (char c : uncompressed) {

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

        return compressResult;
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
