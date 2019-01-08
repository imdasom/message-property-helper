package custom.portal.gui.components;

import string.pattern.common.vo.KeyValue;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerateDataComponentsWrapper {

    public static final String ALL_FILES = "*";
    public static final String FILENAME_DELIMITER = ",";
    private JTextField projectPathField;
    private JTextField keyNameField;
    private JTextField fileDirectoryField;
    private JTextField fileNameField;
    private JTextArea outputMessagesField;
    private List<KeyValue> generatedMessages;

    public GenerateDataComponentsWrapper(JTextField projectPathField, JTextField keyNameField, JTextField fileDirectoryField, JTextField fileNameField, JTextArea outputMessagesField) {
        this.projectPathField = projectPathField;
        this.keyNameField = keyNameField;
        this.fileDirectoryField = fileDirectoryField;
        this.fileNameField = fileNameField;
        this.outputMessagesField = outputMessagesField;

        this.projectPathField.setText("E:\\workspace\\intellij-text-patterner\\gui");
        this.keyNameField.setText("COMMON");
        this.fileDirectoryField.setText("src\\test\\resources\\html");
        this.fileNameField.setText("*");
        this.outputMessagesField.setText("생성버튼을 눌러주세요");
    }

    public String getProjectPath() {
        return projectPathField.getText();
    }

    public String getKeyName() {
        return keyNameField.getText();
    }

    public List<File> getFileList() {
        List<File> files;
        String fileNameFieldText = fileNameField.getText();
        if(ALL_FILES.equals(fileNameFieldText)) {
            String htmlRootPath = this.projectPathField.getText() + "\\" + this.fileDirectoryField.getText();
            File directory = new File(htmlRootPath);
            files = Arrays.stream(directory.listFiles()).collect(Collectors.toList());
        } else {
            String[] fileNames = fileNameFieldText.split(FILENAME_DELIMITER);
            String htmlRootPath = this.projectPathField.getText() + "\\" + this.fileDirectoryField.getText();
            files = new ArrayList<>();
            for(String fileName : fileNames) {
                File file = new File(htmlRootPath + "\\" + fileName);
                files.add(file);
            }
        }
        return files;
    }

    public void setGeneratedMessages(Map<String, String> generatedMessages) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry e : generatedMessages.entrySet()) {
            String key = String.valueOf(e.getKey());
            String value = String.valueOf(e.getValue());
            String outputString = key + "=" + value;
            sb.append(outputString + "\n");
        }
        outputMessagesField.setText(sb.toString());
    }
}
