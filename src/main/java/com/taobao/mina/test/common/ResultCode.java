package com.taobao.mina.test.common;

public class ResultCode {
	/**
	 * �Ƿ�ɹ�
	 */
	private boolean success;
	/**
	 * �ӷ���˴���������Ϣ
	 */
	private String info;
	/**
	 * -1��ʾ��Ч
	 * 0��ʾ�����Ч
	 */
	private short valid;
	
	
	
	public ResultCode() {
		super();
		this.valid = -1;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public synchronized short getValid() {
		if(valid==-1){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return valid;
	}
	
	public synchronized void setValid(short valid) {
		this.valid = valid;
		this.notify();
	}
	
	@Override
	public String toString() {
		return "ResultCode [success=" + success + ", info=" + info + ", valid="
				+ valid + "]";
	}
	
	
}
