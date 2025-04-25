package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

import java.util.List;

public class HRMAdmin {
    public HRMAdmin(){
        PageFactory.initElements(Driver.getDriver(),this);
    }
    @FindBy(xpath = "//a[@href=\"/web/index.php/admin/viewAdminModule\"]")
    public WebElement navigateToAdminPage;

    @FindBy(xpath = "//button[contains(normalize-space(), \"Add\")]")
    public WebElement addUserBtn;

    @FindBy(xpath = "//input[@placeholder=\"Type for hints...\"]")
    public WebElement employeeNameBox; // In this box we have to choose one of the
    // existing employee names.

    @FindBy(xpath = "//label[.=\"Username\"]/../following-sibling::div/input")
    public WebElement usernameBox;

    @FindBy(xpath = "//label[.=\"Password\"]/../following-sibling::div/input")
    public WebElement passwordBox;

    @FindBy(xpath = "//label[.=\"Confirm Password\"]/../following-sibling::div/input")
    public WebElement confirmPswdBox;

    @FindBy(xpath = "//div[@class=\"oxd-form-actions\"]//button[.=\" Cancel \"]")
    public WebElement cancelFormBtn;

    @FindBy(xpath = "//div[@class=\"oxd-form-actions\"]//button[.=\" Save \"]")
    public WebElement saveFormBtn;

    @FindBy(xpath = "//div[@class=\"oxd-table-card\"]")
    public WebElement tableRow;

    @FindBy(xpath = "//div[@class=\"oxd-table-card\"]/div/div[2]/div")
    public List<WebElement> usernameElements;

    @FindBy(xpath = "//span[contains(.,' Records Found')]")
    public WebElement numberOfRecordsElement;

    @FindBy(xpath = "//div[@role=\"listbox\"]//div/span")
    public List<WebElement> userRoleOptions;

    @FindBy(xpath = "//div[@role=\"listbox\"]//div/span")
    public List<WebElement> statusElements;

    @FindBy(xpath = "//div[@class=\"oxd-toast-content oxd-toast-content--success\"]/p[1]")
    public WebElement successMessageHeader;

    @FindBy(xpath = "//div[@class=\"oxd-toast-content oxd-toast-content--success\"]/p[2]")
    public  WebElement successfullySaved;

    @FindBy(xpath = "//label[.=\"User Role\"]/../following-sibling::div")
    public WebElement extendUserRoleDropDown;

    @FindBy(xpath = "//label[.=\"Status\"]/../following-sibling::div")
    public WebElement extendStatusDropDown;

    public void chooseStatus(String status){
       extendStatusDropDown.click();
        for(WebElement statusOption : statusElements) {
            if (statusOption.getText().trim().equalsIgnoreCase(status)) {
                statusOption.click();
                return;
            }
        }

    }

    public void chooseRole(String userRole){
        extendUserRoleDropDown.click();
        for(WebElement userRoleOptionElement : userRoleOptions){
            if (userRoleOptionElement.getText().trim().equalsIgnoreCase(userRole)){
                userRoleOptionElement.click();
                return;
            }
        }
    }

    public WebElement returnElementFromRow(String usernameText){
       for(WebElement username: usernameElements){
           if(username.getText().trim().equalsIgnoreCase(usernameText)){
               return username;
           }
       }
       return null;
    }


    public List<WebElement> returnRowElements(WebDriver driver, String usernameText){
        String xpathForRowElements=
                "//div[@class=\"oxd-table-card\"]/div/div[2]/div[.=\""+usernameText+"\"]/../..//div/div";
        List<WebElement> rowElements
                = driver.findElements(By.xpath(xpathForRowElements));

        return rowElements;

    }
/// //div[@class="oxd-table-card"]/div/div[2]/div[.='"+username+"']/../..//div[@class="oxd-table-cell-actions"]/button
public List<WebElement> returnRowActions(WebDriver driver, String usernameText){
    // This method is going to get the row actions for the username given.
    // first element is delete button, second element is edit button.
    String xpathForRowActions=
"//div[@class='oxd-table-card']/div/div[2]/div[.='"+usernameText+"']/../..//div[@class='oxd-table-cell-actions']/button";
    List<WebElement> rowActions
            = driver.findElements(By.xpath(xpathForRowActions));

    return rowActions;

}








}
