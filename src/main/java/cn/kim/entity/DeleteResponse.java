
package cn.kim.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteResponse", propOrder = {
    "_return"
})
public class DeleteResponse {

    @XmlElement(name = "return")
    protected boolean _return;

 
    public boolean isReturn() {
        return _return;
    }

    public void setReturn(boolean value) {
        this._return = value;
    }

}
