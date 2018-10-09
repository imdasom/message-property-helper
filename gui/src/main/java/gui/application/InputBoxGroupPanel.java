package gui.application;

import gui.components.GenerateDataComponentsWrapper;

import javax.swing.*;

public class InputBoxGroupPanel extends JPanel {

    private JLabel label1 = new JLabel("프로젝트경로");
    private JTextField projectPathField = new JTextField(30);
    private JLabel label2 = new JLabel("페이지 아이디");
    private JTextField keyNameField = new JTextField(16);
    private JLabel label3 = new JLabel("대상파일명");
    private JTextField fileNameField = new JTextField(16);

    public InputBoxGroupPanel() {
        this.setBorder(BorderFactory.createTitledBorder("입력항목"));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label1)
                        .addComponent(label2)
                        .addComponent(label3))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(projectPathField)
                        .addComponent(keyNameField)
                        .addComponent(fileNameField))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(projectPathField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(keyNameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label3)
                        .addComponent(fileNameField))
        );
    }

    public GenerateDataComponentsWrapper getComponentsWrapper() {
        return new GenerateDataComponentsWrapper(projectPathField, keyNameField, fileNameField);
    }

}
