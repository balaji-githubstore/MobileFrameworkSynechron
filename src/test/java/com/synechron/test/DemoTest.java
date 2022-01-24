package com.synechron.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoTest {
	
	@DataProvider
	public String[][] validData()
	{
		String[][] main=new String[3][2];
		//i - number of testcase
		//j - number of parameters
		main[0][0]="john";
		main[0][1]="john123";
	
		main[1][0]="king";
		main[1][1]="king123";
		
		main[2][0]="mark";
		main[2][1]="mark123";
	
		return main;
	}
	
	@Test(dataProvider = "validData")
	public void validTest(String username,String password)
	{
		System.out.println("valid "+username+password);
	}

}
