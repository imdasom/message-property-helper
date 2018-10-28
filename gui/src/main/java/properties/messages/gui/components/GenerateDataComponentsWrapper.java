package properties.messages.gui.components;

import javax.swing.*;

public class GenerateDataComponentsWrapper {

    private JTextField projectPathField;
    private JTextField keyNameField;
    private JTextField fileNameField;

    public GenerateDataComponentsWrapper(JTextField projectPathField, JTextField keyNameField, JTextField fileNameField) {
        this.projectPathField = projectPathField;
        this.keyNameField = keyNameField;
        this.fileNameField = fileNameField;
    }

    public String getProjectPath() {
        return projectPathField.getText();
    }

    public String getKeyName() {
        return keyNameField.getText();
    }

    public String getFileNameRegularExpression() {
        return fileNameField.getText();
    }
}
