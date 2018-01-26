package client.format;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileEntry extends RecursiveTreeObject<FileEntry> {
    private StringProperty fileName;
    private StringProperty size;
    private StringProperty editionTime;

    public FileEntry(String fileName, String size, String editionTime) {
        this.fileName = new SimpleStringProperty(fileName) ;
        this.size = new SimpleStringProperty(size);
        this.editionTime = new SimpleStringProperty(editionTime);
    }

    public StringProperty getFileNameProperty() {
        return fileName;
    }

    public StringProperty getSizeProperty() {
        return size;
    }

    public StringProperty getEditionTimeProperty() {
        return editionTime;
    }
}