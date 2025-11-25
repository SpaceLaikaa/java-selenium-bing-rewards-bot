import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.JavascriptExecutor;

public class bingAutomation {

    private WebDriver driver;
    private final String EDGE_DRIVER_PATH = "C:\\Users\\ardaa\\Desktop\\inteli_framworks\\msedgedriver\\msedgedriver.exe";

    private final String[] SEARCH_TERMS = {
            "Java Spring Boot vs Django performance", "Python vs Java for backend",
            "Izmir University of Economics Software Master's Degree", "Software Engineering internship opportunities 2026",
            "What is a RESTful API in Java", "Design Patterns in Java Singleton",
            "Excel VBA automation with Python", "HTML CSS Grid vs Flexbox",
            "Java Garbage Collection explained", "Big O Notation algorithms",
            "Clean Code principles Martin Fowler", "How to use Selenium with TestNG",
            "Modern web scraping techniques Python", "Docker containers for Java applications",
            "Difference between list and tuple Python",
            "best practices for git commit messages",
            "Java multithreading tutorial", "Cloud computing services comparison AWS Azure",
            "Meshuggah 'Immutable' album review", "Meshuggah best guitar riffs",
            "Meshuggah 8-string tuning setup", "Fredrik Thordendal gear setup",
            "Extreme Progressive Metal bands 2025", "Best metal drummers of all time",
            "How to read electric guitar tabs", "Guitar distortion pedal review 2025",
            "Polyrhythms in metal music explained", "Jazz fusion guitarists",
            "DIY custom electric guitar wiring", "Why Meshuggah uses 8 string guitars",
            "best plugins for metal guitar mixing", "Fractal Audio Axe-Fx III presets",
            "top 10 instrumental progressive metal songs", "Polyphia guitar techniques",
            "electric guitar scales chart",
            "current US dollar to TL exchange rate", "best new sci-fi movies 2025",
            "Super Lig match highlights last week", "2026 world cup qualifiers Turkey matches",
            "best coffee brewing methods", "new android phones comparison",
            "latest artificial intelligence news", "what is the date today 2025",
            "what to do on New Year's Eve Turkey", "Netflix top 10 series in Turkey",
            "When is the first manned mission to Mars", "Space tourism prices",
            "easy homemade pizza recipe", "weekly horoscope scorpio",
            "technology stocks Borsa Istanbul", "e-commerce trends 2025",
            "best online course platforms", "most popular esports games in Turkey",
            "which professions are affected by artificial intelligence", "when does the winter tire mandate end",
            "short and effective motivational quotes",
            "cheapest flight ticket Izmir Kocaeli"
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

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 812);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/604.1");


        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        //for better stability / anti bot detection
        options.addArguments("--disable-infobars");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--disable-blink-features=AutomationControlled");


        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println("mobile emulation");
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
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < searchCount; i++) {
            String query = SEARCH_TERMS[random.nextInt(SEARCH_TERMS.length)];

            if (type.equals("Mobile")) {
                driver.get("https://m.bing.com");
            } else {
                driver.get("https://www.bing.com");
            }

            System.out.println(type + " Search (" + (i + 1) + "/" + searchCount + "): " + query);

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(query);
            searchBox.submit();

            Thread.sleep(1000);
            js.executeScript("window.scrollBy(0, Math.random() * 800 + 400)");
            Thread.sleep(1000);

            int delay = random.nextInt(6000) + 1500;
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