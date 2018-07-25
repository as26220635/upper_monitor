package cn.kim.service.impl;

import cn.kim.service.FileWS;
import cn.kim.service.FileWS;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import java.net.URL;

/**
 * Created by 余庚鑫 on 2017/11/1.
 * CXF访问文件服务器
 */
@WebServiceClient
public class FileWSImplService extends Service {

    public final static QName SERVICE = new QName("http://impl.service.file.cn/", "FileWSImplService");
    public final static QName FILE_WSIMPL_PORT = new QName("http://impl.service.file.cn/", "FileWSImplPort");

    public FileWSImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    @WebEndpoint(name = "FileWSImplPort")
    public FileWS getFileWSImplPort() {
        return super.getPort(FILE_WSIMPL_PORT, FileWS.class);
    }

}
