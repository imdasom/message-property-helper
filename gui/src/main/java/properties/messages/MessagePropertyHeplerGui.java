package properties.messages;

import properties.messages.gui.application.ButtonAreaPanel;
import properties.messages.gui.application.InputBoxGroupPanel;
import properties.messages.gui.event.GenerateButtonClickListener;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MessagePropertyHeplerGui extends JFrame {

    public MessagePropertyHeplerGui(String title) {

        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        InputBoxGroupPanel inputBoxGroupPanel = new InputBoxGroupPanel();
        ActionListener buttonClickEvent = new GenerateButtonClickListener(inputBoxGroupPanel.getComponentsWrapper());
        ButtonAreaPanel buttonAreaPanel = new ButtonAreaPanel(buttonClickEvent);

        add(inputBoxGroupPanel);
        add(buttonAreaPanel);

        add(Box.createVerticalGlue());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {

        new MessagePropertyHeplerGui("Message Property Generator");
        // prjectpath C:\\workspace\\konamoney\\service_portal ==> 이건 한번 입력하면 프로그램껏다켜도유지되도록
        // 생성할이름 PROD_MANA, MEMBER_COMMON, ...
        // 생성할대상html파일이름 productDetailView.html 혹은 product*.html 정규식도가능
        // 생성버튼
    }
}
