package properties.messages.gui.event;

import com.konai.common.core.Expression;
import com.konai.common.util.CollectionUtils;
import com.konai.common.vo.MessageProperty;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import properties.messages.coreengine.GenerateEngine;
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
import java.util.List;

public class GenerateButtonClickListener implements ActionListener {

    private GenerateDataComponentsWrapper componentsWrapper;
    private PortalMessagePropertyCollector collector;

    public GenerateButtonClickListener(GenerateDataComponentsWrapper generateDataComponentsWrapper) {
        this.componentsWrapper = generateDataComponentsWrapper;
        this.collector = new PortalMessagePropertyCollector();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String projectPath = componentsWrapper.getProjectPath();
        String keyName = componentsWrapper.getKeyName();
        List<File> fileList = componentsWrapper.getFileList();

        SetupWrapper setupWrapper = new PortalSetupWrapper();
        ResourceBundleWrapper resourceBundleWrapper = getResourceBundleWrapper(setupWrapper, projectPath);
        List<FileWrapper> fileWrappers = getFileWrapperList(setupWrapper, fileList);

        if(!CollectionUtils.isEmpty(fileWrappers)) {
            FileWrapper fileWrapper = fileWrappers.get(0);
            GenerateEngine generateEngine = instantiateGenerateEngine(collector, resourceBundleWrapper, fileWrapper);
            if(generateEngine == null) {
                showErrorMessage("cannot instantiate generate coreengine");
                return;
            }

            List<SearchResult> searchResultList = generateEngine.getSearchResults();
            KeyNameRule keyNameRule = new PortalKeyNameRule(keyName, "_", resourceBundleWrapper.getResourceMap());
            List<MessageProperty> generatedMessages = generateEngine.getFailureMessage(searchResultList, ResultClass.TotalSimilar, keyNameRule);
            List<MessageProperty> successMessages = generateEngine.getSuccessMessages(searchResultList, ResultClass.TotalSimilar);
            List<MessageProperty> allMessageProperties = mergeList(generatedMessages, successMessages);

            ReplaceEngine replaceEngine = new ReplaceEngine();
            ThymeleafTextPatternSearcher thymeleafTextPatternReplacer = new ThymeleafTextPatternSearcher();
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
    }

    private List<MessageProperty> mergeList(List<MessageProperty> generatedMessages, List<MessageProperty> successMessages) {
        List<MessageProperty> mergedMessages = new ArrayList<>();
        mergedMessages.addAll(generatedMessages);
        mergedMessages.addAll(successMessages);
        return mergedMessages;
    }

    private GenerateEngine instantiateGenerateEngine(PortalMessagePropertyCollector collector, ResourceBundleWrapper resourceBundleWrapper, FileWrapper fileWrapper) {
        GenerateEngine generateEngine;
        if(resourceBundleWrapper != null && fileWrapper != null) {
            generateEngine = new GenerateEngine(collector, resourceBundleWrapper, fileWrapper);
        } else {
            generateEngine = null;
        }
        return generateEngine;
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
