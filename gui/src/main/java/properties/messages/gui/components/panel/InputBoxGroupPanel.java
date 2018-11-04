package properties.messages.gui.components.panel;

import properties.messages.gui.components.GenerateDataComponentsWrapper;

import javax.swing.*;

public class InputBoxGroupPanel extends JPanel {

    private JLabel label1 = new JLabel("프로젝트경로");
    private JTextField projectPathField = new JTextField(30);
    private JLabel label2 = new JLabel("페이지 아이디");
    private JTextField keyNameField = new JTextField(30);
    private JLabel label3 = new JLabel("대상파일명");
    private JTextField fileNameField = new JTextField(30);
    private JLabel outputPathLabel = new JLabel("출력위치");
    private JTextField outputPathField = new JTextField(30);
    private JLabel outputMessagesLabel = new JLabel("생성결과");
    private JTextArea outputMessagesField = new JTextArea(20, 30);

    public InputBoxGroupPanel() {
        this.setBorder(BorderFactory.createTitledBorder("입력항목"));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(label1)
                                .addComponent(label2)
                                .addComponent(label3)
                                .addComponent(outputPathLabel)
                                .addComponent(outputMessagesLabel)
                )
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(projectPathField)
                                .addComponent(keyNameField)
                                .addComponent(fileNameField)
                                .addComponent(outputPathField)
                                .addComponent(outputMessagesField)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(projectPathField)
                )
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(keyNameField)
                )
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(fileNameField)
                )
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(outputPathLabel)
                                .addComponent(outputPathField)
                )
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(outputMessagesLabel)
                                .addComponent(outputMessagesField)
                )
        );
    }

    public GenerateDataComponentsWrapper getComponentsWrapper() {
        return new GenerateDataComponentsWrapper(projectPathField, keyNameField, fileNameField, outputMessagesField);
    }

}
