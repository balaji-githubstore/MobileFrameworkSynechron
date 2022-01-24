package com.synechron.utilities;

import org.testng.annotations.DataProvider;

public class DataProviderUtils {

	@DataProvider
	public String[][] invalidCredentialData() {
		String[][] main = new String[2][3];

		main[0][0] = "john";
		main[0][1] = "john123";
		main[0][2] = "There was an issue signing in";

		main[1][0] = "peter";
		main[1][1] = "peter12";
		main[1][2] = "There was an issue signing in";

		return main;
	}

}
