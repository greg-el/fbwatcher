import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UI {
    public WebDriver create(WebDriver driver) {
        Methods methods = new Methods();
        List listGroups = methods.getMemberGroups(driver);
        JFrame f = new JFrame();

        JButton start = new JButton("Start");
        start.setBounds(500, 400, 100, 30);
        f.add(start);

        JButton addGroup = new JButton("Add");
        addGroup.setBounds(240, 40, 100, 30);
        f.add(addGroup);

        JButton removeGroup = new JButton("Remove");
        removeGroup.setBounds(240, 80, 100, 30);
        f.add(removeGroup);

        // List all groups with membership
        final DefaultListModel groupSelectList = new DefaultListModel();
        for (Object group : listGroups) {
            String[] separate = group.toString().split("\\|\\*\\|");
            groupSelectList.addElement(separate[1]);
        }
        final JList groupDisplayList = new JList(groupSelectList);
        groupDisplayList.setBounds(20, 20, 150, 450);
        JScrollPane groupScrollPane = new JScrollPane(groupDisplayList);
        groupScrollPane.setBounds(20, 20, 200, 400);
        f.add(groupScrollPane);

        // Selected groups
        final DefaultListModel selectedGroups = new DefaultListModel();
        final JList selectedGroupsDisplay = new JList(selectedGroups);
        selectedGroupsDisplay.setBounds(380, 20, 200, 400);
        JScrollPane groupSelectedScrollPane = new JScrollPane(selectedGroupsDisplay);
        groupSelectedScrollPane.setBounds(380, 20, 200, 400);
        f.add(groupSelectedScrollPane);

        // adds to selected groups on add button click
        addGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (groupDisplayList.getSelectedIndex() != -1) {
                    String data = "" + groupDisplayList.getSelectedValue();
                    selectedGroups.addElement(data);
                    groupSelectList.removeElement(data);
                }
            }
        });

        // removes selection from selected groups
        removeGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedGroupsDisplay.getSelectedIndex() != -1) {
                    String data = "" + selectedGroupsDisplay.getSelectedValue();
                    groupSelectList.addElement(data);
                    selectedGroups.removeElement(data);
                }
            }
        });

        f.setSize(800, 500);
        f.setLayout(null);
        f.setVisible(true);

        return driver;
    }
}
