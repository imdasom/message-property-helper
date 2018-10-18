import org.junit.Test;
import wrapper.ResourceBundleWrapper;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainTest {

    @Test
    public void setupResourceBundleList() throws MalformedURLException {
        String location = ".\\src\\test\\resources\\";
        String bundleName = "messages";
        Locale locale = new Locale("ko", "KR");
        ResourceBundleWrapper resourceBundleWrapper = new ResourceBundleWrapper();
        resourceBundleWrapper.add(location, bundleName, locale);
    }

    @Test
    public void setupFileList() {
        List<String> pathList = new ArrayList<>();

        FileListWrapper fileListWrapper = new FileListWrapper();

    }

}
