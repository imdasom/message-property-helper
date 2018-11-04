package properties.messages.portal;

import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;
import properties.messages.wrapper.SetupWrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PortalSetupWrapper implements SetupWrapper {

    @Override
    public ResourceBundleWrapper getResourceBundleWrapper(String projectPath) throws IOException {
        String resourceLocation = "\\src\\test\\resources\\";
        String fullLocation = projectPath + resourceLocation;
        Locale locale = new Locale("ko", "KR");
        ResourceBundleWrapper resourceBundleWrapper = new ResourceBundleWrapper();
        resourceBundleWrapper.add(fullLocation, "messages", locale);
        resourceBundleWrapper.add(fullLocation, "product", locale);
        return resourceBundleWrapper;
    }

    @Override
    public List<FileWrapper> getFileWrappers(List<File> files) throws IOException {
        List<FileWrapper> fileWrappers = new ArrayList<>();
        for(File file : files) {
            fileWrappers.add(new FileWrapper(file));
        }
        return fileWrappers;
    }
}
