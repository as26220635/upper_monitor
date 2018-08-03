import cn.kim.common.netty.TCPServerNetty;

/**
 * Created by 余庚鑫 on 2018/7/29
 */
public class Server {

    public static void main(String[] args) throws InterruptedException {
        TCPServerNetty server= new TCPServerNetty(8888);
//        NettyServerBootstrap server= new NettyServerBootstrap(9999);

    }
}
