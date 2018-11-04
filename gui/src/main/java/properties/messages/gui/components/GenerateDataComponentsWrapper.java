package properties.messages.gui.components;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenerateDataComponentsWrapper {

    private JTextField projectPathField;
    private JTextField keyNameField;
    private JTextField fileNameField;

    public GenerateDataComponentsWrapper(JTextField projectPathField, JTextField keyNameField, JTextField fileNameField) {
        this.projectPathField = projectPathField;
        this.keyNameField = keyNameField;
        this.fileNameField = fileNameField;

        this.projectPathField.setText("E:\\workspace\\intellij-text-patterner\\gui");
        this.keyNameField.setText("PROD_MANA");
        this.fileNameField.setText("productView.html,productDetailView.html");
    }

    public String getProjectPath() {
        return projectPathField.getText();
    }

    public String getKeyName() {
        return keyNameField.getText();
    }

    public List<File> getFileList() {
        String fileNameFieldText = fileNameField.getText();
        String[] fileNames = fileNameFieldText.split(",");
        String htmlRootPath = this.projectPathField.getText() + "\\src\\test\\resources\\html";
        List<File> files = new ArrayList<>();
        for(String fileName : fileNames) {
            File file = new File(htmlRootPath + "\\" + fileName);
            files.add(file);
        }
        return files;
    }
}
