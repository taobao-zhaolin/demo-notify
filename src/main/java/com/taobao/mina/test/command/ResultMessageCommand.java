package com.taobao.mina.test.command;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.taobao.mina.test.common.NotifyConstant;

public class ResultMessageCommand implements IMessageCommand{
	//2 byte
	//响应sucess or fail
	private short commandTag;
	//消息id
	//8 byte
	private long messageId;
	//2 byte
	private short infoLength;
	//具体的信息
	private String info;
	
	
	
	public ResultMessageCommand() {
		super();
		infoLength = 0;
	}

	public short getCommandTag() {
		return commandTag;
	}

	public void setCommandTag(short commandTag) {
		this.commandTag = commandTag;
	}

	public short getInfoLength() {
		return infoLength;
	}
	
	public void setInfoLength(short infoLength) {
		this.infoLength = infoLength;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
		try {
			this.infoLength = (short) info.getBytes(NotifyConstant.CHARACTERSET).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public byte[] encoder() {
		try {
			ByteBuffer buffer;
			buffer = ByteBuffer.allocate(12+infoLength);
			buffer.putShort(commandTag);
			buffer.putLong(messageId);
			buffer.putShort(infoLength);
			if(infoLength>0){
				buffer.put(info.getBytes(NotifyConstant.CHARACTERSET));
			}
			buffer.flip();
			return buffer.array();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	
	public void decoder(byte[] data) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocate(data.length);
		buffer.put(data);
		buffer.flip();
		
		this.commandTag = buffer.getShort();
		this.messageId = buffer.getLong();
		this.infoLength = buffer.getShort();
		if(infoLength>0){
			byte[] reasonbyte = new byte[infoLength];
			buffer.get(reasonbyte);
			this.info = new String(reasonbyte,NotifyConstant.CHARACTERSET);
		}
	}

}
