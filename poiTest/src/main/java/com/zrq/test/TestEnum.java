package com.zrq.test;

/**
 * Copyright © 2020 cjkj. All rights reserved.
 * @Description: 测试枚举类型
 * @author: zrq
 * @date: 2020年8月21日
 * @version: 
 */
public class TestEnum {
	
	public static void main(String[] args) {
		
		//测试枚举类型
		MyEnum[] enums = MyEnum.values();
		for (MyEnum myEnum : enums) {
			if(myEnum.getMs().equals("小庄")) {
				myEnum.setCo(500);
				System.out.println(MyEnum.valueOf("NAME_ZHUANG").man);
			}
			System.out.println(myEnum.getCo()+"=="+myEnum.getMs()+"--"+myEnum.getMan());
		}
	}
	
	

}
