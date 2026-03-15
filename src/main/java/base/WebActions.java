package base;

import core.logging.LoggerManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import types.ElementType;

import java.util.Arrays;
import java.util.Set;
import java.util.List;

import java.time.Duration;
import java.util.logging.Logger;

public class WebActions extends BasePage {

	// ─────────────────────────────────────────────────────────────────────────
// TODO: Page Load Strategy
//       Implement the ability to configure PageLoadStrategy (NORMAL, EAGER, NONE)
//       via ChromeOptions before the driver is initialized.
//       This will be handled at the Driver/Configuration setup level,
//       not inside WebActions.
// ─────────────────────────────────────────────────────────────────────────

	// Composition ('Has - A' Relationship)
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	LoggerManager logger = new LoggerManager(this.getClass());
	Actions actions = new Actions(driver);
	JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
	SoftAssert softAssert = new SoftAssert();
	Alert alert;
	String alertText;
	String currentWindowHandleID;
	Set<String> availableWindowHandleIDs;
	String currentURL;
	String pageTitle;
	WebElement firstSelectedOption;
	List<WebElement> dropdownOptions;
	boolean isMouseHeld;

	Select select;
	private String jsText;

	public WebActions(WebDriver driver) {
		super(driver);
	}

	public WebActions clickOn(WebElement button, String elementName, ElementType elementType) {
		wait.until(ExpectedConditions.elementToBeClickable(button)).click();
		logger.logMessage("info", "Clicked on the %s %s".formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions clickOn(By locator, String elementName, ElementType elementType) {
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		logger.logMessage("info", "Clicked on the %s %s.".formatted(elementName, elementType.getValue()));
		return this;
	}

	public Alert switchToAlert() {
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			alert = driver.switchTo().alert();
		} catch (TimeoutException te) {
			logger.logMessage("error", "Alert has not been appeared within the wait time...");
		} catch (NoAlertPresentException nape) {
			logger.logMessage("error", "No alert present at this moment...");
		}
		return alert;
	}

	public WebActions acceptTheAlert() {
		try {
			if (alert != null) {
				alert.accept();
				logger.logMessage("info", "Accepted the alert appeared on the screen...");
			} else {
				logger.logMessage("warn", "Not able to accept the alert as the alert reference is null...");
			}
		} catch (StaleElementReferenceException sere) {
			logger.logMessage("error", "Alert was dismissed before it could be accepted...");
		}
		return this;
	}

	public WebActions dismissTheAlert() {
		try {
			if (alert != null) {
				alert.dismiss();
				logger.logMessage("info", "Dismissed the alert appeared on the screen.");
			} else {
				logger.logMessage("warn", "Not able to dismiss the alert as the alert reference is null.");
			}
		} catch (StaleElementReferenceException sere) {
			logger.logMessage("error", "Alert was dismissed before it could be dismissed by driver.");
		}
		return this;
	}

	public WebActions getTextFromAlert() {
		try {
			if (alert != null) {
				alertText = alert.getText();
				logger.logMessage("info", "The text available on the appeared alert is " + alertText);
			} else {
				logger.logMessage("warn", "Not able to get the text from the alert as the alert reference is null.");
			}
		} catch (StaleElementReferenceException sere) {
			logger.logMessage("error", "Alert was dismissed before we fetch the text from it.");
		}
		return this;
	}

	public String getAlertText() {
		return alertText;
	}

	public WebActions provideTextInAlert(String textToBeProvided) {
		try {
			if (alert != null) {
				alert.sendKeys(textToBeProvided);
				logger.logMessage("info", "The text provided in the alert is '" + textToBeProvided + "'.");
			} else {
				logger.logMessage("warn", "Not able to provide the text in the alert as the alert reference is null.");
			}
		} catch (StaleElementReferenceException sere) {
			logger.logMessage("error", "Alert was dismissed before we provide the required text in it.");
		}
		return this;
	}

	public WebActions getCurrentWindowHandle() {
		try {
			currentWindowHandleID = driver.getWindowHandle();
			logger.logMessage("info", "Fetched the current window handle ID : " + currentWindowHandleID);
		} catch (NoSuchWindowException nswe) {
			logger.logMessage("error",
					"Not able to fetched the current window handle ID, due to some windows related issue.");
		}
		return this;
	}

	public String getCurrentWindowHandleID() {
		if (currentWindowHandleID == null || currentWindowHandleID.isEmpty()) {
			getCurrentWindowHandle();
		}
		if (currentWindowHandleID != null && !currentWindowHandleID.isEmpty()) {
			return currentWindowHandleID;
		} else {
			logger.logMessage("warn", "Current window handle ID is not available.");
			return null;
		}
	}

	public WebActions getAllAvailableWindowHandles() {
		try {
			availableWindowHandleIDs = driver.getWindowHandles();
			logger.logMessage("info", "Fetched all available window handle IDs : " + availableWindowHandleIDs);
		} catch (NoSuchWindowException nswe) {
			logger.logMessage("error",
					"Not able to fetched the available window handle IDs, due to some windows related issue.");
		}
		return this;
	}

	public Set<String> getAllWindowHandleIDs() {
		return availableWindowHandleIDs;
	}

	public WebActions switchToTargetWindow(String targetWindowID) {
		try {
			if (targetWindowID != null && !targetWindowID.isEmpty()) {
				driver.switchTo().window(targetWindowID);
				logger.logMessage("info", "Switch to the target window ID, i.e., " + targetWindowID + " successfully.");
			} else {
				logger.logMessage("warn", "Target window ID may be null or empty, so not able to switch.");
			}
		} catch (NoSuchWindowException nswe) {
			logger.logMessage("error", "Not able to switch to the target window due to some windows related issue.");
		}
		return this;
	}

	public WebActions closeTheCurrentWindow() {
		try {
			String currentWindowHandleID = this.getCurrentWindowHandleID();
			driver.close();
			logger.logMessage("info", "Closed the current window i.e., " + currentWindowHandleID);
		} catch (NoSuchWindowException nswe) {
			logger.logMessage("error", "Not able to get the current window ." + nswe.getMessage());
		}
		return this;
	}

	public WebActions switchToFrame(int frameIndex) {
		try {
			if (frameIndex >= 0) {
				driver.switchTo().frame(frameIndex);
				logger.logMessage("info", "Switch to the " + frameIndex + " index frame.");
			} else {
				logger.logMessage("warn", "Frame index can not be a negative number.");
			}
		} catch (NoSuchFrameException nsfe) {
			logger.logMessage("error",
					"There is no frame available with index no " + frameIndex + "." + nsfe.getMessage());
		}
		return this;
	}

	public WebActions switchToFrame(String frameNameOrID) {
		try {
			if (frameNameOrID != null && !frameNameOrID.isEmpty()) {
				driver.switchTo().frame(frameNameOrID);
				logger.logMessage("info", "Switch to the frame with ID/name as " + frameNameOrID);
			} else {
				logger.logMessage("warn",
						"Not able to switch to the frame as the frame reference is either null or empty.");
			}
		} catch (NoSuchFrameException nsfe) {
			logger.logMessage("error", "No such frame available in the screen. " + nsfe.getMessage());
		}
		return this;
	}

	public WebActions switchToFrame(WebElement frameElement) {
		try {
			if (frameElement != null) {
				driver.switchTo().frame(frameElement);
				logger.logMessage("info", "Switch to the required frame element.");
			} else {
				logger.logMessage("warn", "Not able to switch to the frame as the frame reference is either null.");
			}
		} catch (NoSuchFrameException nsfe) {
			logger.logMessage("error", "No such frame available in the screen. " + nsfe.getMessage());
		}
		return this;
	}

	public WebActions switchToDefaultFrame() {
		try {
			driver.switchTo().defaultContent();
			logger.logMessage("info", "Switched to the default frame successfully.");
		} catch (WebDriverException wde) {
			logger.logMessage("error", "Not able to switch to the default frame." + wde.getMessage());
		}
		return this;
	}

	public WebActions navigateTo(String url) {
		try {
			if (url != null && !url.isEmpty()) {
				driver.navigate().to(url);
				logger.logMessage("info", "Navigated to the URL " + url + " successfully.");
			} else {
				logger.logMessage("warn", "Not able to navigate as the URL reference was either null or empty.");
			}
		} catch (WebDriverException wde) {
			logger.logMessage("error",
					"Not able to navigate to the URL as either the driver may be closed or there may be some other driver specific issue happened."
							+ wde.getMessage());
		}
		return this;
	}

	public WebActions navigateBack() {
		try {
			driver.navigate().back();
			logger.logMessage("info", "Navigated back successfully.");
		} catch (WebDriverException wde) {
			logger.logMessage("error",
					"Not able to navigate back as either the driver may be closed or there may be some other driver specific issue happened."
							+ wde.getMessage());
		}
		return this;
	}

	public WebActions navigateForward() {
		try {
			driver.navigate().forward();
			logger.logMessage("info", "Navigated forward successfully.");
		} catch (WebDriverException wde) {
			logger.logMessage("error",
					"Not able to navigate forward as either the driver may be closed or there may be some other driver specific issue happened."
							+ wde.getMessage());
		}
		return this;
	}

	public WebActions refreshPage() {
		try {
			driver.navigate().refresh();
			logger.logMessage("info", "Page refreshed successfully.");
		} catch (WebDriverException wde) {
			logger.logMessage("error",
					"Not able to refresh the page as either the driver may be closed or there may be some other driver specific issue happened."
							+ wde.getMessage());
		}
		return this;
	}

	public WebActions getTheCurrentUrl() {
		try {
			currentURL = driver.getCurrentUrl();
			logger.logMessage("info", "The current URL fetched is " + currentURL);
		} catch (WebDriverException wde) {
			logger.logMessage("error",
					"Not able to get the current URL as either the driver may be close or any other driver related issue happened."
							+ wde.getMessage());
		}
		return this;
	}

	public String getTheCurrentUrlAsString() {
		if (currentURL == null || currentURL.isEmpty()) {
			getTheCurrentUrl();
		}
		return currentURL;
	}

	public WebActions getThePageTitle() {
		try {
			pageTitle = driver.getTitle();
			logger.logMessage("info", "The title of the page is " + pageTitle);
		} catch (WebDriverException wde) {
			logger.logMessage("error",
					"Not able to get the page title as either the driver may be close or any other driver related issue happened."
							+ wde.getMessage());
		}
		return this;
	}

	public String getThePageTitleAsString() {
		if (pageTitle == null || pageTitle.isEmpty()) {
			getThePageTitle();
		}
		return pageTitle;
	}

	// Dropdown handling
	public WebActions selectDropdownOptionByIndex(WebElement dropdownElement, int indexOfOption, String dropdownName) {
		try {
			if (dropdownElement != null && indexOfOption >= 0) {
				select = new Select(dropdownElement);
				select.selectByIndex(indexOfOption);
				logger.logMessage("info",
						"Selected the " + indexOfOption + " index option from the " + dropdownName + " dropdown.");
			} else {
				logger.logMessage("warn",
						"Either the dropdown element provided is null or the index no is not correct.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error", "The provided web element is not found on the web page." + nsee.getMessage());
		}
		return this;
	}

	public WebActions selectDropdownOptionByVisibleText(WebElement dropdownElement, String visibleTextOfOption,
			String dropdownName) {
		try {
			if (dropdownElement != null && visibleTextOfOption != null && !visibleTextOfOption.isEmpty()) {
				select = new Select(dropdownElement);
				select.selectByVisibleText(visibleTextOfOption);
				logger.logMessage("info",
						"Selected the " + visibleTextOfOption + " option from the " + dropdownName + " dropdown.");
			} else {
				logger.logMessage("warn",
						"Either the dropdown element provided is null or the option's visible text provided is null/empty.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error",
					"The provided web element is not found on the web page or " + "the option with text '"
							+ visibleTextOfOption + "' is not available in the " + dropdownName + " dropdown."
							+ nsee.getMessage());
		}
		return this;
	}

	public WebActions selectDropdownOptionByValue(WebElement dropdownElement, String valueOfOption,
			String dropdownName) {
		try {
			if (dropdownElement != null && valueOfOption != null && !valueOfOption.isEmpty()) {
				select = new Select(dropdownElement);
				select.selectByValue(valueOfOption);
				logger.logMessage("info",
						"Selected the " + valueOfOption + " option from the " + dropdownName + " dropdown.");
			} else {
				logger.logMessage("warn",
						"Either the dropdown element provided is null or the option's value provided is null/empty.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error",
					"The provided web element is not found on the web page or " + "the option with value '"
							+ valueOfOption + "' is not available in the " + dropdownName + " dropdown."
							+ nsee.getMessage());
		}
		return this;
	}

	public WebActions deselectDropdownOptionByIndex(WebElement multiSelectDropdownElement, int indexOfOption,
			String dropdownName) {
		try {
			if (multiSelectDropdownElement != null && indexOfOption >= 0) {
				select = new Select(multiSelectDropdownElement);
				if (select.isMultiple()) {
					select.deselectByIndex(indexOfOption);
					logger.logMessage("info", "Deselected the " + indexOfOption + " index option from the "
							+ dropdownName + " dropdown.");
				} else {
					logger.logMessage("warn", "The provided dropdown " + dropdownName
							+ " is not a multi-select dropdown, so deselect operation can not be performed.");
				}
			} else {
				logger.logMessage("warn",
						"Either the dropdown element provided is null or the index no is not correct.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error", "The provided web element is not found on the web page." + nsee.getMessage());
		}
		return this;
	}

	public WebActions deselectDropdownOptionByVisibleText(WebElement multiSelectDropdownElement,
			String visibleTextOfOption, String dropdownName) {
		try {
			if (multiSelectDropdownElement != null && visibleTextOfOption != null && !visibleTextOfOption.isEmpty()) {
				select = new Select(multiSelectDropdownElement);
				if (select.isMultiple()) {
					select.deselectByVisibleText(visibleTextOfOption);
					logger.logMessage("info", "Deselected the " + visibleTextOfOption + " option from the "
							+ dropdownName + " dropdown.");
				} else {
					logger.logMessage("warn", "The provided dropdown " + dropdownName
							+ " is not a multi-select dropdown, so deselect operation can not be performed.");
				}
			} else {
				logger.logMessage("warn",
						"Either the dropdown element provided is null or the option's visible text provided is null/empty.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error",
					"The provided web element is not found on the web page or " + "the option with text '"
							+ visibleTextOfOption + "' is not available in the " + dropdownName + " dropdown."
							+ nsee.getMessage());
		}
		return this;
	}

	public WebActions deselectDropdownOptionByValue(WebElement multiSelectDropdownElement, String valueOfOption,
			String dropdownName) {
		try {
			if (multiSelectDropdownElement != null && valueOfOption != null && !valueOfOption.isEmpty()) {
				select = new Select(multiSelectDropdownElement);
				if (select.isMultiple()) {
					select.deselectByValue(valueOfOption);
					logger.logMessage("info",
							"Deselected the " + valueOfOption + " option from the " + dropdownName + " dropdown.");
				} else {
					logger.logMessage("warn", "The provided dropdown " + dropdownName
							+ " is not a multi-select dropdown, so deselect operation can not be performed.");
				}
			} else {
				logger.logMessage("warn",
						"Either the dropdown element provided is null or the option's value provided is null/empty.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error",
					"The provided web element is not found on the web page or " + "the option with value '"
							+ valueOfOption + "' is not available in the " + dropdownName + " dropdown."
							+ nsee.getMessage());
		}
		return this;
	}

	public WebActions deselectAllDropdownOptions(WebElement multiSelectDropdownElement, String dropdownName) {
		try {
			if (multiSelectDropdownElement != null) {
				select = new Select(multiSelectDropdownElement);
				if (select.isMultiple()) {
					if (select.getAllSelectedOptions().isEmpty()) {
						logger.logMessage("warn", "No options are selected in the dropdown " + dropdownName
								+ " at this moment, so nothing to deselect.");
					} else {
						select.deselectAll();
						logger.logMessage("info", "Deselected all the options from the " + dropdownName + " dropdown.");
					}
				} else {
					logger.logMessage("warn", "The provided dropdown " + dropdownName
							+ " is not a multi-select dropdown, so deselect operation can not be performed.");
				}
			} else {
				logger.logMessage("warn", "The dropdown element provided for " + dropdownName + " is null.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error", "The provided web element i.e., " + dropdownName
					+ " is not found on the web page. " + nsee.getMessage());
		}
		return this;
	}

	public WebActions getTheFirstSelectedOption(WebElement dropdownElement, String dropdownName) {
		try {
			if (dropdownElement != null) {
				select = new Select(dropdownElement);
				firstSelectedOption = select.getFirstSelectedOption();
				logger.logMessage("info", "Got the first selected option from the " + dropdownName + " dropdown.");
			} else {
				logger.logMessage("warn", "The dropdown element provided is null.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error",
					"The provided dropdown element is not found on the web page. " + nsee.getMessage());
		}
		return this;
	}

	public WebElement getTheFirstSelectedOptionAsWebElement() {
		if (firstSelectedOption == null) {
			logger.logMessage("warn", "No option has been selected yet in the dropdown.");
			return null;
		}
		return firstSelectedOption;
	}

	public WebActions getAllDropdownOptions(WebElement dropdownElement, String dropdownName) {
		try {
			if (dropdownElement != null) {
				select = new Select(dropdownElement);
				dropdownOptions = select.getOptions();
				logger.logMessage("info", "Got all available options from the " + dropdownName + " dropdown.");
			} else {
				logger.logMessage("warn", "The dropdown element provided is null.");
			}
		} catch (NoSuchElementException nsee) {
			logger.logMessage("error",
					"The provided dropdown element is not found on the web page. " + nsee.getMessage());
		}
		return this;
	}

	public List<WebElement> getAllDropdownOptionAsWebElementList() {
		if (dropdownOptions == null) {
			logger.logMessage("warn", "Dropdown options not fetched yet, please call getAllDropdownOptions() first.");
			return null;
		} else if (dropdownOptions.isEmpty()) {
			logger.logMessage("warn", "Dropdown has no options available.");
			return dropdownOptions;
		}
		return dropdownOptions;
	}

	public WebActions mouseLeftClick(WebElement element, String elementName, ElementType elementType) {
		try {
			actions.moveToElement(element).click().perform();
			logger.logMessage("info",
					"Performed left clicked on the %s %s using mouse.".formatted(elementName, elementType.getValue()));
		} catch (MoveTargetOutOfBoundsException mtoobe) {
			logger.logMessage("error", "The target element is not on the view port now.");
		}
		return this;
	}

	public WebActions mouseDoubleClick(WebElement element, String elementName, ElementType elementType) {
		actions.moveToElement(element).doubleClick().perform();
		logger.logMessage("info",
				"Performed double click on the %s %s using mouse.".formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions mouseRightClick(WebElement element, String elementName, ElementType elementType) {
		actions.moveToElement(element).contextClick().perform();
		logger.logMessage("info",
				"Performed right click on the %s %s using mouse.".formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions mouseClickAndHold(WebElement element, String elementName, ElementType elementType) {
		actions.moveToElement(element).clickAndHold().perform();
		isMouseHeld = true;
		logger.logMessage("info",
				"Performed click and hold on the %s %s using mouse.".formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions mouseHeldRelease(WebElement element, String elementName, ElementType elementType) {
		if (isMouseHeld) {
			actions.moveToElement(element).release().perform();
			isMouseHeld = false;
			logger.logMessage("info", "Performed release from the %s %s that has been held using mouse."
					.formatted(elementName, elementType.getValue()));
		} else {
			logger.logMessage("warn", "Mouse held release is not possible as nothing is held by mouse at this moment.");
		}
		return this;
	}

	public WebActions mouseMoveToElement(WebElement element, String elementName, ElementType elementType) {
		actions.moveToElement(element).perform();
		logger.logMessage("info", "Moved the mouse over %s %s.".formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions mouseMoveToElementWithOffset(WebElement element, String elementName, ElementType elementType,
			int xOffset, int yOffset) {
		actions.moveToElement(element, xOffset, yOffset).perform();
		logger.logMessage("info", "Moved the mouse to %s %s by '%d' x-offset and '%d' y-offset.".formatted(elementName,
				elementType.getValue(), xOffset, yOffset));
		return this;
	}

	public WebActions mouseMoveByOffset(int xOffset, int yOffset) {
		actions.moveByOffset(xOffset, yOffset).perform();
		logger.logMessage("info",
				"Moved the mouse by " + xOffset + " & " + yOffset + " offset from the current position.");
		return this;
	}

	public WebActions dragAndDrop(WebElement draggableElement, String draggableElementName,
			ElementType draggableElementType, WebElement targetDropElement, String targetDropElementName,
			ElementType targetDropElementType) {
		actions.dragAndDrop(draggableElement, targetDropElement).perform();
		logger.logMessage("info", "Drag the %s %s and drop it to the %s %s".formatted(draggableElementName,
				draggableElementType.getValue(), targetDropElementName, targetDropElementType.getValue()));
		return this;
	}

	public WebActions dragAndDropByOffset(WebElement draggableElement, String draggableElementName,
			ElementType draggableElementType, int xOffset, int yOffset) {
		actions.dragAndDropBy(draggableElement, xOffset, yOffset).perform();
		logger.logMessage("info", "Drag and drop the %s %s by %d & %d from x Offset and y Offset."
				.formatted(draggableElementName, draggableElementType.getValue(), xOffset, yOffset));
		return this;
	}

	public WebActions scrollToElement(WebElement element, String elementName, ElementType elementType) {
		actions.scrollToElement(element).perform();
		logger.logMessage("info", "Scrolled the page until we reach to the element %s %s in in view."
				.formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions scrollByAmount(int xOffset, int yOffset) {
		actions.scrollByAmount(xOffset, yOffset).perform();
		logger.logMessage("info", "Scrolled the page by %d & %d pixel from x offset and y offset respectively."
				.formatted(xOffset, yOffset));
		return this;
	}

	public WebActions scrollFromOrigin(WebElement element, String elementName, ElementType elementType, int xOffset,
			int yOffset) {
		WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(element);
		actions.scrollFromOrigin(scrollOrigin, xOffset, yOffset).perform();
		logger.logMessage("info",
				"Scrolled the page from %s %s by %d & %d pixel from x offset and y offset respectively."
						.formatted(elementName, elementType.getValue(), xOffset, yOffset));
		return this;
	}

	public WebActions performKeyBoardActions(Keys[] keys, String targetKey) {
		for (Keys key : keys) {
			actions.keyDown(key);
		}
		actions.sendKeys(targetKey);
		for (int i = keys.length - 1; i >= 0; i--) {
			actions.keyUp(keys[i]);
		}
		actions.perform();
		logger.logMessage("info", "Performed the required keyboard action successfully.");
		return this;
	}

	public WebActions performKeyBoardActionsOnWebElement(WebElement element, String elementName,
			ElementType elementType, Keys[] keys, String targetKey) {
		actions.moveToElement(element).click();
		for (Keys key : keys) {
			actions.keyDown(key);
		}
		actions.sendKeys(targetKey);
		for (int i = keys.length - 1; i >= 0; i--) {
			actions.keyUp(keys[i]);
		}
		actions.perform();
		logger.logMessage("info", "Performed the required keyboard action successfully on %s %s.".formatted(elementName,
				elementType.getValue()));
		return this;
	}

	public WebActions sendKeys(WebElement element, String elementName, ElementType elementType, String text) {
		element.sendKeys(text);
		logger.logMessage("info",
				"Provided text value '%s' in the %s %s.".formatted(text, elementName, elementType.getValue()));
		return this;
	}

	public WebActions clickUsingJS(WebElement element, String elementName, ElementType elementType) {
		javascriptExecutor.executeScript("arguments[0].click();", element);
		logger.logMessage("info", "Clicked on %s %s.".formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions scrollIntoView(WebElement element, String elementName, ElementType elementType) {
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
		logger.logMessage("info", "Scrolled %s %s into view using JS.".formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions highlightElement(WebElement element, String elementName, ElementType elementType) {
		javascriptExecutor.executeScript("arguments[0].style.border='3px solid red';", element);
		logger.logMessage("info", "Highlighted %s %s using JS.".formatted(elementName, elementType.getValue()));
		return this;
	}

	public WebActions getTextUsingJS(WebElement element, String elementName, ElementType elementType) {
		jsText = (String) javascriptExecutor.executeScript("return arguments[0].innerText;", element);
		logger.logMessage("info",
				"Got text from %s %s using JS: %s".formatted(elementName, elementType.getValue(), jsText));
		return this;
	}

	public String getJsText() {
		return jsText;
	}

	public WebActions setValueUsingJS(WebElement element, String elementName, ElementType elementType, String value) {
		javascriptExecutor.executeScript("arguments[0].value=arguments[1];", element, value);
		logger.logMessage("info",
				"Set value '%s' on %s %s using JS.".formatted(value, elementName, elementType.getValue()));
		return this;
	}
}
