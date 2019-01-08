package custom.portal.gui.event;

import string.pattern.common.util.CollectionUtils;
import string.pattern.common.util.StringUtils;
import string.pattern.common.vo.Key;
import string.pattern.common.vo.KeyValue;
import string.pattern.generate.core.KeyNameRule;
import string.pattern.search.vo.Message;
import custom.portal.PortalFileWrapper;
import custom.portal.PortalKeyNameRule;
import custom.portal.PortalSetupConfiguration;
import custom.portal.gui.components.GenerateDataComponentsWrapper;
import properties.messages.coreengine.MessagePropertyPatterner;
import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateButtonClickListener implements ActionListener {

    private GenerateDataComponentsWrapper componentsWrapper;

    public GenerateButtonClickListener(GenerateDataComponentsWrapper generateDataComponentsWrapper) {
        this.componentsWrapper = generateDataComponentsWrapper;
    }

    private void putAllMessageMap(List<KeyValue> generatedMessages, Map<Key, Message> resourceTokenList) {
        for(KeyValue keyValue : generatedMessages) {
            resourceTokenList.put(keyValue.getKey(), new Message(keyValue.getValue().getValue()));
        }
    }

    private void putAllStringMap(List<KeyValue> generatedMessages, Map<String, String> messageToMap) {
        for(KeyValue keyValue : generatedMessages) {
            messageToMap.put(keyValue.getKey().getValue(), keyValue.getValue().getValue());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String projectPath = componentsWrapper.getProjectPath();
        String defaultKeyName = componentsWrapper.getKeyName();
        List<File> fileList = componentsWrapper.getFileList();

        PortalSetupConfiguration setupWrapper = new PortalSetupConfiguration();
        List<PortalFileWrapper> fileWrappers = getFileWrapperList(setupWrapper, fileList);
        ResourceBundleWrapper resourceBundleWrapper = getResourceBundleWrapper(setupWrapper, projectPath);
        if(resourceBundleWrapper == null || CollectionUtils.isEmpty(fileWrappers)) {
            return;
        }

        MessagePropertyPatterner patterner = new MessagePropertyPatterner();

        Map<Key, Message> resourceTokenList = resourceBundleWrapper.getResourceMap();
        for(FileWrapper fileWrapper : fileWrappers) {
            PortalFileWrapper portalFileWrapper = (PortalFileWrapper) fileWrapper;
            String keyName = StringUtils.getString(portalFileWrapper.getKeyName(), defaultKeyName);
            KeyNameRule keyNameRule = new PortalKeyNameRule(keyName, "_", resourceTokenList);
            List<KeyValue> generatedMessages = patterner.generate(portalFileWrapper, resourceTokenList, keyNameRule);
            portalFileWrapper.setGeneratedMessages(generatedMessages);
            putAllMessageMap(generatedMessages, resourceTokenList);
        }

        Map<String, String> messageToMap = new HashMap<>();
        for(FileWrapper fileWrapper : fileWrappers) {
            PortalFileWrapper portalFileWrapper = (PortalFileWrapper) fileWrapper;
            List<KeyValue> generatedMessages = portalFileWrapper.getGeneratedMessages();
            putAllStringMap(generatedMessages, messageToMap);
        }
        componentsWrapper.setGeneratedMessages(messageToMap);
    }

    private ResourceBundleWrapper getResourceBundleWrapper(PortalSetupConfiguration setupWrapper, String projectPath) {
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

    private List<PortalFileWrapper> getFileWrapperList(PortalSetupConfiguration setupWrapper, List<File> files) {
        List<PortalFileWrapper> fileWrappers = null;
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
