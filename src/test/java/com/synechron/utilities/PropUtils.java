package com.synechron.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {
	
	public static String getValue(String fileDetail,String key) throws IOException
	{
		FileInputStream file=new FileInputStream(fileDetail);
		
		Properties prop=new Properties();
		prop.load(file);
		return prop.getProperty(key);
	}

}
