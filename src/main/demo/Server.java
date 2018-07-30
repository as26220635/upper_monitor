import cn.kim.common.netty.NettyServerBootstrap;
import cn.kim.common.netty.TCPServerNetty;
import cn.kim.util.TextUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 余庚鑫 on 2018/7/29
 */
public class Server {

    public static void main(String[] args) throws InterruptedException {
        TCPServerNetty server= new TCPServerNetty(8888);
//        NettyServerBootstrap server= new NettyServerBootstrap(9999);

    }
}
