package com.taobao.mina.test.command;

public interface IMessageCommand {
	/**
	 * ±àÂë
	 * @return
	 */
	public byte[] encoder();
	/**
	 * ½âÂë
	 * @throws Exception
	 */
	public void decoder(byte[] data) throws Exception;
}
