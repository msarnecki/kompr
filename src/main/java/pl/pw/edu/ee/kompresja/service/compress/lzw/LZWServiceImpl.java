package pl.pw.edu.ee.kompresja.service.compress.lzw;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Marcin Jasion <marcin.jasion@gmail.com>
 */

@Service("lzwService")
public class LZWServiceImpl implements LZWService {
    private Logger log = LoggerFactory.getLogger(LZWServiceImpl.class);
    public static final int START_DICT_SIZE = 256;


    @Override
    public List<Integer> compress(String uncompressedText) {
        if (uncompressedText == null || StringUtils.isEmpty(uncompressedText))
            throw new IllegalArgumentException();

        Map<String, Integer> dictionary = Maps.newTreeMap();

        for (int i = 0; i < START_DICT_SIZE; i++)
            dictionary.put(Character.toString((char) i), i);

        String w = "";
        List<Integer> compressResult = Lists.newLinkedList();

        for (char c : uncompressedText.toCharArray()) {

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
}
