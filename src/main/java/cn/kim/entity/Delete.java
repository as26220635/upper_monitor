package cn.kim.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delete", propOrder = {
		"file"
})
public class Delete {

	protected CxfFileWrapper file;
	
	public CxfFileWrapper getFile() {
		return file;
	}
	public void setFile(CxfFileWrapper file) {
		this.file = file;
	}


}
