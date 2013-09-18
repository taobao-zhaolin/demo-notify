package com.taobao.mina.test.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SyncVariables {
	
	public static ConcurrentHashMap<Long, ResultCode> hashMap=new ConcurrentHashMap<Long, ResultCode>();
	
	private static AtomicLong uuid = new AtomicLong(0);
	
	public static long getUuid() {
		return uuid.getAndIncrement();
	}
}
