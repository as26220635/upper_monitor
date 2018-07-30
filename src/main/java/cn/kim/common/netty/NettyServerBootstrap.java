package cn.kim.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by 余庚鑫 on 2018/7/29
 */
public class NettyServerBootstrap {

    private int port;


    public NettyServerBootstrap(int port) {
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
                protected void initChannel(SocketChannel socketChannel)
                        throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    p.addLast(new NettyServerHandler());
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


}