package properties.messages.wrapper;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SetupWrapperTest {

    private static String projectPath = "E:\\workspace\\intellij-message-property-helper";
    private static String keyName = "PROD_MANA";
    private static String fileNameRegularExpression = "product*.html";

    @Test
    public void setupResourceBundelWrapper() throws IOException {
        String location = "\\src\\test\\resources\\";
        SetupWrapper setupWrapper = new PortalSetupWrapper();
        ResourceBundleWrapper resourceBundleWrapper = setupWrapper.getResourceBundleWrapper(projectPath + location);
        Assert.assertEquals(17, resourceBundleWrapper.getResourceMap().size());
//        keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", resourceBundleWrapper.getResourceMap());
    }

    @Test
    public void setupFileWrapperList() throws IOException {
        SetupWrapper setupWrapper = new PortalSetupWrapper();
        List<FileWrapper> fileWrapperList = setupWrapper.getFileWrappers(fileNameRegularExpression);
        Assert.assertEquals(1, fileWrapperList.size());
    }

}