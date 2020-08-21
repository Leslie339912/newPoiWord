package com.zrq.test;

/**
 * Copyright © 2020 cjkj. All rights reserved.
 * @Description: 枚举类型
 * @author: zrq
 * @date: 2020年8月21日
 * @version: 
 */
public enum MyEnum {
	
	//每个枚举类型就对应一个构造方法（带参数）
	NAME_ZHUANG(100,"小庄","好人"),
	NAME_LIN(200,"小林","坏人");

	Integer co;
	String ms;
	String man;
	
	//构造方法一定要有，带所有参数的构造(参数一定要对应)
	MyEnum(int ordinal,String ms,String man) {
		// TODO Auto-generated constructor stub
		this.co = ordinal;
		this.ms = ms;
		this.man = man;
	}
	
	
	public String getMan() {
		return man;
	}


	public void setMan(String man) {
		this.man = man;
	}


	public Integer getCo() {
		return co;
	}
	public void setCo(Integer co) {
		this.co = co;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	
	
	

}
