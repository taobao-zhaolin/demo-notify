package com.taobao.mina.test;

import java.text.SimpleDateFormat;
import java.util.Random;

import com.taobao.mina.test.command.SendMessageCommand;
import com.taobao.mina.test.common.ResultCode;
import com.taobao.mina.test.common.SyncVariables;
import com.taobao.mina.test.handler.MinaClientHandler;
import com.taobao.mina.test.remoting.TMinaClient;
import com.taobao.mina.test.serialize.HessianSerialUtil;
/**
 * 同步调用方法参考:
 * http://sunjun041640.blog.163.com/blog/static/256268322011111874633997/
 * 
 * @author danchen
 *
 */
public class Client implements Runnable{
	
	
    private final String DATE_FORMAT = "yyyy-MM-dd";
	
	private final SimpleDateFormat formatDate = new SimpleDateFormat(DATE_FORMAT);
	
	private final String topic = "TEST";
	
	private final String group = "TESTGROUP";
	
	
	public Client() {
		super();
	}
	
	public SendMessageCommand getCommand(long id){
		try {
			Person person = new Person();
			Random r1 = new Random();
			person.setUserId(r1.nextLong());
			person.setNick("danchen");
			person.setCountry("china");
			person.setAge("30");
			person.setBirthDay(formatDate.parse("1982-03-01"));
			person.setAddress("hangzhou");
			byte[] bytedata = HessianSerialUtil.serialize(person);
			SendMessageCommand command = new SendMessageCommand();
			Random random = new Random();
			long l = random.nextLong();
			
			command.setCommandTag((short)0);
			command.setMessageId(id);
			command.setMd5(l);
			command.setMessageLength(bytedata.length);
			command.setSerializeMethod((short)1);
			command.setTopic(topic);
			command.setTopicStrLength((short) topic.length());
			command.setGroup(group);
			command.setGroupStrLength((short) group.length());
			command.setMessageContent(bytedata);
			return command;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public void run() {
		TMinaClient tMinaClient = new TMinaClient("127.0.0.1");
		MinaClientHandler minaClientHandler = new MinaClientHandler();
		tMinaClient.setHandler(minaClientHandler);
		try {
			tMinaClient.init();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		double total = 100000;
		long start = System.currentTimeMillis();
		
		for(int i=0;i<total;i++){
			try {
				long id=SyncVariables.getUuid();
				byte[] data = getCommand(id).encoder();
				ResultCode resultCode = new ResultCode();
				SyncVariables.hashMap.put(id, resultCode);
				tMinaClient.writeData(data);
				
				if(resultCode.getValid()==0){
					System.out.println(resultCode.toString());
					SyncVariables.hashMap.remove(id);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		long end = System.currentTimeMillis();
		double speed =total/(((double) (end-start))/(double)1000);
		System.out.println("speed="+speed+" current thread id="+Thread.currentThread().getId()+" is over.");
		
	}

	

	public static void main(String[] args) {
		int length=2;
		Thread[] threads = new Thread[length];
		Client[] clients = new Client[length];
		for(int i=0;i<clients.length;i++){
			clients[i] = new Client();
			threads[i]=new Thread(clients[i]);
			threads[i].start();
		}
	
	}

	

}
