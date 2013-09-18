package com.taobao.mina.test;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3343019863546864448L;

	private Long userId;
	
	private String nick;
	
	private String country;
	
	private String age;
	
	private Date birthDay;
	
	private String address;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Person [userId=" + userId + ", nick=" + nick + ", country="
				+ country + ", age=" + age + ", birthDay=" + birthDay
				+ ", address=" + address + "]";
	}
	
	
}
