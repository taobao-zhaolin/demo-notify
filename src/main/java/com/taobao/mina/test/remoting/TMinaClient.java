package com.taobao.mina.test.remoting;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TMinaClient {
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	/**
	 * 服务器主机
	 */
	private String serverHostName;
	/**
	 * 默认作为服务端的port
	 */
	private static final int serverPORT = 9123;
	/**
	 * buffer size
	 */
	private static final int readBufferSize = 2048;
	/**
	 * 当接受到消息时的处理方法
	 */
	private IoHandler handler;
	/**
	 * 连接
	 */
	private static ConnectFuture future;
	
	
	
	public TMinaClient(String hostName) {
		this.serverHostName = hostName;
	}



	public IoHandler getHandler() {
		return handler;
	}



	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}



	public void init() throws Exception{
		if(handler==null){
	    	throw new Exception("not init message deal hander.");
	    }
		IoConnector connector;
		try {
			connector = new NioSocketConnector();
			connector.getSessionConfig().setReadBufferSize(readBufferSize);
			 
			connector.getFilterChain().addLast("logger", new LoggingFilter());
			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter((new ObjectSerializationCodecFactory())));
			connector.setHandler(handler);
			future = connector.connect(new InetSocketAddress(serverHostName, serverPORT));
			logger.warn("connection to host="+serverHostName+" port="+serverPORT+" is successful");
		} catch (Exception e) {
			logger.warn("connection to host="+serverHostName+" port="+serverPORT+" is failed.for detail reason:"+e.getMessage());
			throw new Exception(e);
		}
	
	}
	
	public void writeData(byte[] message) throws Exception{
		if(future.isConnected()){
			future.getSession().write(message);
		}else{
			logger.warn("not connected to target host.host="+this.serverHostName);
			throw new Exception("not connected,please check.");
		}
	}
	
}
