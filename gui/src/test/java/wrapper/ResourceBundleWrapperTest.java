package wrapper;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Locale;

public class ResourceBundleWrapperTest {

    private String location = ".\\src\\test\\resources\\";
    private Locale locale = new Locale("ko", "KR");

    @Test
    public void getResourceMap() throws IOException {
        ResourceBundleWrapper resourceBundleWrapper = new ResourceBundleWrapper();
        resourceBundleWrapper.add(location, "messages", locale);
        resourceBundleWrapper.add(location, "product", locale);
        Assert.assertEquals(resourceBundleWrapper.getResourceMap().size(), 16);
    }
}