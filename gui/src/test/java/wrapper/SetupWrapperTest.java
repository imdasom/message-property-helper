package wrapper;

import org.junit.Assert;
import org.junit.Test;
import properties.messages.portal.PortalSetupWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetupWrapperTest {

    private static String projectPath = "E:\\workspace\\intellij-message-property-helper\\gui";
    private static String fileNameRegularExpression = "productView.html";

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
        File file = new File(projectPath + "\\src\\test\\resources\\html\\" + fileNameRegularExpression);
        List<File> htmlFiles = new ArrayList<>();
        htmlFiles.add(file);
        SetupWrapper setupWrapper = new PortalSetupWrapper();
        List<FileWrapper> fileWrapperList = setupWrapper.getFileWrappers(htmlFiles);
        Assert.assertEquals(1, fileWrapperList.size());
    }

}