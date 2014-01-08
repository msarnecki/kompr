package pl.pw.edu.ee.kompresja.model;

/**
 * @author Marek Sarnecki : msarneck@gmail.com
 */

public class DictionaryBuffer {
    private StringBuffer buffer;
    private final int size;


    public DictionaryBuffer(final int size) {
        this.size = size;
        buffer = new StringBuffer(size);
    }

    public void append(final String msg) {
        buffer.append(msg);
        trim();
    }

    public void append(final char c) {
        this.append(String.valueOf(c));
    }

    private void trim() {
        if (buffer.length() > size) {
            buffer = buffer.delete(0, buffer.length() - size);
        }
    }

    public String substring(int start, int end) {
        return buffer.substring(start, end);
    }

    public int indexOf(String string) {
        return buffer.indexOf(string);
    }

    public StringBuffer getBuffer() {
        return buffer;
    }

    public int getSize() {
        return size;
    }
}
