package com.taobao.mina.test;

import java.text.SimpleDateFormat;
import java.util.Random;

import com.taobao.mina.test.command.SendMessageCommand;
import com.taobao.mina.test.serialize.HessianSerialUtil;

public class Test {
	
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    private static final String TOPIC = "TEST";
    
    private static final String GROUP = "TEST-GROUP";
	
	private static final SimpleDateFormat formatDate = new SimpleDateFormat(DATE_FORMAT);

	public static void main(String[] args) throws Exception {
		    Person person = new Person();
	        person.setUserId(1000L);
	        person.setNick("danchen");
	        person.setCountry("china");
	        person.setAge("30");
	        person.setBirthDay(formatDate.parse("1982-03-01"));
	        person.setAddress("hangzhou");
	        byte[] bytedata = HessianSerialUtil.serialize(person);
	        SendMessageCommand command = new SendMessageCommand();
	        Random random = new Random();
	        long l = random.nextLong();
	        
	        command.setCommandTag((short)1);
	        command.setMessageId(l);
	        command.setMd5(l);
	        command.setMessageLength(bytedata.length);
	        command.setSerializeMethod((short)1);
	        command.setMessageContent(bytedata);
	        command.setTopic(TOPIC);
	        command.setTopicStrLength((short)TOPIC.length());
	        command.setGroup(GROUP);
	        command.setGroupStrLength((short)GROUP.length());
		
	        byte[] encodebyte = command.encoder();
	        
	        SendMessageCommand command2 = new SendMessageCommand();
	        command2.decoder(encodebyte);
	        
	        if(command.getMessageId()!=command2.getMessageId()){
	        	System.out.println("command2 messageId="+command2.getMessageId());
	        	System.out.println("message id is not the same.");
	        }
	        
	        if(command.getMessageLength()!=command2.getMessageLength()){
	        	System.out.println("message length is not the same.");
	        }
	        
	        for(int i=0;i<command.getMessageLength();i++){
	        	if(command.getMessageContent()[i]!=command2.getMessageContent()[i]){
	        		System.out.println("command1 1 byte="+command.getMessageContent()[i]);
		        	System.out.println("command2 1 byte="+command2.getMessageContent()[i]);
	        		System.out.println("content is not the same.");
	        	}
	        	
	        }
	        
	        

	}

}
