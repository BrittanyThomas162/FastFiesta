package src.orderSystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerEntry extends JFrame
{
    private JTextField nameField;       
    private JTextField addressField;
    private JTextField phoneNumField;
    
    private JButton saveButton;
    private JButton closeButton;

    private JPanel inputPanel;
    private JPanel buttonPanel;
  
    private CustomerListing cListing;
    private CustomerEntry thisEntry;

    public CustomerEntry(CustomerListing cListing)
    {
        this.cListing = cListing;
        thisEntry = this;
        setTitle("Adding new customer");
        inputPanel = new JPanel();
        buttonPanel = new JPanel();
        
        inputPanel.setPreferredSize(new Dimension(400, 80));
        buttonPanel.setPreferredSize(new Dimension(400, 50));
      
        inputPanel.add(new JLabel("Customer Name:")); 
        nameField = new JTextField(30);
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField(50);
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Phone Number:")); 
        phoneNumField = new JTextField(13);
        inputPanel.add(phoneNumField);
       
        inputPanel.setLayout(new GridLayout(4,4));

        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        closeButton.addActionListener(new CloseButtonListener());
        saveButton.addActionListener(new SaveButtonListener());

        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    private class CloseButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            thisEntry.setVisible(false);
        }
    }

    private class SaveButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            try {

                String name = nameField.getText();
                String[] nameParts = name.split(" ");
                String address = addressField.getText();
                String phoneNum = phoneNumField.getText();

                if ((nameParts.length == 2) && (address.length() > 0))
                {  
                    cListing.addCustomer(new Customer(name, address, phoneNum));
                    thisEntry.setVisible(false);
                }
                else if (nameParts.length < 2)
                {
                    String message = "Please enter a first and last name.";
                    JOptionPane.showMessageDialog(null, message, "Notice!", JOptionPane.PLAIN_MESSAGE);
                }

                if (address.length() < 1)
                {
                    String message = "Please enter an address.";
                    JOptionPane.showMessageDialog(null, message, "Notice!", JOptionPane.PLAIN_MESSAGE);
                }
              }
            catch(Exception e)
            { }
        }
    }
}