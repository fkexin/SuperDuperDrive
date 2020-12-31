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
    private WebElement credentialTab;

    @FindBy(id = "add-credential")
    private WebElement addCredentialButton;

    @FindBy(id = "edit-credential")
    private WebElement editCredentialButton;

    @FindBy(id = "delete-credential")
    private WebElement deleteCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement urlField;

    @FindBy(id = "credential-username")
    private WebElement usernameField;

    @FindBy(id = "credential-password")
    private WebElement passwordField;

    //modal footer
    @FindBy(id = "credential-modal-submit")
    private WebElement credentialSubmitButton;

    @FindBy(id = "credUrl")
    private List<WebElement> urlList;

    @FindBy(id = "credUsername")
    private List<WebElement> usernameList;

    @FindBy(id = "credPassword")
    private List<WebElement> passwordList;


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
        wait.until(ExpectedConditions.visibilityOf(credentialTab)).click();
        credentialTab.click();
        wait.until(ExpectedConditions.visibilityOf(addCredentialButton));
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
        wait.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addCredentialButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(urlField)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(credentialSubmitButton)).click();
    }

    public void editCredential(WebDriver driver, String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(urlField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(urlField)).sendKeys(url);
        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(credentialSubmitButton)).click();
    }

    public void deleteCredential(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteCredentialButton)).click();
    }

}