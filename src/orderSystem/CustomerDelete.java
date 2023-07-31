package src.orderSystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerDelete extends JFrame
{ 
    private JTextField CustomerNameField;       
    private JButton deleteButton;
    private JButton cancelButton;
   
    private JPanel inputPanel;
    private JPanel buttonPanel;

    private CustomerDelete thisEntry;
    private boolean success = false;
    private CustomerListing cListing;

   public CustomerDelete(CustomerListing cListing)
    {
        this.cListing = cListing; 
        thisEntry = this;
        setTitle("Delete Customer");
        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        inputPanel.setPreferredSize(new Dimension(200, 80));
        buttonPanel.setPreferredSize(new Dimension(400, 80));

        inputPanel.add(new JLabel("Customer Name:")); 
        CustomerNameField = new JTextField(10);
        inputPanel.add(CustomerNameField);
        //inputPanel.setLayout(new GridLayout(3,4));
      
        deleteButton  = new JButton("Delete");
        cancelButton =  new JButton("Cancel / Close");
        deleteButton.addActionListener(new DeleteButtonListener());
        cancelButton.addActionListener(new CloseButtonListener());
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.PAGE_START);
        add(buttonPanel, BorderLayout.CENTER);
        
        pack();
        setVisible(true);
      
    }

    private class DeleteButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            try{
                
                String CustomerName = CustomerNameField.getText();
                Customer delCustomer = cListing.findCustomer(CustomerName);
                
               if (delCustomer != null)
               { 
                 cListing.removeCustomer(delCustomer);
                
                  success = true;
               }
              if (success == false)
              {
               
                  String message = "Action Unsuccessful. Customer " + (String.valueOf(CustomerName))+ " NOT Found";
               
              JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
                }
              else
              {
                                  String message = "Customer " + (String.valueOf(CustomerName))+ " successfully deleted";
                JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
                  CustomerNameField.setText("");
                  success = false;
               
              }
          } 
         
            catch(Exception e)
            {
               
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
