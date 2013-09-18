package com.taobao.mina.test.command;

public class FailResultMessageCommand extends ResultMessageCommand {
	private static final short failTag = 2;

	public FailResultMessageCommand() {
		super();
		super.setCommandTag(failTag);
	}

}
