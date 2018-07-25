
package cn.kim.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "downloadResponse", propOrder = {
    "_return"
})
public class DownloadResponse {

    @XmlElement(name = "return")
    protected CxfFileWrapper _return;

    public CxfFileWrapper getReturn() {
        return _return;
    }

    public void setReturn(CxfFileWrapper value) {
        this._return = value;
    }

}
