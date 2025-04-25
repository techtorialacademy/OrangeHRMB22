package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions","hooks"}, // path to stepdefinitions and hooks
        dryRun = false, // to get the unimplemented steps without running the script.
        plugin = {
                "pretty", // Prints gherkin steps on the console
                "html:target/HRM-AutomationReport.html" // to create an html report
        }

)

public class TestRunner { }
