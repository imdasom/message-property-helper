package wrapper;

import com.konai.common.util.FileUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWrapper {

    private List<ResourceBundle> resourceBundles;

    public ResourceBundleWrapper() {
        resourceBundles = new ArrayList<>();
    }

    public void add(String location, String bundleName, Locale locale) throws MalformedURLException {
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, locale);
        resourceBundles.add(bundle);
    }
}
