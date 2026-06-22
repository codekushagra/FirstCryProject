package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src//test//resources//features",
    glue = {"stepDefinations", "hooks"},
    tags = "@login or @filterByPrice",
    plugin = {
        "pretty", 
        "html:target/cucumber.html", 
        "json:target/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }
)
public class TestRunner extends AbstractTestNGCucumberTests {
//	@DataProvider(parallel = true)
//	public Object[][] scenarios(){
//		return super.scenarios();
//	}
//	
}
