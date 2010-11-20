package com.loja.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ReportUtilTest {

	@Test
	public void formatDate() {
		Date data = new Date();
		ReportUtil reportUtil = new ReportUtil();
		reportUtil.setData(data);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		String expected = formatter.format(data);
		Assert.assertEquals(reportUtil.getFormatedDate(), expected);
	}
}
