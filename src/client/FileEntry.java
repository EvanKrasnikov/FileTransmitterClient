package client;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class FileEntry extends RecursiveTreeObject<FileEntry> {
    private String fileName;
    private String size;
    private String editionTime;

    public FileEntry(String fileName, String size, String editionTime) {
        this.fileName = fileName ;
        this.size = size;
        this.editionTime = editionTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSize() {
        return size;
    }

    public String getEditionTime() {
        return editionTime;
    }
}