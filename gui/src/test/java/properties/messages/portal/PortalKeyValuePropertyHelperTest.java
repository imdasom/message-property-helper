package properties.messages.portal;

import com.konai.common.core.PatternSearcher;
import com.konai.common.vo.KeyValue;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.vo.ResultClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PortalKeyValuePropertyHelperTest {

    FileWrapper fileWrapper;
    ResourceBundleWrapper resourceBundleWrapper;
    KeyNameRule keyNameRule;

    //regular expression pattern
    PatternSearcher collectPattern = new ThymeleafTextValuePatternSearcher();
    PatternSearcher thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();


    @Before
    public void setup() throws IOException {
        String location = ".\\src\\test\\resources\\";
        Locale locale = new Locale("ko", "KR");
        resourceBundleWrapper = new ResourceBundleWrapper();
        resourceBundleWrapper.add(location, "messages", locale);
        resourceBundleWrapper.add(location, "product", locale);
        File htmlFile = new File(".\\src\\test\\resources\\html\\productView.html");
        fileWrapper = new FileWrapper(htmlFile);
        keyNameRule = new properties.messages.portal.PortalKeyNameRule("PROD_MANA", "_", resourceBundleWrapper.getResourceMap());
    }

    @Test
    public void testGenerate() {
        PortalMessagePropertyHelper helper = new PortalMessagePropertyHelper();
        List<KeyValue> keyValueList = helper.generate(fileWrapper, resourceBundleWrapper, keyNameRule, ResultClass.TotalSimilar, collectPattern);
        System.out.println(Arrays.deepToString(keyValueList.toArray()));
        Assert.assertEquals(4, keyValueList.size());
    }

}