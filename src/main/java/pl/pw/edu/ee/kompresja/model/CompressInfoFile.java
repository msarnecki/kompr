package pl.pw.edu.ee.kompresja.model;

import java.io.File;

public class CompressInfoFile {

    private CompressInfo compressInfo;
    private File compressedFile;

    public CompressInfoFile(CompressInfo compressInfo, File compressedFile) {
        this.compressInfo = compressInfo;
        this.compressedFile = compressedFile;
    }

    public CompressInfo getCompressInfo() {
        return compressInfo;
    }

    public void setCompressInfo(CompressInfo compressInfo) {
        this.compressInfo = compressInfo;
    }

    public File getCompressedFile() {
        return compressedFile;
    }

    public void setCompressedFile(File compressedFile) {
        this.compressedFile = compressedFile;
    }
}
