package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	public String baseURL;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;

		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get(baseURL + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		// Assertions.assertTrue(driver.findElement(By.id("success-signup-msg")).getText().contains("You successfully signed up!"));
	}

	private void doMockCreateNote(String title, String description, Integer noteid) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// tab notes
		WebElement tabNoteButton = driver.findElement(By.id("nav-notes-tab"));
		tabNoteButton.click();

		// Create a new note
		String buttonId = "addNewNote";
		if(noteid > 0) {
			buttonId = "editNote1";
		}

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(buttonId)));
		WebElement addNoteButton = driver.findElement(By.id(buttonId));
		addNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement titleInput = driver.findElement(By.id("note-title"));
		titleInput.click();
		titleInput.clear();
		titleInput.sendKeys(title);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement descriptionInput = driver.findElement(By.id("note-description"));
		descriptionInput.click();
		descriptionInput.clear();
		descriptionInput.sendKeys(description);

		if(noteid > 0) {
			WebElement noteidInput = driver.findElement(By.id("note-id"));
			String script = "arguments[0].setAttribute('value', arguments[1])";
			((JavascriptExecutor) driver).executeScript(script, noteidInput, noteid);
		}

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change-note")));
		WebElement saveButton = driver.findElement(By.id("save-change-note"));
		saveButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("goToHome")));

		WebElement goToHomeButton = driver.findElement(By.id("goToHome"));
		goToHomeButton.click();
	}

	private void doMockCreateCredential(String url, String username, String password) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// tab credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement tabCredentialButton = driver.findElement(By.id("nav-credentials-tab"));
		tabCredentialButton.click();

		// Create a new note
		String buttonId = "create-new-credential";
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(buttonId)));
		WebElement addCredentialButton = driver.findElement(By.id(buttonId));
		addCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement urlInput = driver.findElement(By.id("credential-url"));
		urlInput.click();
		urlInput.clear();
		urlInput.sendKeys(url);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement usernameInput = driver.findElement(By.id("credential-username"));
		usernameInput.click();
		usernameInput.clear();
		usernameInput.sendKeys(username);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement passwordInput = driver.findElement(By.id("credential-password"));
		passwordInput.click();
		passwordInput.clear();
		passwordInput.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change-credential")));
		WebElement saveButton = driver.findElement(By.id("save-change-credential"));
		saveButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("goToHome")));
		WebElement goToHomeButton = driver.findElement(By.id("goToHome"));
		goToHomeButton.click();
	}
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get(baseURL+ "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * create new note
	 **/


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		assertEquals(baseURL + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get(baseURL+ "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/**
	 * Verify that the home page is not accessible without logging in.
	 */
	@Test
	public void testHomePageNotAccessibleWithoutLogin() {
		// Navigate to the home page
		driver.get(baseURL + "/home");

		// Verify that the login page is displayed
		// comment out by ThanhTLN
		assertEquals("Login", driver.getTitle());
	}

	/**
	 * Verify login logout.
	 */
	@Test
	public void testSignUpLoginAndLogout() {
		// Sign up a new user
		doMockSignUp("ssss", "asdasdas", "thanh1234", "123456");

		// Log in
		doLogIn("thanh1234", "123456");

		// Access the home page
		driver.get(baseURL + "/home");

		// Verify that the home page is accessible
		assertEquals("Home", driver.getTitle());

		// Log out
		WebElement logoutLink = driver.findElement(By.id("logout-system"));
		logoutLink.click();

		// Verify that the home page is no longer accessible after logging out
		assertEquals("Login", driver.getTitle());
	}

	/**
	 * Write a test that creates a note, and verifies it is displayed.
	 */
	@Test
	public void testCreateAndViewNote() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// Sign up a new user
		doMockSignUp("Tran", "Thanh", "thanh1234", "123456");
		// Log in
		doLogIn("thanh1234", "123456");

		// create new note
		doMockCreateNote("test a note", "this is a note", 0);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		// tab notes
		WebElement tabNoteButton = driver.findElement(By.id("nav-notes-tab"));
		tabNoteButton.click();

		// Verify the note is displayed
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitleDisplay1")));
		WebElement noteTitle = driver.findElement(By.id("noteTitleDisplay1"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescriptionDisplay1")));
		WebElement noteDescription = driver.findElement(By.id("noteDescriptionDisplay1"));

		assertEquals("test a note", noteTitle.getText());
		assertEquals("this is a note", noteDescription.getText());
	}

	/**
	 * Write a test that edits an existing note and verifies that the changes are displayed.
	 */
	@Test
	public void testEditNote() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// Sign up a new user
		doMockSignUp("Tran", "Thanh", "thanh1234", "123456");
		// Log in
		doLogIn("thanh1234", "123456");

		// create new note
		doMockCreateNote("test a note", "this is a note", 0);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		// tab notes
		WebElement tabNoteButton = driver.findElement(By.id("nav-notes-tab"));
		tabNoteButton.click();

		// edit note
		doMockCreateNote("update test a note", "update this is a note", 1);

		// Verify the changes are displayed
		// tab notes
		WebElement tabNoteButton2 = driver.findElement(By.id("nav-notes-tab"));
		tabNoteButton2.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteTitleDisplay1")));
		WebElement noteTitle = driver.findElement(By.id("noteTitleDisplay1"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteDescriptionDisplay1")));
		WebElement noteDescription = driver.findElement(By.id("noteDescriptionDisplay1"));

		assertEquals("update test a note", noteTitle.getText());
		assertEquals("update this is a note", noteDescription.getText());
	}

	/**
	 * Write a test that deletes a note and verifies that the note is no longer displayed.
	 */
	@Test
	// comment out by thanhtln
	public void testDeleteNote() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// Sign up a new user
		doMockSignUp("Tran", "Thanh", "thanh1234", "123456");
		// Log in
		doLogIn("thanh1234", "123456");

		// create new note
		doMockCreateNote("test a note", "this is a note", 0);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		// tab notes
		WebElement tabNoteButton = driver.findElement(By.id("nav-notes-tab"));
		tabNoteButton.click();

		// Delete an existing note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNote1")));
		WebElement deleteButton = driver.findElement(By.id("deleteNote1"));
		deleteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-delete-confirm")));
		WebElement confirmButton = driver.findElement(By.id("btn-delete-confirm"));
		confirmButton.click();

		// Verify that the note is no longer displayed
		List<WebElement> noteTitles = driver.findElements(By.id("noteTitleDisplay1"));
		List<WebElement> noteDescriptions = driver.findElements(By.id("noteDescriptionDisplay1"));

		Assertions.assertTrue(noteTitles.isEmpty());
		Assertions.assertTrue(noteDescriptions.isEmpty());
	}

	@Test
	public void testCreateCredential() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		// Sign up a new user
		doMockSignUp("Tran", "Thanh", "thanh1234", "123456");
		// Log in
		doLogIn("thanh1234", "123456");

		// create new credential
		doMockCreateCredential("http://localhost:8182/home", "thanh1234", "123456");

		// tab credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement tabCredentialButton = driver.findElement(By.id("nav-credentials-tab"));
		tabCredentialButton.click();

		// Verify that the credential details are visible in the list
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUrl1")));
		WebElement credentialUrl = driver.findElement(By.id("credentialUrl1"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialUsername1")));
		WebElement credentialUsername = driver.findElement(By.id("credentialUsername1"));

		assertEquals("http://localhost:8182/home", credentialUrl.getText());
		assertEquals("thanh1234", credentialUsername.getText());
	}
}
