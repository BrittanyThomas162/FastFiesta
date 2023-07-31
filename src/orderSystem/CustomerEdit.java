package src.orderSystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerEdit extends JFrame
{ 
    private JTextField oldNameField;       
    private JTextField newNameField;       
    private JTextField newAddressField;
    private JTextField newNumberField;

    private JButton updateButton;
    private JButton cancelButton;

    private JPanel inputPanel;
    private JPanel buttonPanel;

    private CustomerListing cListing;
    private CustomerEdit thisEntry;
    private boolean success = false;
  
   public CustomerEdit(CustomerListing cListing)
    {
        this.cListing = cListing; 
        thisEntry = this;
        setTitle("Edit Customer");
        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        inputPanel.setPreferredSize(new Dimension(400, 80));
        buttonPanel.setPreferredSize(new Dimension(400, 100));

        inputPanel.add(new JLabel("Name of Customer to be Updated")); 
        oldNameField = new JTextField(30);
        inputPanel.add(oldNameField);
        inputPanel.add(new JLabel("New Customer Name:")); 
        newNameField = new JTextField(30);
        inputPanel.add(newNameField);
        inputPanel.add(new JLabel("New Address of Customer:")); 
        newAddressField = new JTextField(50);
        inputPanel.add(newAddressField);
        inputPanel.add(new JLabel("New Number of Customer:")); 
        newNumberField = new JTextField(20);
        inputPanel.add(newNumberField);
      
        inputPanel.setLayout(new GridLayout(4,4));
      
        updateButton  = new JButton("Update Customer");
        cancelButton =  new JButton("Cancel / Close");
      
        updateButton.addActionListener(new  UpdateButtonListener());
        cancelButton.addActionListener(new CloseButtonListener());

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.PAGE_START);
        add(buttonPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    
    }


  
    private class UpdateButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
           try{

              
                 String oName = oldNameField.getText();
               
                String newName = newNameField.getText();
                String[] nameParts = newName.split(" ");
                String newAddress = newAddressField.getText();
                String newNumber = newNumberField.getText();
              
                Customer editCustomer = cListing.findCustomer(oName);
              
              
               if (nameParts.length == 2 && editCustomer!=null)
               { 
                    cListing.updateCustomerData(oName, newName, newAddress, newNumber);
                    success = true;
              
                }
                else if (nameParts.length < 2)
              {
                    String message = "Please enter a first and last name.";
                    JOptionPane.showMessageDialog(null, message, "Notice!", JOptionPane.PLAIN_MESSAGE); 
              }
              
            
              if (success == false)
              {
                  
                  oldNameField.setText("");
                  newNameField.setText("");
                  newAddressField.setText("");
                  newNumberField.setText("");
                   
               
                  String message = "Action Unsuccessfully. Customer " + oName+ " NOT Found";
                  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
              }
              else
              {
                  String message = "Customer " + oName + " successfully edited. Click sort by name to see changes in table.";
            
                JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
                  oldNameField.setText("");
                  newNameField.setText("");
                  newAddressField.setText("");
                  newNumberField.setText("");
               
              }
        } 
         
            catch(Exception e)
            {
                 System.out.println(e.getMessage());
               
              String message = "Something went wrong. Please try again.";
                JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
            }
        }
      
    }

    private class CloseButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            thisEntry.setVisible(false);
        }
    }

  
}
