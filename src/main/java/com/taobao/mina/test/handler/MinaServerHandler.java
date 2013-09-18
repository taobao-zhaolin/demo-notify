package com.taobao.mina.test.handler;


import java.nio.ByteBuffer;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.taobao.mina.test.command.FailResultMessageCommand;
import com.taobao.mina.test.command.SendMessageCommand;
import com.taobao.mina.test.command.SuccessResultMessageCommand;
import com.taobao.mina.test.common.NotifyConstant;
import com.taobao.mina.test.serialize.HessianSerialUtil;
import com.taobao.mina.test.serialize.JavaSerialUtil;
import com.taobao.mina.test.store.NotifyDAO;
import com.taobao.mina.test.store.NotifyDO;

public class MinaServerHandler extends IoHandlerAdapter {
		
	@Override
	public void sessionCreated(IoSession session) throws Exception {
        System.out.println("client "+session.getRemoteAddress()+" is connected.");
    }
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
        session.close();
    }
    @Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
    	if(message==null){
    		return;
    	}
    	
    	byte[] messageByte = (byte[])message;
    	ByteBuffer bytebuffer = ByteBuffer.allocate(10);
    	bytebuffer.put(messageByte, 0, 10);
    	bytebuffer.flip();
    	
    	short commandTag = bytebuffer.getShort();
        long messageId = bytebuffer.getLong();
    	
        
    	if(commandTag==0){
    		//normal message
    		try {
				SendMessageCommand command = new SendMessageCommand();
				command.decoder(messageByte);
				
				//store the message to database
				if(NotifyConstant.storeMessage){
					writeToDatabase(command);
				}
				
				//print
				if(command.getSerializeMethod()==1){
					Object object = HessianSerialUtil.deserialize(command.getMessageContent());
					System.out.println(object);
				}else if(command.getSerializeMethod()==2){
					Object object = JavaSerialUtil.deserialize(command.getMessageContent());
					//System.out.println(object);
				}else{
					throw new Exception("not support serialize method.");
				}
				
				SuccessResultMessageCommand successCommand = new SuccessResultMessageCommand();
				successCommand.setMessageId(messageId);
				successCommand.setInfo("success");
				session.write(successCommand.encoder());
			} catch (Exception e) {
				FailResultMessageCommand failCommand = new FailResultMessageCommand();
				failCommand.setInfo("error");
				failCommand.setMessageId(messageId);
				session.write(failCommand.encoder());
				e.printStackTrace();
			}	
    	}else if(commandTag==1){
    		//receive success info from client
    	}else if(commandTag==2){
    		//receive fail info from client
    	}
   
    }
    
    private void writeToDatabase(SendMessageCommand command) throws Exception{
		if(command==null){
			throw new Exception("SendMessageCommand is null");
		}
		
		NotifyDO notifyDO = new NotifyDO();
		notifyDO.setMessageId(command.getMessageId());
		notifyDO.setTopic(command.getTopic());
		notifyDO.setGroup(command.getGroup());
		notifyDO.setContent(command.getMessageContent());
		NotifyDAO notifyDAO = new NotifyDAO();
		notifyDAO.insert(notifyDO);
	}

	@Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
}
