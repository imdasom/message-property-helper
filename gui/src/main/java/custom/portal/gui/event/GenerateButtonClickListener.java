package custom.portal.gui.event;

import com.konai.common.core.Expression;
import com.konai.common.util.CollectionUtils;
import com.konai.common.vo.KeyValue;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.vo.ResultClass;
import properties.messages.gui.components.GenerateDataComponentsWrapper;
import properties.messages.portal.PortalKeyNameRule;
import properties.messages.portal.PortalMessagePropertyHelper;
import properties.messages.portal.PortalSetupWrapper;
import properties.messages.portal.ThymeleafTextValuePatternSearcher;
import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateButtonClickListener implements ActionListener {

    private GenerateDataComponentsWrapper componentsWrapper;

    public GenerateButtonClickListener(GenerateDataComponentsWrapper generateDataComponentsWrapper) {
        this.componentsWrapper = generateDataComponentsWrapper;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String projectPath = componentsWrapper.getProjectPath();
        String keyName = componentsWrapper.getKeyName();
        List<File> fileList = componentsWrapper.getFileList();

        PortalSetupWrapper setupWrapper = new PortalSetupWrapper();
        List<FileWrapper> fileWrappers = getFileWrapperList(setupWrapper, fileList);
        ResourceBundleWrapper resourceBundleWrapper = getResourceBundleWrapper(setupWrapper, projectPath);
        KeyNameRule keyNameRule = new PortalKeyNameRule(keyName, "_", resourceBundleWrapper.getResourceMap());
        ThymeleafTextValuePatternSearcher collectPattern = new ThymeleafTextValuePatternSearcher();

        if(resourceBundleWrapper == null || CollectionUtils.isEmpty(fileWrappers)) {
            return;
        }

        FileWrapper fileWrapper = fileWrappers.get(0);

        PortalMessagePropertyHelper helper = new PortalMessagePropertyHelper();
        List<KeyValue> generatedMessages = helper.generate(
                fileWrapper,
                resourceBundleWrapper,
                keyNameRule,
                ResultClass.TotalSimilar,
                collectPattern
        );

        List<KeyValue> allMessageProperties = new ArrayList<>();
        allMessageProperties.addAll(resourceBundleWrapper.getResourceMapToList());
        allMessageProperties.addAll(generatedMessages);

        List<Expression> result = helper.replace(allMessageProperties, fileWrapper);

        // print out replace result
        generatedMessages.stream().forEach(System.out::println);
        result.stream().forEach(
                expression -> System.out.println(expression.getValue())
        );

        // set text area genereated key-value
        Map<String, String> messageToMap = new HashMap<>();
        for(KeyValue keyValue : generatedMessages) {
            messageToMap.put(keyValue.getKey().getValue(), keyValue.getValue().getValue());
        }
        componentsWrapper.setGeneratedMessages(messageToMap);
    }

    private ResourceBundleWrapper getResourceBundleWrapper(PortalSetupWrapper setupWrapper, String projectPath) {
        ResourceBundleWrapper resourceBundleWrapper = null;
        try {
            resourceBundleWrapper = setupWrapper.getResourceBundleWrapper(projectPath);
        } catch (IOException e) {
            showErrorMessage("프로젝트 경로 에러");
            resourceBundleWrapper = null;
        } finally {
            return resourceBundleWrapper;
        }
    }

    private List<FileWrapper> getFileWrapperList(PortalSetupWrapper setupWrapper, List<File> files) {
        List<FileWrapper> fileWrappers = null;
        try {
            fileWrappers = setupWrapper.getFileWrappers(files);
        } catch (IOException e) {
            showErrorMessage("IOException error from getting file list.");
            fileWrappers = null;
        } finally {
            if(CollectionUtils.isEmpty(fileWrappers)) {
                showErrorMessage("File list is empty");
                fileWrappers = null;
            }
            return fileWrappers;
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(),
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
