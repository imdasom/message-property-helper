package properties.messages.wrapper;

import java.io.IOException;
import java.util.List;

public interface SetupWrapper {
    ResourceBundleWrapper getResourceBundleWrapper(String path) throws IOException;
    List<FileWrapper> getFileWrappers(String fileNameRegularExpression) throws IOException;
}
