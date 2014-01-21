package pl.pw.edu.ee.kompresja.model;

/**
 * @author @author msarneck@gmail.com
 */

public class CompressInfo {

    private long duration;         // in ms
    private long sizeBefore;
    private long sizeAfter;
    private String compressRatio;

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

    public String getCompressRatio() {
        return compressRatio;
    }

    public void setCompressRatio(String compressRatio) {
        this.compressRatio = compressRatio;
    }
}
