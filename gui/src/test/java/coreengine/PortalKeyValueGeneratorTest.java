package coreengine;

import string.pattern.common.vo.KeyValue;
import string.pattern.generate.core.KeyNameRule;
import string.pattern.search.vo.ResultClass;
import string.pattern.search.vo.SearchResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import custom.portal.PortalKeyNameRule;
import properties.messages.coreengine.MessagePropertyPatterner;
import properties.messages.filter.MessageFilter;
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

    private MessagePropertyPatterner helper = new MessagePropertyPatterner();

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
        List<SearchResult> searchResults = helper.search(resourceBundleWrapper.getResourceMap(), fileWrapper.getExpressions());
        List<KeyValue> messageProperties = MessageFilter.getFailureMessage(searchResults, ResultClass.TotalSimilar, keyNameRule);
        Assert.assertEquals(1, messageProperties.size());
        messageProperties.stream().forEach(System.out::println);
    }

    @Test
    public void generateKey_띄어쓰기만다르면_같은문자로_인식하도록_하는_경우_success_case() {
        List<SearchResult> searchResults = helper.search(resourceBundleWrapper.getResourceMap(), fileWrapper.getExpressions());
        List<KeyValue> messageProperties = MessageFilter.getFailureMessage(searchResults, ResultClass.TotalSimilar, keyNameRule);
        Assert.assertEquals(4, messageProperties.size());
        messageProperties.stream().forEach(System.out::println);
    }

    @Test
    public void generateKey_문자가_완전히_같아야_하는_경우_fail_case() {
        List<SearchResult> searchResults = helper.search(resourceBundleWrapper.getResourceMap(), fileWrapper.getExpressions());
        List<KeyValue> messageProperties = MessageFilter.getFailureMessage(searchResults, ResultClass.TotalEqual, keyNameRule);
        Assert.assertEquals(4, messageProperties.size());
        messageProperties.stream().forEach(System.out::println);
    }

    @Test
    public void generateKey_문자가_완전히_같아야_하는_경우_success_case() {
        List<SearchResult> searchResults = helper.search(resourceBundleWrapper.getResourceMap(), fileWrapper.getExpressions());
        List<KeyValue> messageProperties = MessageFilter.getFailureMessage(searchResults, ResultClass.TotalEqual, keyNameRule);
        Assert.assertEquals(1, messageProperties.size());
        messageProperties.stream().forEach(System.out::println);
    }
}
