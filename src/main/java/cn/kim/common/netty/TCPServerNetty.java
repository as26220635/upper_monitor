package cn.kim.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.*;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 余庚鑫 on 2018/7/29
 */
public class TCPServerNetty {
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
        bind();
    }


    private void bind() {


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
//                    p.addLast(new LineBasedFrameDecoder(1024));
//                    p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
//                    p.addLast("bytesDecoder", new ByteArrayDecoder());
                    // Encoder
//                    p.addLast("frameEncoder", new LengthFieldPrepender(4));
//                    p.addLast("bytesEncoder", new ByteArrayEncoder());

                    p.addLast(new SmartCarEncoder());
                    p.addLast(new SmartCarDecoder());
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
            return TCPServerNetty.getClientMap().get(clientIP).writeAndFlush(msg).isSuccess();
        } catch (Exception e) {
            return false;
        }
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