package properties.messages.gui.application;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonAreaPanel extends JPanel {

    private JButton generateButton = new JButton("생성");

    public ButtonAreaPanel(ActionListener clickEvent) {
        add(generateButton);
        generateButton.addActionListener(clickEvent);
    }
}
