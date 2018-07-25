package cn.kim.util;

import cn.kim.common.attr.Attribute;
import cn.kim.common.attr.Attribute;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * Created by 余庚鑫 on 2018/5/23
 * 邮件工具类
 */
public class EmailUtil {
    //是否开启debug模式
    private final static String IS_DEBUG = "true";

    private static Session session;

    /**
     * 初始化
     */
    public static void init() {
        System.out.println("====加载邮箱配置到缓存=====");
        //加载邮箱配置
        CacheUtil.setParam("EMAIL_USER", AllocationUtil.get("EMAIL_USER"));
        CacheUtil.setParam("EMAIL_PASSWORD", AllocationUtil.get("EMAIL_PASSWORD"));
        CacheUtil.setParam("EMAIL_PROTOCOL", AllocationUtil.get("EMAIL_PROTOCOL"));
        CacheUtil.setParam("EMAIL_HOST", AllocationUtil.get("EMAIL_HOST"));
        CacheUtil.setParam("EMAIL_PORT", AllocationUtil.get("EMAIL_PORT"));
        CacheUtil.setParam("EMAIL_AUTH", AllocationUtil.get("EMAIL_AUTH"));
        CacheUtil.setParam("EMAIL_STATUS", AllocationUtil.get("EMAIL_STATUS"));
        CacheUtil.setParam("EMAIL_SSL_ENABLE", AllocationUtil.get("EMAIL_SSL_ENABLE"));

        Properties props = new Properties();
        // 开启debug调试，以便在控制台查看
        props.setProperty("mail.debug", IS_DEBUG);
        // 设置邮件服务器主机名
        props.setProperty("mail.host", TextUtil.toString(CacheUtil.getParam("EMAIL_HOST")));
        // 发送服务器需要身份验证
        props.setProperty("mail." + TextUtil.toString(CacheUtil.getParam("EMAIL_PROTOCOL")) + ".auth", TextUtil.toString(CacheUtil.getParam("EMAIL_AUTH")).equals(TextUtil.toString(Attribute.STATUS_SUCCESS)) ? "true" : "false");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", TextUtil.toString(CacheUtil.getParam("EMAIL_PROTOCOL")));
        // SSL配置
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        props.put("mail." + TextUtil.toString(CacheUtil.getParam("EMAIL_PROTOCOL")) + ".ssl.enable", TextUtil.toString(CacheUtil.getParam("EMAIL_SSL_ENABLE")).equals(TextUtil.toString(Attribute.STATUS_SUCCESS)) ? "true" : "false");
        props.put("mail." + TextUtil.toString(CacheUtil.getParam("EMAIL_PROTOCOL")) + ".ssl.socketFactory", sf);

        session = Session.getInstance(props);
    }


    /**
     * 发送邮件
     *
     * @param title          标题
     * @param content        内容
     * @param toEmailAddress 收件人
     */
    public static void sendEmail(String title, String content, String[] toEmailAddress) {
        //是否开启发送邮件
        if (session == null || TextUtil.toString(AllocationUtil.get("EMAIL_STATUS")).equals(TextUtil.toString(Attribute.STATUS_ERROR))) {
            return;
        }
        String emailUser = TextUtil.toString(CacheUtil.getParam("EMAIL_USER"));
        String emailHost = TextUtil.toString(CacheUtil.getParam("EMAIL_HOST"));
        String emailPassword = TextUtil.toString(CacheUtil.getParam("EMAIL_PASSWORD"));

        Transport transport = null;
        try {
            Message msg = new MimeMessage(session);
            // 发送的邮箱地址
            msg.setFrom(new InternetAddress(emailUser));
            // 设置标题
            msg.setSubject(title);
            // 设置内容
            msg.setContent(content, "text/html;charset=gbk;");
            transport = session.getTransport();
            // 设置服务器以及账号和密码
            // 这里端口改成465
            transport.connect(emailHost, emailUser, emailPassword);
            // 发送到的邮箱地址
            transport.sendMessage(msg, getAddress(toEmailAddress));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送HTML内容邮件
     *
     * @param title          标题
     * @param sendHtml       内容
     * @param toEmailAddress 收件人
     */
    public void doSendHtmlEmail(String title, String sendHtml, String[] toEmailAddress) {
        //是否开启发送邮件
        if (session == null || TextUtil.toString(AllocationUtil.get("EMAIL_STATUS")).equals(TextUtil.toString(Attribute.STATUS_ERROR))) {
            return;
        }
        String emailUser = TextUtil.toString(CacheUtil.getParam("EMAIL_USER"));
        String emailHost = TextUtil.toString(CacheUtil.getParam("EMAIL_HOST"));
        String emailPassword = TextUtil.toString(CacheUtil.getParam("EMAIL_PASSWORD"));
        String emailProtocol = TextUtil.toString(CacheUtil.getParam("EMAIL_PROTOCOL"));

        Transport transport = null;
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUser));

            // 邮件主题
            message.setSubject(title);

            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(sendHtml, "text/html;charset=UTF-8");

            // 保存邮件
            message.saveChanges();

            transport = session.getTransport(emailProtocol);

            // smtp验证  用户名和授权码
            transport.connect(emailHost, emailUser, emailPassword);

            // 发送
            transport.sendMessage(message, getAddress(toEmailAddress));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送HTML内容邮件 带附件
     *
     * @param title          标题
     * @param sendHtml       内容
     * @param toEmailAddress 收件人
     * @param attachments    附件
     */
    public void doSendHtmlEmail(String title, String sendHtml, String[] toEmailAddress, File[] attachments) {
        //是否开启发送邮件
        if (session == null || TextUtil.toString(AllocationUtil.get("EMAIL_STATUS")).equals(TextUtil.toString(Attribute.STATUS_ERROR))) {
            return;
        }
        String emailUser = TextUtil.toString(CacheUtil.getParam("EMAIL_USER"));
        String emailHost = TextUtil.toString(CacheUtil.getParam("EMAIL_HOST"));
        String emailPassword = TextUtil.toString(CacheUtil.getParam("EMAIL_PASSWORD"));
        String emailProtocol = TextUtil.toString(CacheUtil.getParam("EMAIL_PROTOCOL"));

        Transport transport = null;
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUser));

            // 邮件主题
            message.setSubject(title);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            // 添加附件的内容
            if (attachments != null) {
                for (File attachment : attachments) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));

                    // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                    // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                    //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                    //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

                    //MimeUtility.encodeWord可以避免文件名乱码
                    attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();

            transport = session.getTransport(emailProtocol);
            // smtp验证
            transport.connect(emailHost, emailUser, emailPassword);
            // 发送
            transport.sendMessage(message, getAddress(toEmailAddress));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param emilAddress
     * @return
     * @throws Exception
     * @Title: getAddress
     * @Description: 遍历收件人信息
     * @return: Address[]
     */
    private static Address[] getAddress(String[] emilAddress) throws Exception {
        Address[] address = new Address[emilAddress.length];
        for (int i = 0; i < address.length; i++) {
            address[i] = new InternetAddress(emilAddress[i]);
        }
        return address;
    }
}
