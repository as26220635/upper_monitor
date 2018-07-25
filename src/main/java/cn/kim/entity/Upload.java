
package cn.kim.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "upload", propOrder = {
    "file"
})
public class Upload {

    protected CxfFileWrapper file;
   

    public CxfFileWrapper getFile() {
        return file;
    }

    public void setFile(CxfFileWrapper value) {
        this.file = value;
    }

}
