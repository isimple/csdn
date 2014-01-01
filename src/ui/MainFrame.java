/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author yang
 */
public class MainFrame extends JFrame {

    private JList resourcesList;
    private JList logList;
    private JLabel countLabel = new JLabel();
    private JButton btnFetch = new JButton();
    private JButton btnAutoComment = new JButton();
    private JPanel panelRight;

    public MainFrame() {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        resourcesList = new JList();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(resourcesList, c);

        logList = new JList();
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        this.add(logList, c);

        panelRight = new JPanel();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = GridBagConstraints.REMAINDER;
        this.add(panelRight, c);

        countLabel = new JLabel("还有资源。。。个");
        panelRight.add(countLabel);
        btnFetch = new JButton("获取列表");
        panelRight.add(btnFetch);
        btnAutoComment = new JButton("开始评价");
        panelRight.add(btnAutoComment);
//
//        button = new JButton("Long-Named Button 4");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 40; // make this component tall
//        c.weightx = 0.0;
//        c.gridwidth = 3;
//        c.gridx = 0;
//        c.gridy = 1;
//        pane.add(button, c);
//
//        button = new JButton("5");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 0; // reset to default
//        c.weighty = 1.0; // request any extra vertical space
//        c.anchor = GridBagConstraints.PAGE_END; // bottom of space
//        c.insets = new Insets(10, 0, 0, 0); // top padding
//        c.gridx = 1; // aligned with button 2
//        c.gridwidth = 2; // 2 columns wide
//        c.gridy = 2; // third row
//        pane.add(button, c);
    }

    public static void main(String[] args) {
        MainFrame fg = new MainFrame();
        fg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fg.setSize(300, 100);
        fg.setVisible(true);
        fg.setLocationRelativeTo(null);
    }
}
