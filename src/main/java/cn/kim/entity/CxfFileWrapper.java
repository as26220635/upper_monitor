
package cn.kim.entity;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cxfFileWrapper", propOrder = {
        "file",
        "fileMD5",
        "fileName",
        "filePath",
        "fileToken",
})
public class CxfFileWrapper {

    @XmlMimeType("application/octet-stream")
    protected DataHandler file;
    protected String fileMD5;
    protected String fileName;
    protected String filePath;
    protected String fileToken;

    public DataHandler getFile() {
        return file;
    }

    public void setFile(DataHandler value) {
        this.file = value;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String value) {
        this.fileMD5 = value;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String value) {
        this.fileName = value;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String value) {
        this.filePath = value;
    }

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

}
