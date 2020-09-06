package com.fullstack.batch3;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Unit test for simple App.
 */
public class AppTest {
	ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	ThreadLocal<WebDriverWait> explicitwait = new ThreadLocal<WebDriverWait>();

	@BeforeMethod(alwaysRun = true)
	public void runPreconditions() {
		// open website
		WebDriverManager.chromedriver().setup();
		driver.set(new ChromeDriver());
		driver.get().get("https://testautomasi.com");
		driver.get().manage().window().maximize();
		explicitwait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(60)));

	}

	@AfterMethod(alwaysRun = true)
	public void runPostConditions() {
		driver.get().quit();
	}

	@Test(groups = {
			"RegressionTest" }, priority = 1, description = "Verify that login is working correctly with correct username and password")

	public void loginWithCorrectCredentials() {
		String username = "fullstackdemo";
		String password = "fullstackdemo";

		// click on login link
		explicitwait.get().until(
				ExpectedConditions.visibilityOf(driver.get().findElement(By.xpath("//a[contains(text(),'Log In')]"))));
		driver.get().findElement(By.xpath("//a[contains(text(),'Log In')]")).click();

		// type username(variable) in username field

		explicitwait.get().until(ExpectedConditions.elementToBeClickable(By.id("username")));

		driver.get().findElement(By.id("username")).sendKeys(username);

		// type password(variable) in password field
		driver.get().findElement(By.id("password")).sendKeys(password);

		// click login button
		driver.get().findElement(By.xpath("//button[contains(text(),'Login')]")).click();
		// verify login is successfull with username validation
		explicitwait.get().until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'" + username + "')]")));

		String actualUsername = driver.get().findElement(By.xpath("//strong[contains(text(),'" + username + "')]"))
				.getText();

		assertEquals(actualUsername, username);

	}

	@Test(groups = { "SanityTest" }, priority = 2, description = "Verify that the title of website is correct")

	public void verifyTitle() {

		String title = driver.get().getTitle();
		assertEquals(title, "Home -");

	}

}
