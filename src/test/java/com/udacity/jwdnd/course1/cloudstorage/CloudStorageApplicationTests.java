package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private String baseUrl;

	private WebDriver driver;

	public SignupPage signupPage;
	public LoginPage loginPage;
	public HomePage homePage;
	public NoteTabPage noteTabPage;
	public CredentialTabPage credentialTabPage;

	String firstname = "Eric";
	String lastname = "Neal";
	String username = "admin";
	String password = "test123";


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}


	@Test
	@Order(1)
	public void testUnauthorizedAccess(){
		// redirected to login when not logged in and
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		// invalid requests redirected to login page
		driver.get(baseUrl + "/ofsdf");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseUrl + "/note/add");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseUrl + "/credential/delete/1");
		Assertions.assertEquals("Login", driver.getTitle());

		//login with wrong password
		driver.get(baseUrl + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, "password");

		// login unsuccessful and should not be able to access home
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	//login helper
	public void signupAndLogin() {
		//sign up new user
		driver.get(baseUrl + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);

		// login
		driver.get(baseUrl + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	@Test
	@Order(2)
	public void testValidSignupLoginAccessHomeAndLogout() throws InterruptedException {
		signupAndLogin();
		//access home
		Assertions.assertEquals("Home", driver.getTitle());

		//logout
		homePage = new HomePage(driver);
		homePage.logout(driver);

		//redirected to login page
		Thread.sleep(1000);
		Assertions.assertEquals("Login", driver.getTitle());

		//unable to access home -> redirected to login
		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}


	@Test
	@Order(3)
	public void testEditNoteAndDisplay() throws InterruptedException {
		signupAndLogin();

		//Add note
		noteTabPage = new NoteTabPage(driver);
		String noteTitle = "Project 1";
		String noteDescription = "Cloud drive";
		noteTabPage.addNote(driver, noteTitle, noteDescription);
		driver.get(baseUrl + "/result");
		driver.get(baseUrl + "/home");

		//Check added note
		List<String> noteDetail = noteTabPage.getNoteDetail(driver);
		Assertions.assertEquals(noteTitle, noteDetail.get(0));
		Assertions.assertEquals(noteDescription, noteDetail.get(1));

		//Editing note description
		driver.get(baseUrl + "/home");
		noteDescription= "Cloud drive and test";
		noteTabPage.editNote(driver, noteTitle, noteDescription);

		//Check edited note
		driver.get(baseUrl + "/home");
		noteDetail = noteTabPage.getNoteDetail(driver);
		Assertions.assertEquals(noteTitle, noteDetail.get(0));
		Assertions.assertEquals(noteDescription, noteDetail.get(1));

		//Delete note
		noteTabPage.deleteNote(driver);

		//check the note is deleted
		driver.get(baseUrl + "/home");
		Assertions.assertEquals(null, noteTabPage.getNoteDetail(driver));

	}

	@Test
	@Order(4)
	public void testAddEditDeleteCredentials() throws InterruptedException {
		signupAndLogin();

		//Add credential
		credentialTabPage = new CredentialTabPage(driver);
		String url = "www.google.com";
		username = "test user";
		password = "test pass";
		credentialTabPage.addCredential(driver, url, username, password);
		driver.get(baseUrl + "/home");

		//Check added credential
		List<String> credentialDetail = credentialTabPage.getCredentialDetail(driver);
		Assertions.assertEquals(url, credentialDetail.get(0));
		Assertions.assertEquals(username, credentialDetail.get(1));
		//password is plain text, detail.get(2) is encoded
		Assertions.assertNotEquals(password, credentialDetail.get(2));

		//Editing the username only
		driver.get(baseUrl + "/home");
		String newUsername = "new username";
		credentialTabPage.editCredential(driver, url, newUsername, password);

		//Check edited credential
		driver.get(baseUrl + "/home");
		credentialDetail = credentialTabPage.getCredentialDetail(driver);
		Assertions.assertEquals(url, credentialDetail.get(0));
		Assertions.assertEquals(newUsername, credentialDetail.get(1));
		//password is plain text, detail.get(2) is encoded
		Assertions.assertNotEquals(password, credentialDetail.get(2));

		//Delete credential
		credentialTabPage.deleteCredential(driver);

		// check the credential is deleted
		driver.get(baseUrl + "/home");
		Assertions.assertEquals(null, credentialTabPage.getCredentialDetail(driver));
	}
}
