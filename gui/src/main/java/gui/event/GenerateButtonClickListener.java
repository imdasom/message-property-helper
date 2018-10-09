package gui.event;

import gui.components.GenerateDataComponentsWrapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerateButtonClickListener implements ActionListener {

    private GenerateDataComponentsWrapper componentsWrapper;

    public GenerateButtonClickListener(GenerateDataComponentsWrapper generateDataComponentsWrapper) {
        this.componentsWrapper = generateDataComponentsWrapper;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String projectPath = componentsWrapper.getProjectPath();
        String keyName = componentsWrapper.getKeyName();
        String fileNameRegularExpression = componentsWrapper.getFileNameRegularExpression();
        System.out.println(projectPath);
        System.out.println(keyName);
        System.out.println(fileNameRegularExpression);
    }
}
