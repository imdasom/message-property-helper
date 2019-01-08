package properties.messages.portal;

import com.konai.common.vo.KeyValue;
import com.konai.generate.core.KeyNameRule;
import custom.portal.PortalFileWrapper;
import custom.portal.PortalKeyNameRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import properties.messages.coreengine.MessagePropertyPatterner;
import properties.messages.wrapper.ResourceBundleWrapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PortalKeyValuePropertyHelperTest {

    PortalFileWrapper portalFileWrapper;
    ResourceBundleWrapper resourceBundleWrapper;
    KeyNameRule keyNameRule;

    @Before
    public void setup() throws IOException {
        String location = ".\\src\\test\\resources\\";
        Locale locale = new Locale("ko", "KR");
        resourceBundleWrapper = new ResourceBundleWrapper();
        resourceBundleWrapper.add(location, "messages", locale);
        resourceBundleWrapper.add(location, "product", locale);
        File htmlFile = new File(".\\src\\test\\resources\\html\\productView.html");
        portalFileWrapper = new PortalFileWrapper(htmlFile);
        keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", resourceBundleWrapper.getResourceMap());
    }

    @Test
    public void testGenerate() {
        MessagePropertyPatterner helper = new MessagePropertyPatterner();
        List<KeyValue> keyValueList = helper.generate(portalFileWrapper, resourceBundleWrapper.getResourceMap(), keyNameRule);
        System.out.println(Arrays.deepToString(keyValueList.toArray()));
        Assert.assertEquals(4, keyValueList.size());
    }

}