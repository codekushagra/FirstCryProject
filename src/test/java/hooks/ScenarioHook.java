package hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
//java -jar src/main/resources/selenium-server-4.44.0.jar standalone
import org.openqa.selenium.chrome.*;
import java.io.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;

import base.Base;
import io.cucumber.java.*;
import io.qameta.allure.Allure;

public class ScenarioHook extends Base {
	private static final Logger log = LogManager.getLogger(ScenarioHook.class);
	RemoteWebDriver remoteDriver;

	@Before
	public void setup(Scenario scenario) {
		log.info("Starting scenario: " + scenario.getName());
		try {
			String browser = this.getBrowser();
			log.info("Configuring browser: " + browser);
			if(browser.equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				java.util.Map<String, Object> prefs = new java.util.HashMap<>();
				prefs.put("profile.default_content_setting_values.geolocation", 2);
				prefs.put("profile.default_content_setting_values.notifications", 2);
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-infobars");
				options.setCapability("platformName", "Windows");
				driver = new ChromeDriver(options);
				log.info("Chrome driver initialized: " + driver);
			}
			else if(browser.equals("edge")) {
				driver = new EdgeDriver();
				EdgeOptions options = new EdgeOptions();
				options.setCapability("platformName","Windows");
				log.info("Edge driver initialized.");
				remoteDriver = new RemoteWebDriver( new URL("http://localhost:4444"),options);
			}
			setDriver(remoteDriver);
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			getDriver().manage().window().maximize();
			log.info("Browser window maximized.");
		} catch(Exception e) {
			log.error("Exception in setup: ", e);
		}
	}

	@After
	public void tearDown(Scenario scenario)  {
		log.info("TearDown: scenario status is " + scenario.getStatus());
		if(!scenario.isFailed() && getDriver()!=null) {
			log.info("Scenario passed, taking screenshot...");
			byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
			Allure.addAttachment("Screenshot","image/png",new ByteArrayInputStream(screenshot),".png");
			log.info("Screenshot attached to Allure.");
		}
		
		if(getDriver()!=null) {
			log.info("Quitting WebDriver...");
			getDriver().quit();
			log.info("WebDriver quit successfully.");
		}
	}
}
