package coreengine;

import com.konai.common.vo.KeyValue;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import properties.messages.portal.PortalKeyNameRule;
import properties.messages.portal.PortalMessagePropertyHelper;
import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PortalKeyValueGeneratorTest {

    private ResourceBundleWrapper resourceBundleWrapper;
    private FileWrapper fileWrapper;
    private KeyNameRule keyNameRule;

    private PortalMessagePropertyHelper helper = new PortalMessagePropertyHelper();

    @Before
    public void setup() throws IOException {
        String location = ".\\src\\test\\resources\\";
        Locale locale = new Locale("ko", "KR");
        resourceBundleWrapper = new ResourceBundleWrapper();
        resourceBundleWrapper.add(location, "messages", locale);
        resourceBundleWrapper.add(location, "product", locale);
        File htmlFile = new File(".\\src\\test\\resources\\html\\productView.html");
        fileWrapper = new FileWrapper(htmlFile);
        keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", resourceBundleWrapper.getResourceMap());
    }

    @Test
    public void generateKey_띄어쓰기만다르면_같은문자로_인식하도록_하는_경우_fail_case() {
        List<SearchResult> searchResults = helper.search(resourceBundleWrapper, fileWrapper.getExpressions());
        List<KeyValue> messageProperties = helper.getFailureMessage(searchResults, keyNameRule, ResultClass.TotalSimilar);
        Assert.assertEquals(1, messageProperties.size());
        messageProperties.stream().forEach(System.out::println);
    }

    @Test
    public void generateKey_띄어쓰기만다르면_같은문자로_인식하도록_하는_경우_success_case() {
        List<SearchResult> searchResults = helper.search(resourceBundleWrapper, fileWrapper.getExpressions());
        List<KeyValue> messageProperties = helper.getFailureMessage(searchResults, keyNameRule, ResultClass.TotalSimilar);
        Assert.assertEquals(4, messageProperties.size());
        messageProperties.stream().forEach(System.out::println);
    }

    @Test
    public void generateKey_문자가_완전히_같아야_하는_경우_fail_case() {
        List<SearchResult> searchResults = helper.search(resourceBundleWrapper, fileWrapper.getExpressions());
        List<KeyValue> messageProperties = helper.getFailureMessage(searchResults, keyNameRule, ResultClass.TotalEqual);
        Assert.assertEquals(4, messageProperties.size());
        messageProperties.stream().forEach(System.out::println);
    }

    @Test
    public void generateKey_문자가_완전히_같아야_하는_경우_success_case() {
        List<SearchResult> searchResults = helper.search(resourceBundleWrapper, fileWrapper.getExpressions());
        List<KeyValue> messageProperties = helper.getFailureMessage(searchResults, keyNameRule, ResultClass.TotalEqual);
        Assert.assertEquals(1, messageProperties.size());
        messageProperties.stream().forEach(System.out::println);
    }
}
