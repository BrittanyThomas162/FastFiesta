package src.orderSystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderDelete extends JFrame
{ 
    private JTextField orderNumField;       
    private JButton deleteButton;
    private JButton cancelButton;
   
    private JPanel inputPanel;
    private JPanel buttonPanel;

    private OrderListing orderListing;
    private OrderDelete thisEntry;
    private boolean success = false;

   public OrderDelete(OrderListing orderListing)
    {
        this.orderListing = orderListing;
        thisEntry = this;
        setTitle("Delete Order");
        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        inputPanel.setPreferredSize(new Dimension(200, 80));
        buttonPanel.setPreferredSize(new Dimension(400, 80));

        inputPanel.add(new JLabel("Order Number:")); 
        orderNumField = new JTextField(10);
        inputPanel.add(orderNumField);
       
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
                int orderNum = Integer.parseInt(orderNumField.getText());
                Order delOrder = orderListing.findOrder(orderNum);
                
               if (delOrder != null)
               { 
                  orderListing.removeOrder(delOrder, orderNum);
                  success = true;
               }
              if (success == false)
              {
                  String message = "Action Unsuccessfully. Order " + (String.valueOf(orderNum))+ " NOT Found";
                  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
                  orderNumField.setText("");
              }
              else
              {
                  String message = "Order " + (String.valueOf(orderNum))+ " successfully deleted. Click Sort to see changes in Table.";
                  JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
                  orderNumField.setText("");
                  success = false;
              }
          } 
            catch(NumberFormatException e)
            {
                String message = "Please enter a number";
                JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
            
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
