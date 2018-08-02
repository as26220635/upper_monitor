package cn.kim.common.netty;

import cn.kim.common.attr.Constants;
import cn.kim.entity.TCPSendMessage;
import cn.kim.listener.LockListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import javax.xml.bind.DatatypeConverter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by 余庚鑫 on 2018/7/29
 */
public class TCPServerNetty {
    /**
     * 发送消息超时时间
     */
    public static final int OVER_TIME = 5000;
    /**
     * 心跳
     */
    public static final int HEART_BEAT = 0x56;
    /**
     * 刷卡
     */
    public static final int REQUEST_CARD = 0x53;
    /**
     * 开门
     */
    public static final int OPEN_DOOR = 0x2C;
    /**
     * 门常开
     */
    public static final int OPEN_DOORS = 0x2D;
    /**
     * 关门
     */
    public static final int CLOSE_DOOR = 0x2E;
    /**
     * 锁门
     */
    public static final int LOCK_DOOR = 0x2F;
    /**
     * 禁止读卡
     */
    public static final int PROHIBITION_CARD = 0x5A;
    /**
     * 时间同步
     */
    public static final int TIME_ASYNC = 0x07;
    /**
     * 操作报警
     */
    public static final int POLICE_ALARM = 0x18;
    /**
     * 操作火警
     */
    public static final int FIRE_ALARM = 0x19;
    /**
     * 设置参数
     */
    public static final int SET_PARAM = 0x63;
    /**
     * 控制器复位
     */
    public static final int CONTROLLER_RESET = 0x04;
    /**
     * 控制输出
     */
    public static final int OUT_PUT = 0x73;
    /**
     * 端口号
     */
    private int port;
    /**
     * 客户端集合
     */
    private static Map<String, Channel> clientMap = new ConcurrentHashMap<>();


    public TCPServerNetty(int port) {
        this.port = port;
//        this.bind();
    }


    public void bind() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        try {


            ServerBootstrap bootstrap = new ServerBootstrap();


            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            //连接数
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            //不延迟，消息立即发送
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            //长连接
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    // Decoders
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new TCPDecoder());
                    p.addLast(new OutBoundHandler());
                    p.addLast(new IdleStateHandler(0, 0, 300), new InBoundHandler());
                }
            });
            ChannelFuture f = bootstrap.bind(port).sync();
            if (f.isSuccess()) {
                System.out.println("启动Netty服务成功，端口号：" + this.port);
            }
            // 关闭连接
            f.channel().closeFuture().sync();


        } catch (Exception e) {
            System.out.println("启动Netty服务异常，异常信息：" + e.getMessage());
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    /**
     * 发送消息
     *
     * @param clientIP
     * @param msg
     * @return
     */
    public boolean send(String clientIP, byte[] msg) {
        try {
            System.out.println(DatatypeConverter.printHexBinary(msg));
            TCPSendMessage tcpSendMessage = new TCPSendMessage();
            tcpSendMessage.setClientIp(clientIP);
            tcpSendMessage.setData(msg);
            //同步锁
            CountDownLatch countDownLatch = new CountDownLatch(1);
            tcpSendMessage.setCountDownLatch(countDownLatch);
            //等待1秒 判断是否下发成功
            TCPServerNetty.getClientMap().get(clientIP).writeAndFlush(tcpSendMessage);
            //等待，设置超时时间
            countDownLatch.await(OVER_TIME, TimeUnit.MILLISECONDS);
            //是否成功
            return tcpSendMessage.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean send(String clientIP, int command) {
        return send(clientIP, command, null);
    }

    public boolean send(String clientIP, int command, byte[] data) {
        return send(clientIP, TCPServerNetty.stickyPack(command, 00, 00, data));
    }

    public boolean send(String clientIP, int command, int door, byte[] data) {
        return send(clientIP, TCPServerNetty.stickyPack(command, 00, door, data));
    }

    public boolean send(String clientIP, int command, int address, int door, byte[] data) {
        return send(clientIP, TCPServerNetty.stickyPack(command, address, door, data));
    }

    /**
     * 粘包
     *
     * @param command
     * @param address
     * @param door
     * @param data
     * @return
     */
    public static byte[] stickyPack(int command, int address, int door, byte[] data) {
        byte[] resultBytes = new byte[7 + 2 + (data == null ? 0 : data.length)];
        resultBytes[0] = Constants.TCP_HEAD_DATA;
        resultBytes[1] = (byte) 0xA0;
        resultBytes[2] = (byte) command;
        resultBytes[3] = (byte) address;
        resultBytes[4] = (byte) door;
        resultBytes[5] = data == null ? 00 : (byte) data.length;
        resultBytes[6] = 00;
        //复制数据
        if (data != null) {
            System.arraycopy(data, 0, resultBytes, 7, data.length);
        }
        //异或校验
        byte checkByte = resultBytes[0];
        for (int i = 1; i < resultBytes.length - 2; i++) {
            checkByte ^= resultBytes[i];
        }
        //校验
        resultBytes[7 + (data == null ? 0 : data.length)] = checkByte;
        resultBytes[7 + (data == null ? 0 : data.length) + 1] = Constants.TCP_END_DATA;
        return resultBytes;
    }

    public static Map<String, Channel> getClientMap() {
        return clientMap;
    }

    public static void setClientMap(Map<String, Channel> map) {
        TCPServerNetty.clientMap = map;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

}