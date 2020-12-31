package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class NoteTabPage {
    //Notes Tab
    @FindBy(id="nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id="add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id="delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id="note-title")
    private WebElement noteTitleField;

    @FindBy(id="note-description")
    private WebElement noteDescriptionField;

    //modal footer
    @FindBy(id="note-modal-submit")
    private WebElement noteSubmitButton;

    @FindBy(id="notetitle")
    private List<WebElement> titleList;

    @FindBy(id="notedescription")
    private List<WebElement> descriptionList;


    public NoteTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public List<String> getNoteDetail(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();
        noteTab.click();
        wait.until(ExpectedConditions.visibilityOf(addNoteButton));
        if (titleList.size() == 0) {
            return null;
        }
        List<String> detail = new ArrayList<>(List.of(titleList.get(0).getText(),
                descriptionList.get(0).getText()));
        return detail;
    }


    public void addNote(WebDriver driver, String title, String description) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();

        wait.until(ExpectedConditions.elementToBeClickable(noteTitleField)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionField)).sendKeys(description);

        wait.until(ExpectedConditions.elementToBeClickable(noteSubmitButton)).click();
    }

    public void editNote(WebDriver driver, String title, String description) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();

        wait.until(ExpectedConditions.elementToBeClickable(noteTitleField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleField)).sendKeys(title);

        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionField)).sendKeys(description);

        wait.until(ExpectedConditions.elementToBeClickable(noteSubmitButton)).click();
    }

    public void deleteNote(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
    }

}
