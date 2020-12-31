package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CredentialTabPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredential;

    @FindBy(id = "add-credential")
    private WebElement addButton;

    @FindBy(id = "edit-credential")
    private WebElement editButton;

    @FindBy(id="credentialSubmit")
    private WebElement saveButton;

    @FindBy(id = "delete-credential")
    private WebElement deleteButton;

    @FindBy(id = "credUrl")
    private List<WebElement> urlList;

    @FindBy(id = "credUsername")
    private List<WebElement> usernameList;

    @FindBy(id = "credPassword")
    private List<WebElement> passwordList;

    @FindBy(id = "credential-url")
    private WebElement inputUrl;

    @FindBy(id = "credential-username")
    private WebElement inputUsername;

    @FindBy(id = "credential-password")
    private WebElement inputPassword;

    //modal footer
    @FindBy(id = "credential-modal-submit")
    private WebElement submitModalButton;

    public CredentialTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public List<String> getCredentialDetail(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOf(navCredential)).click();
        navCredential.click();
        wait.until(ExpectedConditions.visibilityOf(addButton));
        if (urlList.size() == 0) {
            return null;
        }
        List<String> detail = new ArrayList<>(List.of(urlList.get(0).getText(),
                usernameList.get(0).getText(),
                passwordList.get(0).getText()));

        return detail;
    }


    public void addCredential(WebDriver driver, String url, String username, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(navCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputUrl))
                .sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(inputUsername))
                .sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword))
                .sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(submitModalButton)).click();
    }

    public void editCredential(WebDriver driver, String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(navCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(inputUrl)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputUrl))
                .sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(inputUsername)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputUsername))
                .sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword))
                .sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(submitModalButton)).click();
    }

    public void deleteCredential(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }

}