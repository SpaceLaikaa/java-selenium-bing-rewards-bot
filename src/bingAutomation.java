import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class bingAutomation {

    private WebDriver driver;
    private final String EDGE_DRIVER_PATH = "C:\\Users\\ardaa\\Desktop\\inteli_framworks\\msedgedriver\\msedgedriver.exe";

    private final String[] SEARCH_TERMS = {
            "Meshuggah 'Immutable' review", "Java for web scraping tutorial",
            "Izmir University of Economics software", "Internship opportunities after SAT",
            "Long-distance girlfriend gift"
    };

    @BeforeMethod(onlyForGroups = {"desktop"})
    public void setupDesktop() {
        System.setProperty("webdriver.edge.driver", EDGE_DRIVER_PATH);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeMethod(onlyForGroups = {"mobile"})
    public void setupMobile() {
        System.setProperty("webdriver.edge.driver", EDGE_DRIVER_PATH);
        EdgeOptions options = new EdgeOptions();

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone X");

        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Mobile emulation started (Edge).");
    }

    @Test(groups = {"desktop"})
    public void performDesktopSearches() throws InterruptedException {
        performSearches(20, "Desktop");
    }

    @Test(groups = {"mobile"})
    public void performMobileSearches() throws InterruptedException {
        performSearches(15, "Mobile");
    }

    private void performSearches(int searchCount, String type) throws InterruptedException {
        Random random = new Random();

        for (int i = 0; i < searchCount; i++) {
            String query = SEARCH_TERMS[random.nextInt(SEARCH_TERMS.length)];
            driver.get("https://www.bing.com");

            System.out.println(type + " Search (" + (i + 1) + "/" + searchCount + "): " + query);

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(query);
            searchBox.submit();

            int delay = random.nextInt(3000) + 1500;
            Thread.sleep(delay);
        }
        System.out.println(type + " search loop completed.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}