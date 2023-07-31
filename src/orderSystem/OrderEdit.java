package src.orderSystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderEdit extends JFrame
{ 
    private JTextField orderNumField;       
    private JTextField nameField;       
    private JTextField quantityField;
    private JCheckBox comboCBox;
    private JRadioButton burgerButton, pizzaButton, tacoButton;
    private ButtonGroup buttonGroup;

    private JButton updateButton;
    private JButton cancelButton;

    private JPanel inputPanel;
    private JPanel buttonPanel;
    
    private OrderEdit thisEntry;
    private OrderListing orderListing;
    private boolean success = false;
    private String entreeSelection;
    private int radioSelect = 0;
  
   
    
   public OrderEdit(OrderListing orderListing)
    {
        this.orderListing = orderListing;
        thisEntry = this;
        setTitle("Edit Order");
        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        inputPanel.setPreferredSize(new Dimension(400, 80));
        buttonPanel.setPreferredSize(new Dimension(400, 100));

        inputPanel.add(new JLabel("Order Number:")); 
        orderNumField = new JTextField(10);
        inputPanel.add(orderNumField);
        inputPanel.add(new JLabel("New Customer Name:")); 
        nameField = new JTextField(30);
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("New Number of Items:")); 
        quantityField = new JTextField(3);
        inputPanel.add(quantityField);
        inputPanel.setLayout(new GridLayout(3,4));
      
        burgerButton = new JRadioButton("Burger");
        pizzaButton = new JRadioButton("Pizza");
        tacoButton = new JRadioButton("Taco");
        burgerButton.addActionListener(new BurgerListener());
        pizzaButton.addActionListener(new PizzaListener());
        tacoButton .addActionListener(new TacoListener());
      
        buttonGroup = new ButtonGroup();
        buttonGroup.add(burgerButton);
        buttonGroup.add(pizzaButton);
        buttonGroup.add(tacoButton);
        
        buttonPanel.add(burgerButton);
        buttonPanel.add(pizzaButton);
        buttonPanel.add(tacoButton);
        buttonPanel.add(new JLabel("Combo Meal?"));
        comboCBox = new JCheckBox();
        buttonPanel.add(comboCBox);
        updateButton  = new JButton("Update Order");
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

    public String getEntreeSelection()
    {
        return entreeSelection;
    }

    public void setEntreeSelection(String entreeSelection)
    {
        this.entreeSelection = entreeSelection;
    }
  
   private class BurgerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            radioSelect = 1;
            setEntreeSelection("burger");
        }
    }

  private class PizzaListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            radioSelect = 1;
            setEntreeSelection("pizza");
        }
    }

   private class TacoListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            radioSelect = 1;
            setEntreeSelection("taco");
        }
    }


    private class UpdateButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            try{
                int orderNum = Integer.parseInt(orderNumField.getText());
               
                String newName = nameField.getText();
                String[] nameParts = newName.split(" ");
                int newQuantity = Integer.parseInt(quantityField.getText());
                boolean newCombo = (comboCBox.isSelected()) ? true : false;
                String newEntree = getEntreeSelection();
              
                Order editOrder = orderListing.findOrder(orderNum);
              
               if ((nameParts.length == 2)  && (radioSelect==1) && editOrder!=null)
               { 
                  orderListing.updateOrderData(editOrder,orderNum, newName, newQuantity, newEntree, newCombo);
                  success = true;
               }
               else if (nameParts.length < 2)
              {
                  String message = "Please enter a first and last name.";
                  JOptionPane.showMessageDialog(null, message, "Notice!", JOptionPane.PLAIN_MESSAGE);
              }
              if (radioSelect==0)
                {
                    String message = "Please select a meal.";
                  JOptionPane.showMessageDialog(null, message, "Notice!", JOptionPane.PLAIN_MESSAGE);
                }

              if (success == false)
              {
                  String message = "Action Unsuccessfully. Order " + (String.valueOf(orderNum))+ " NOT Found";
                  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
                  orderNumField.setText("");
                  nameField.setText("");
                  quantityField.setText("");
                  buttonGroup.clearSelection();
                  comboCBox.setSelected(false);
                  radioSelect = 0;
                  
              }
              else
              {
                  String message = "Order " + (String.valueOf(orderNum))+ " successfully edited. Click Sort to see changes in Table.";
                  JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
                  orderNumField.setText("");
                  nameField.setText("");
                  quantityField.setText("");
                  buttonGroup.clearSelection();
                  comboCBox.setSelected(false);
                  radioSelect = 0;
                  success = false;
              
              }
          } 
            catch(NumberFormatException e)
            {
                String message = "Please enter a number for order and/or new number of items.";
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
