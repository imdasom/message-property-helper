package components;

import org.junit.Assert;
import org.junit.Test;
import properties.messages.gui.components.GenerateDataComponentsWrapper;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class GenerateDataComponentsWrapperTest {

    @Test
    public void getFileNameRegularExpressionTest() {
        GenerateDataComponentsWrapper generateDataComponentsWrapper = new GenerateDataComponentsWrapper(
                new JTextField(),
                new JTextField(),
                new JTextField()
        );
        List<File> files = generateDataComponentsWrapper.getFileList();
        File file1 = files.get(0);
        File file2 = files.get(1);
        Assert.assertEquals(2, files.size());
        Assert.assertEquals("productView.html", file1.getName());
        Assert.assertEquals("productDetailView.html", file2.getName());
    }
}