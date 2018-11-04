package properties.messages.portal;

import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.vo.MessageProperty;
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

public class PortalMessagePropertyHelperTest {

    FileWrapper fileWrapper;
    ResourceBundleWrapper resourceBundleWrapper;
    KeyNameRule keyNameRule;

    //regular expression pattern
    PatternSearcher<Expression, Expression> collectPattern = new ThymeleafTextValuePatternSearcher();
    PatternSearcher<Expression, Expression> thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();


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
        List<MessageProperty> messagePropertyList = helper.generate(fileWrapper, resourceBundleWrapper, keyNameRule, ResultClass.TotalSimilar, collectPattern);
        System.out.println(Arrays.deepToString(messagePropertyList.toArray()));
        Assert.assertEquals(4, messagePropertyList.size());
    }

}