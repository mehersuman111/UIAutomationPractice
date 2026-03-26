package pages.entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.WebActions;
import types.ElementType;

public class HomePage extends WebActions {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[normalize-space(text())='Sign out']/..")
	private WebElement logOutButton;

	public HomePage chechLogoutButtonAppeared(boolean shouldDisplayed) {
		isDisplayed(logOutButton, "Logout", ElementType.BUTTON, shouldDisplayed);
		return this;
	}
}