package pl.pw.edu.ee.kompresja.service.compress.lz77;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pw.edu.ee.kompresja.model.DictionaryBuffer;

import java.io.*;

public class LZ77T {
    private final static Logger log = LoggerFactory.getLogger(LZ77T.class);
    public static final int DICTIONARY_SIZE = 512;
    public static final String COMPRESSED_EXTENSION = ".77";
    public static final String UNCOMPRESSED_EXTENSION = "u";

    private DictionaryBuffer dictionary;

    public LZ77T() {
        dictionary = new DictionaryBuffer(DICTIONARY_SIZE);
    }

    public File compress(final File infile) throws IOException {
        Reader inputFile = new BufferedReader(new FileReader(infile));

        File result = new File(infile + COMPRESSED_EXTENSION);
        PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(result)));

        int nextChar;
        int matchIndex = 0;
        int tempIndex = 0;
        String repeatMatch = "";


        while ((nextChar = inputFile.read()) != -1) {

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

        return result;
    }

    public File decompress(final File infile) throws IOException {
        Reader inputFile = new BufferedReader(new FileReader(infile));

        final File result = new File(infile.getName() + UNCOMPRESSED_EXTENSION);
        final PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(result)));
        final StreamTokenizer st = getStreamTokenizer(inputFile);
        int wordIndexInDictionary;
        int repeatedWordFromDictionaryLength;

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

                String matchedStringFromDictionary = dictionary.substring(wordIndexInDictionary, wordIndexInDictionary + repeatedWordFromDictionaryLength);

                printWriter.print(matchedStringFromDictionary);
                dictionary.append(matchedStringFromDictionary);
            } else {
            }

        }

        inputFile.close();
        printWriter.flush();
        printWriter.close();

        return result;
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
