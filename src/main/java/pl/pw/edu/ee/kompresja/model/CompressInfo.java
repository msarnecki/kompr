package pl.pw.edu.ee.kompresja.model;

import java.io.File;

/**
 * @author @author msarneck@gmail.com
 */

public class CompressInfo {

    private long duration;         // in ms
    private long sizeBefore;
    private long sizeAfter;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSizeBefore() {
        return sizeBefore;
    }

    public void setSizeBefore(long sizeBefore) {
        this.sizeBefore = sizeBefore;
    }

    public long getSizeAfter() {
        return sizeAfter;
    }

    public void setSizeAfter(long sizeAfter) {
        this.sizeAfter = sizeAfter;
    }
}
