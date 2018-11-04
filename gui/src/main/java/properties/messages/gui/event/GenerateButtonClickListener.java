package properties.messages.gui.event;

import com.konai.common.core.Expression;
import com.konai.common.util.CollectionUtils;
import com.konai.common.vo.MessageProperty;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.vo.ResultClass;
import properties.messages.coreengine.ReplaceEngine;
import properties.messages.gui.components.GenerateDataComponentsWrapper;
import properties.messages.portal.*;
import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;
import properties.messages.wrapper.SetupWrapper;

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

        SetupWrapper setupWrapper = new PortalSetupWrapper();
        List<FileWrapper> fileWrappers = getFileWrapperList(setupWrapper, fileList);
        ResourceBundleWrapper resourceBundleWrapper = getResourceBundleWrapper(setupWrapper, projectPath);
        KeyNameRule keyNameRule = new PortalKeyNameRule(keyName, "_", resourceBundleWrapper.getResourceMap());
        ThymeleafTextValuePatternSearcher collectPattern = new ThymeleafTextValuePatternSearcher();

        if(resourceBundleWrapper == null || CollectionUtils.isEmpty(fileWrappers)) {
            return;
        }

        FileWrapper fileWrapper = fileWrappers.get(0);

        PortalMessagePropertyHelper helper = new PortalMessagePropertyHelper();
        List<MessageProperty> generatedMessages = helper.generate(
                fileWrapper,
                resourceBundleWrapper,
                keyNameRule,
                ResultClass.TotalSimilar,
                collectPattern
        );

        Map<String, String> messageToMap = new HashMap<>();
        for(MessageProperty messageProperty : generatedMessages) {
            messageToMap.put(messageProperty.getKey().getValue(), messageProperty.getValue().getValue());
        }
        componentsWrapper.setGeneratedMessages(messageToMap);

        List<MessageProperty> allMessageProperties = new ArrayList<>();
        allMessageProperties.addAll(resourceBundleWrapper.getResourceMapToList());
        allMessageProperties.addAll(generatedMessages);

        ReplaceEngine replaceEngine = new ReplaceEngine();
        ThymeleafTextValuePatternSearcher thymeleafTextPatternReplacer = new ThymeleafTextValuePatternSearcher();
        ThymeleafTextValuePatterner thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();
        BetweenHtmlTagPatternSearcher plainValuePatterner = new BetweenHtmlTagPatternSearcher();
        List<Expression> result = replaceEngine.set(allMessageProperties, fileWrapper.getExpressions())
                .replace(thymeleafTextValuePatterner, thymeleafTextPatternReplacer)
                .replace(plainValuePatterner, plainValuePatterner)
                .get();

        generatedMessages.stream().forEach(System.out::println);
        result.stream().forEach(
                expression -> System.out.println(expression.getValue())
        );
    }

    private ResourceBundleWrapper getResourceBundleWrapper(SetupWrapper setupWrapper, String projectPath) {
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

    private List<FileWrapper> getFileWrapperList(SetupWrapper setupWrapper, List<File> files) {
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
