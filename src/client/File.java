package client;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class File extends RecursiveTreeObject<File> {
    private StringProperty fileName;
    private StringProperty size;
    private StringProperty editionTime;

    public File(String fileName, String size, String editionTime) {
        this.fileName = new SimpleStringProperty(fileName) ;
        this.size = new SimpleStringProperty(size);
        this.editionTime = new SimpleStringProperty(editionTime);
    }

    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getSize() {
        return size.get();
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public String getEditionTime() {
        return editionTime.get();
    }

    public StringProperty editionTimeProperty() {
        return editionTime;
    }

    public void setEditionTime(String editionTime) {
        this.editionTime.set(editionTime);
    }
}