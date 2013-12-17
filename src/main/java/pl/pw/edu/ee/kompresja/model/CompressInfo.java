package pl.pw.edu.ee.kompresja.model;

/**
 * @author @author msarneck@gmail.com
 *
 */

public class CompressInfo {


    private int duration;
    private int sizeBefore;
    private int sizeAfter;


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSizeBefore() {
        return sizeBefore;
    }

    public void setSizeBefore(int sizeBefore) {
        this.sizeBefore = sizeBefore;
    }

    public int getSizeAfter() {
        return sizeAfter;
    }

    public void setSizeAfter(int sizeAfter) {
        this.sizeAfter = sizeAfter;
    }
}
