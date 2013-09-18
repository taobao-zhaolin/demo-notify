package com.taobao.mina.test.command;

public class SuccessResultMessageCommand extends ResultMessageCommand {

	private static final short successTag = 1;
	
	public SuccessResultMessageCommand() {
		super();
		super.setCommandTag(successTag);
	}
	
}
