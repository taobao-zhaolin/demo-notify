package com.taobao.mina.test.command;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.taobao.mina.test.common.NotifyConstant;

public class SendMessageCommand implements IMessageCommand{
	/*
	 * 1 message;
	 */
	private short commandTag;
	/*
	 * 消息id 8 byte
	 */
	private long messageId;
	/*
	 * 序列化方法 hessian 1 ; java 2 ; 3 custom
	 * 2 byte
	 */
	private short serializeMethod;
	/*
	 * 消息体的md5
	 * 8 byte
	 */
	private long md5;
	/*
	 * topic长度
	 */
	private short topicStrLength;
	/*
	 * topic
	 */
	private String topic;
	/*
	 * group长度
	 */
	private short groupStrLength;
	/*
	 * group
	 */
	private String group;
	/*
	 * 消息体的长度
	 * 4 byte
	 */
	private int messageLength;
	/*
	 * 消息的主体
	 * 变长
	 */
	private byte[] messageContent;
	
	public long getMessageId() {
		return messageId;
	}
	public short getCommandTag() {
		return commandTag;
	}
	public void setCommandTag(short commandTag) {
		this.commandTag = commandTag;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public short getSerializeMethod() {
		return serializeMethod;
	}
	public void setSerializeMethod(short serializeMethod) {
		this.serializeMethod = serializeMethod;
	}
	public int getMessageLength() {
		return messageLength;
	}
	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}
	public long getMd5() {
		return md5;
	}
	public void setMd5(long md5) {
		this.md5 = md5;
	}
	public short getTopicStrLength() {
		return topicStrLength;
	}
	public void setTopicStrLength(short topicStrLength) {
		this.topicStrLength = topicStrLength;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public short getGroupStrLength() {
		return groupStrLength;
	}
	public void setGroupStrLength(short groupStrLength) {
		this.groupStrLength = groupStrLength;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public byte[] getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(byte[] messageContent) {
		this.messageContent = messageContent;
	}
	
	public byte[] encoder(){
		ByteBuffer buffer;
		try {
			buffer = ByteBuffer.allocate(28+topic.getBytes(NotifyConstant.CHARACTERSET).length+group.getBytes(NotifyConstant.CHARACTERSET).length+messageContent.length);
			//solid head
			buffer.putShort(commandTag); //2 bytes
			buffer.putLong(messageId);  //8 bytes
			buffer.putShort(serializeMethod); //2 bytes
			buffer.putLong(md5); //8 bytes
			buffer.putShort(topicStrLength); //2 bytes
			buffer.putShort(groupStrLength); //2 bytes
			buffer.putInt(messageLength);    //4 bytes
			//variable
			buffer.put(topic.getBytes(NotifyConstant.CHARACTERSET));
			buffer.put(group.getBytes(NotifyConstant.CHARACTERSET));
			buffer.put(messageContent);
			buffer.flip();
			return buffer.array();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public void decoder(byte[] messageCommand) throws Exception{
		ByteBuffer buffer = ByteBuffer.allocate(messageCommand.length);
		buffer.put(messageCommand);
		buffer.flip();
		//solid head
		this.commandTag = buffer.getShort();
		this.messageId = buffer.getLong();
		this.serializeMethod = buffer.getShort();
		this.md5 = buffer.getLong();
		this.topicStrLength = buffer.getShort();
		this.groupStrLength = buffer.getShort();
		this.messageLength = buffer.getInt();
		//variable
		byte[] topicByte = new byte[topicStrLength];
		buffer.get(topicByte,0,topicByte.length);
		this.topic = new String(topicByte,NotifyConstant.CHARACTERSET);
		
		byte[] groupByte = new byte[groupStrLength];
		buffer.get(groupByte,0,groupByte.length);
		this.group = new String(groupByte,NotifyConstant.CHARACTERSET);
		
		this.messageContent = new byte[messageLength];
		buffer.get(this.messageContent, 0, this.messageContent.length);
	}
	
	
}
