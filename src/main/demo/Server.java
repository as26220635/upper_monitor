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

        Timer timer=new Timer();//实例化Timer类
        timer.schedule(new TimerTask(){
            public void run(){
                System.out.println(123);
                server.send(TCPServerNetty.getClientMap().keySet().iterator().next(), TextUtil.toByte("Test"));
                this.cancel();}},3000);//五百毫秒


    }
}
