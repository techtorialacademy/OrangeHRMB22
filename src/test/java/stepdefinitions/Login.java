package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.HRMLogin;
import utils.ConfigReader;
import utils.Driver;
import utils.LoggerUtils;
import java.util.List;
import java.util.Map;
import static hooks.Hooks.driver;
public class Login {

    private static HRMLogin loginPage = new HRMLogin();

    @Given("I am on the OrangeHRM login page")
    public void i_am_on_the_orange_hrm_login_page() {
        LoggerUtils.info(((RemoteWebDriver)driver).getSessionId().toString());
        LoggerUtils.info("______________---------_____________");
        driver.get(ConfigReader.readProperty("stg_url"));
    }
    @When("User enters valid credentials")
    public void user_enters_valid_credentials(DataTable dataTable) {
        List<Map<String,String>> credentialsList =
                dataTable.asMaps(String.class,String.class);
        for (Map<String,String> creds : credentialsList){
            String username = creds.get("username");
            String password = creds.get("password");
            loginPage.usernameBox.sendKeys(username);
            loginPage.passwordBox.sendKeys(password);
            LoggerUtils.info("Valid username and password is sent.");
            loginPage.loginBtn.click();
        }

    }
    @Then("User should be redirected to dashboard page")
    public void user_should_be_redirected_to_dashboard_page() throws InterruptedException {
        LoggerUtils.info("Validating that url contains `dashboard`");
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        Thread.sleep(367);
        LoggerUtils.info("Validating that page displays `Dashboard` header");
        Assert.assertTrue(loginPage.dashboardHeader.isDisplayed());

        LoggerUtils.info("Login was successful.");

    }

    @When("User enters invalid credentials {string} {string}")
    public void user_enters_invalid_credentials(String username, String password) {
        loginPage.usernameBox.sendKeys(username);
        loginPage.passwordBox.sendKeys(password);
        LoggerUtils.info("Invalid ->username and password<- combination is sent.");

        loginPage.loginBtn.click();
    }
    @Then("User should see the {string} error message")
    public void user_should_see_the_error_message(String expectedErrorMessage) {
        LoggerUtils.info("Validating the error message element is displayed.");
        Assert.assertTrue(loginPage.invalidCredentialsWarning.isDisplayed());

        LoggerUtils.info("Validating displayed error message matches the expected.");

        String actualDisplayedErrorMessage =
                loginPage.invalidCredentialsWarning.getText().trim();
        Assert.assertEquals(expectedErrorMessage,actualDisplayedErrorMessage);

        LoggerUtils.
                info(expectedErrorMessage + " is displayed successfully.");

    }
    @When("User misses a credential field {string} or {string}")
    public void user_misses_a_credential_field_or(String username, String password) {
        if(username.isEmpty() && !password.isEmpty()){
            LoggerUtils.info("Validating when user doesn't enter username.");
            loginPage.passwordBox.sendKeys(password);
        }else if(password.isEmpty()){
            LoggerUtils.info("Validating when user doesn't enter password.");
            loginPage.usernameBox.sendKeys(username);
        }else if (username.isEmpty() && password.isEmpty()){
            LoggerUtils.info("Validating when user leaves both field empty");
            // no need to send any keys
        }

        loginPage.loginBtn.click();
    }
    @Then("User should see a {string} message for the {string} field")
    public void user_should_see_a_message_for_the_field(String requiredMsg, String missingField) {
        if(missingField.equals("username")){
            Assert.assertTrue(loginPage.requiredWarningForUsername.isDisplayed());
            String actualMsg = loginPage.requiredWarningForUsername.getText().trim();
            Assert.assertEquals(requiredMsg,actualMsg);
            LoggerUtils.info("Required message successfully displayed for missing username field. ");
        }
        else if(missingField.equals("password")){
            String actualMsg = loginPage.requiredWarningForPassword.getText().trim();
            Assert.assertEquals(requiredMsg,actualMsg);
            Assert.assertTrue(loginPage.requiredWarningForPassword.isDisplayed());
            LoggerUtils.info("Required message successfully displayed for missing password field");
        }
        else if (missingField.equals("both")){
            // Required message text is correct for username
            String actualMsgUsername = loginPage.requiredWarningForUsername.getText().trim();
            Assert.assertEquals(requiredMsg,actualMsgUsername);

            // Required message text is correct for password
            String actualMsgPassword = loginPage.requiredWarningForPassword.getText().trim();
            Assert.assertEquals(requiredMsg,actualMsgPassword);

            Assert.assertTrue(loginPage.requiredWarningForPassword.isDisplayed());
            Assert.assertTrue(loginPage.requiredWarningForUsername.isDisplayed());
            LoggerUtils.info("Required message for both missing field is successfully displayed.");
        }



    }

}
