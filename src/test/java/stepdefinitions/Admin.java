package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pages.HRMAdmin;
import utils.LoggerUtils;
import static hooks.Hooks.driver;

import java.security.Key;
import java.util.List;

public class Admin {
    private static HRMAdmin adminPage = new HRMAdmin();

    @When("User navigates to the Admin User Management page")
    public void user_navigates_to_the_admin_user_management_page() {
        LoggerUtils.info("Navigating to the Admin - User Management Page ...");
        adminPage.navigateToAdminPage.click();

    }
    @When("I click on the Add button")
    public void i_click_on_the_add_button() {
        LoggerUtils.info("On the Admin - User Management Page, clicking add button...");
        adminPage.addUserBtn.click();
    }
    @When("I enter user details {string} {string} {string} {string} {string}")
    public void i_enter_user_details(String userRole, String userName, String employeeName, String status, String password) {
        LoggerUtils.info("Filling out add user form.");

        adminPage.chooseRole(userRole);
        adminPage.chooseStatus(status);

        adminPage.employeeNameBox.sendKeys(employeeName );
        driver.findElement(By.xpath("//span[.='"+employeeName+"']")).click();


        adminPage.usernameBox.sendKeys(userName);
        adminPage.passwordBox.sendKeys(password);
        adminPage.confirmPswdBox.sendKeys(password);

    }
    @When("I click the Save button")
    public void i_click_the_save_button() {
        LoggerUtils.info("Saving the user...");
        adminPage.saveFormBtn.click();


    }
    @Then("I should see a success message")
    public void i_should_see_a_success_message() {
        Assert.assertTrue(
                adminPage.successMessageHeader.isDisplayed()
        );
        Assert.assertTrue(
                adminPage.successfullySaved.isDisplayed()
        );

        LoggerUtils.info("Success message after saving the form is successfully displayed.");
    }
    @Then("the new user should be listed with following information {string} {string} {string} {string} {string}")
    public void the_new_user_should_be_listed_with_following_information(String userRole, String userName, String employeeName, String status, String password) {
        List<WebElement> rowForTheUser =
                adminPage.returnRowElements(driver,userName);

        // first is checkbox
        // second is checkbox
        // third is username
        // fourth is userRole
        // fifth is employeeName
        // sixth is status
        Assert.assertTrue(
                rowForTheUser.get(2).getText().trim().equals(userName)
        );
        Assert.assertTrue(
                rowForTheUser.get(3).getText().trim().equals(userRole)
        );  Assert.assertTrue(
                rowForTheUser.get(4).getText().trim().equals(employeeName)
        );  Assert.assertTrue(
                rowForTheUser.get(5).getText().trim().equals(status)
        );

        LoggerUtils.info("Validated that saved user is displayed correctly");


    }




    @When("I click edit for user {string}")
    public void i_click_edit_for_user(String username) {
       List<WebElement> edit_delete =  adminPage.returnRowActions(driver,username);
       edit_delete.get(1).click(); // clicking on edit for the username

    }
    @When("I modify the user details {string} {string}")
    public void i_modify_the_user_details(String status, String userRole) {
        adminPage.chooseRole(userRole);
        adminPage.chooseStatus(status);
        adminPage.saveFormBtn.click();


    }
    @Then("the user should be listed with following information {string} {string} {string} {string}")
    public void the_user_should_be_listed_with_following_information(String userRole, String userName, String employeeName, String status) {
        List<WebElement> rowForTheUser =
                adminPage.returnRowElements(driver,userName);
        Assert.assertTrue(
                rowForTheUser.get(2).getText().trim().equals(userName)
        );
        Assert.assertTrue(
                rowForTheUser.get(3).getText().trim().equals(userRole)
        );  Assert.assertTrue(
                rowForTheUser.get(4).getText().trim().equals(employeeName)
        );  Assert.assertTrue(
                rowForTheUser.get(5).getText().trim().equals(status)
        );
    }

}
