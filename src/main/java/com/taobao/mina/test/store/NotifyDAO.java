package com.taobao.mina.test.store;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class NotifyDAO {
	
	public void insert(NotifyDO notifyDO) throws Exception{
		String command = "insert into notify(messageId,topic,bus_group,content,gmt_create,gmt_modified) values(?,?,?,?,now(),now())";
		Connection conn = NotifyDatabase.getConn();
		PreparedStatement  stmt = conn.prepareStatement(command);
		stmt.setLong(1, notifyDO.getMessageId());
		stmt.setString(2, notifyDO.getTopic());
		stmt.setString(3, notifyDO.getGroup());
		InputStream is = new ByteArrayInputStream(notifyDO.getContent());
		stmt.setBinaryStream(4, is, notifyDO.getContent().length);
	    stmt.executeUpdate();
        stmt.close();
	}
	
}
