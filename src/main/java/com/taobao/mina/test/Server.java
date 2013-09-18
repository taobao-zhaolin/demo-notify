package com.taobao.mina.test;


import com.taobao.mina.test.common.NotifyConstant;
import com.taobao.mina.test.handler.MinaServerHandler;
import com.taobao.mina.test.remoting.TMinaServer;
import com.taobao.mina.test.store.NotifyDatabase;

/**
 * notify·þÎñ¶ËÆô¶¯
 * @author danchen
 *
 */
public class Server {
	
	
    public static void main( String[] args ) throws Exception
    {
        TMinaServer minaServer = new TMinaServer();
        MinaServerHandler hander = new MinaServerHandler();
        minaServer.setHandler(hander);
        minaServer.init();
        
        if(NotifyConstant.storeMessage){
        	 NotifyDatabase notifySqlMapClient = new NotifyDatabase();
        	 notifySqlMapClient.init();
        }
       
    }
}
