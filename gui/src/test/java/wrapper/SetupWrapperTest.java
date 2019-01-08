package wrapper;

import custom.portal.PortalFileWrapper;
import custom.portal.PortalSetupConfiguration;
import org.junit.Assert;
import org.junit.Test;
import properties.messages.wrapper.ResourceBundleWrapper;

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
        PortalSetupConfiguration setupWrapper = new PortalSetupConfiguration();
        ResourceBundleWrapper resourceBundleWrapper = setupWrapper.getResourceBundleWrapper(projectPath + location);
        Assert.assertEquals(17, resourceBundleWrapper.getResourceMap().size());
    }

    @Test
    public void setupFileWrapperList() throws IOException {
        File file = new File(projectPath + "\\src\\test\\resources\\html\\" + fileNameRegularExpression);
        List<File> htmlFiles = new ArrayList<>();
        htmlFiles.add(file);
        PortalSetupConfiguration setupConfig = new PortalSetupConfiguration();
        List<PortalFileWrapper> fileWrapperList = setupConfig.getFileWrappers(htmlFiles);
        Assert.assertEquals(1, fileWrapperList.size());
    }

}