package com.taobao.mina.test.remoting;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;


public class TMinaServer {
	/**
	 * 默认作为服务端的port
	 */
	private static final int PORT = 9123;
	/**
	 * buffer size
	 */
	private static final int readBufferSize = 2048;
	/**
	 * 默认消息的处理方法
	 */
	private IoHandler handler;	

	public TMinaServer() {
		
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
		  	IoAcceptor acceptor = new NioSocketAcceptor();
	        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
	        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
	        acceptor.setHandler(handler );
	        acceptor.getSessionConfig().setReadBufferSize( readBufferSize );
	        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
	        acceptor.bind( new InetSocketAddress(PORT) );
	}
	
}
