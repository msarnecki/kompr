package pl.pw.edu.ee.kompresja.service.compress.lz77;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pw.edu.ee.kompresja.model.CompressInfo;
import pl.pw.edu.ee.kompresja.model.CompressInfoFile;
import pl.pw.edu.ee.kompresja.model.DictionaryBuffer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author msarnecki@gmail.com
 */

@Service("LZ77Service")
public class LZ77ServiceImpl implements LZ77Service {
    private static final Logger log = LoggerFactory.getLogger(LZ77ServiceImpl.class);

    public static final int DICTIONARY_SIZE = 512;
    public static final String COMPRESSED_EXTENSION = ".77";
    public static final String UNCOMPRESSED_EXTENSION = "u";

    private DictionaryBuffer dictionary;

    public LZ77ServiceImpl() {
        dictionary = new DictionaryBuffer(DICTIONARY_SIZE);
    }


    @Override
    public CompressInfoFile compressFile(final File fileToCompress) throws IOException {
        final Reader inputFile = new BufferedReader(new FileReader(fileToCompress));

        final File fileResult = new File(fileToCompress + COMPRESSED_EXTENSION);
        final PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(fileResult)));
        final CompressInfo compressInfo = new CompressInfo();

        int nextChar;
        int matchIndex = 0;
        int tempIndex = 0;
        String repeatMatch = "";


        while (( nextChar = inputFile.read() ) != -1) {

            tempIndex = dictionary.indexOf(repeatMatch + (char) nextChar);
            if (tempIndex != -1) {
                repeatMatch += (char) nextChar;
                matchIndex = tempIndex;
            } else {
                // ~0~7~s
                String lz77String = "~" + matchIndex + "~" + repeatMatch.length() + "~" + (char) nextChar;
                final String rawString = repeatMatch + (char) nextChar;

                if (lz77String.length() <= rawString.length()) {
                    output.print(lz77String);
                    dictionary.append(rawString);
                    repeatMatch = "";
                    matchIndex = 0;
                } else {
                    repeatMatch = rawString;
                    matchIndex = -1;
                    while (repeatMatch.length() > 1 && matchIndex == -1) {
                        output.print(repeatMatch.charAt(0));
                        dictionary.append(repeatMatch.charAt(0));
                        repeatMatch = repeatMatch.substring(1, repeatMatch.length());
                        matchIndex = dictionary.indexOf(repeatMatch);
                    }
                }
            }
        }

        if (matchIndex != -1) {
            String lz77String = "~" + matchIndex + "~" + repeatMatch.length();
            if (lz77String.length() <= repeatMatch.length()) {
                output.print(lz77String);
            } else {
                output.print(repeatMatch);
            }
        }

        inputFile.close();
        output.flush();
        output.close();

        compressInfo.setSizeBefore(fileToCompress.length());
        compressInfo.setSizeAfter(fileResult.length());

        BigDecimal sizeBefore = new BigDecimal(compressInfo.getSizeBefore());
        BigDecimal sizeAfter = new BigDecimal(compressInfo.getSizeAfter() * 100);
        if (!sizeBefore.equals(BigDecimal.ZERO)) {
            BigDecimal ratio = sizeAfter.divide(sizeBefore, 2, RoundingMode.HALF_UP);

            compressInfo.setCompressRatio(ratio.toString());
        } else {
            compressInfo.setCompressRatio("0");
        }

        return new CompressInfoFile(compressInfo, fileResult);
    }

    @Override
    public CompressInfoFile decompressFile(final File fileToDecompress) throws IOException {
        Reader inputFile = new BufferedReader(new FileReader(fileToDecompress));

        final File fileResult = new File(fileToDecompress.getName() + UNCOMPRESSED_EXTENSION);
        final PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileResult)));
        final StreamTokenizer st = getStreamTokenizer(inputFile);
        int wordIndexInDictionary;
        int repeatedWordFromDictionaryLength;
        final CompressInfo compressInfo = new CompressInfo();

        while (st.nextToken() != StreamTokenizer.TT_EOF) {

            if (st.ttype == StreamTokenizer.TT_WORD) {
                dictionary.append(st.sval);
                printWriter.print(st.sval);
            } else if (st.ttype == StreamTokenizer.TT_NUMBER) {
                wordIndexInDictionary = (int) st.nval;
                st.nextToken();
                if (st.ttype == StreamTokenizer.TT_WORD) {
                    dictionary.append(wordIndexInDictionary + st.sval);
                    printWriter.print(wordIndexInDictionary + st.sval);
                    break;
                }

                st.nextToken();
                repeatedWordFromDictionaryLength = (int) st.nval;

                String matchedStringFromDictionary = dictionary.substring(wordIndexInDictionary,
                                                                          wordIndexInDictionary + repeatedWordFromDictionaryLength);

                printWriter.print(matchedStringFromDictionary);
                dictionary.append(matchedStringFromDictionary);
            } else {
            }

        }

        inputFile.close();
        printWriter.flush();
        printWriter.close();

        compressInfo.setSizeBefore(fileToDecompress.getTotalSpace());
        compressInfo.setSizeAfter(fileResult.getTotalSpace());
        return new CompressInfoFile(compressInfo, fileResult);
    }

    private StreamTokenizer getStreamTokenizer(Reader inputFile) {
        StreamTokenizer result = new StreamTokenizer(inputFile);

        result.ordinaryChar((int) ' ');
        result.ordinaryChar((int) '.');
        result.ordinaryChar((int) '-');
        result.ordinaryChar((int) '\n');
        result.wordChars((int) '\n', (int) '\n');
        result.wordChars((int) ' ', (int) '}');

        return result;
    }


}
