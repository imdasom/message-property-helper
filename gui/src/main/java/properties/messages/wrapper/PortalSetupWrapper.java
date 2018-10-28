package properties.messages.wrapper;

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
    public List<FileWrapper> getFileWrappers(String fileNameRegularExpression) throws IOException {
        List<FileWrapper> fileWrappers = new ArrayList<>();
        File htmlFile = new File(".\\src\\test\\resources\\html\\productView.html");
        fileWrappers.add(new FileWrapper(htmlFile));
        return fileWrappers;
    }
}
