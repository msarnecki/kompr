package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */

@Scope("prototype")
@Service("lzwService")
public class LZWServiceImpl implements LZWService {
    private Logger log = LoggerFactory.getLogger(LZWServiceImpl.class);
    public static final int START_DICT_SIZE = 256;
    private static final int BUFFER_SIZE = 4096;

    private Map<String, Integer> dictionary = Maps.newTreeMap();


    @Override
    public List<Integer> compress(File fileToCompress) throws IOException {
        List<Integer> compressResult = Lists.newLinkedList();
        dictionary = Maps.newTreeMap();


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

        dictionary = Maps.newTreeMap();

        return compress(uncompressedText.toCharArray());
    }

    private List<Integer> compress(char[] uncompressed) {
        for (int i = 0; i < START_DICT_SIZE; i++)
            dictionary.put(Character.toString((char) i), i);

        String w = "";
        List<Integer> compressResult = Lists.newLinkedList();

        for (char c : uncompressed) {

            String wc = w + c;

            if (dictionary.containsKey(wc))
                w = wc;

            else {
                int newDictionaryKey = dictionary.keySet().size();
                dictionary.put(wc, newDictionaryKey);
                compressResult.add(dictionary.get(w));
                w = Character.toString(c);
            }
        }

        if (StringUtils.isNotEmpty(w))
            compressResult.add(dictionary.get(w));

        return compressResult;
    }


    private FileChannel getFileChannel(File fileToCompress) throws FileNotFoundException {
        RandomAccessFile rac = new RandomAccessFile(fileToCompress, "r");
        return rac.getChannel();
    }

    private char[] getBufferedText(ByteBuffer buffer) {
        StringBuilder bufferedString = new StringBuilder(BUFFER_SIZE);

        buffer.flip();
        while (buffer.hasRemaining()) {
            bufferedString.append((char) buffer.get());
        }
        buffer.clear();

        return getCharArray(bufferedString);

    }

    private char[] getCharArray(StringBuilder bufferedString) {
        int bufferLength = bufferedString.length();
        char[] result = new char[bufferLength];

        bufferedString.getChars(0, bufferLength, result, 0);

        return result;
    }


}
