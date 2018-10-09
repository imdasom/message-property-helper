package gui.application;

import gui.components.GenerateDataComponentsWrapper;
import gui.event.GenerateButtonClickListener;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonAreaPanel extends JPanel {

    private JButton generateButton = new JButton("생성");

    public ButtonAreaPanel(ActionListener clickEvent) {
        add(generateButton);
        generateButton.addActionListener(clickEvent);
    }
}
