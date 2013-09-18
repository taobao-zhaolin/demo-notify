package com.taobao.mina.test.handler;


import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.mina.test.command.ResultMessageCommand;
import com.taobao.mina.test.common.ResultCode;
import com.taobao.mina.test.common.SyncVariables;



public class MinaClientHandler extends IoHandlerAdapter {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());

	
	public MinaClientHandler()
	{
		
	}


	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	}


	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}


	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}


	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}


	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}


	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		if(message==null){
    		return;
    	}
    	
    	byte[] messageByte = (byte[])message;
    	ResultMessageCommand resultMessageCommand = new ResultMessageCommand();
    	resultMessageCommand.decoder(messageByte);
    	long messageId = resultMessageCommand.getMessageId();
    	ResultCode resultCode = SyncVariables.hashMap.get(messageId);
    	
    	if(resultMessageCommand.getCommandTag() == 1){
    		resultCode.setSuccess(true);
    	}else if(resultMessageCommand.getCommandTag() == 2){
    		resultCode.setSuccess(false);
    	}
    	resultCode.setInfo(resultMessageCommand.getInfo());
    	resultCode.setValid((short)0);
	}


	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
	}
	 
	
	
}
