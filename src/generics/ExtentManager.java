package generics;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.relevantcodes.extentreports.ExtentReports;


public class ExtentManager{
	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null) {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			extent=new ExtentReports(System.getProperty("user.dir")+"/ExtentReports/"+"InteliServe Test Automation Report" + timeStamp + ".html", true);
			extent.loadConfig(new File(System.getProperty("user.dir")+"/testdata/ReportsConfig.xml")); 
			extent.loadConfig(new File("./testdata/ReportsConfig.xml"));
			extent.addSystemInfo("Selenium Version", "3.7.1").addSystemInfo(
					"Environment", "InteliServe_Test").addSystemInfo("User Name", "Vishal Madhur");
		}
		return extent;
	}

}
