package src.orderSystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderEntry extends JFrame
{
    private JTextField nameField;       
    private JTextField quantityField;

    private JCheckBox comboCBox;
    private JButton submitButton;
    private JButton cancelButton;
    private JRadioButton burgerButton, pizzaButton, tacoButton;
    private ButtonGroup buttonGroup;

    private JPanel inputPanel;
    private JPanel buttonPanel;
    
    private Order order;
    private String entreeSelection;
    private OrderListing orderListing;
    private OrderEntry thisEntry;
    private int radioSelect = 0;
    
    public OrderEntry(OrderListing orderListing)
    {
        this.orderListing = orderListing;
        thisEntry = this;
        order = new Order(); 
        setTitle("Place Order");
        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        inputPanel.setPreferredSize(new Dimension(400, 80));
        buttonPanel.setPreferredSize(new Dimension(400, 100));

        inputPanel.add(new JLabel("Customer Name:")); 
        nameField = new JTextField(30);
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Number of Items:")); 
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
        submitButton  = new JButton("Submit Order");
        cancelButton =  new JButton("Cancel / Close");

        submitButton.addActionListener(new SubmitButtonListener());
        cancelButton.addActionListener(new CloseButtonListener());

        buttonPanel.add(submitButton);
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

    private class SubmitButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            try{
              
                String name = nameField.getText();
                String[] nameParts = name.split(" ");
                int quantity = Integer.parseInt(quantityField.getText());
                boolean isCombo = (comboCBox.isSelected()) ? true : false;
                String entree = getEntreeSelection();
              
               if ((nameParts.length == 2)  && (radioSelect==1))
               {  
                  order = new Order(name, quantity, isCombo, entree);
                  orderListing.addOrder(order);
                  
                  nameField.setText("");
                  quantityField.setText("");
                  buttonGroup.clearSelection();
                  comboCBox.setSelected(false);
                  radioSelect = 0;
                  thisEntry.pack();
                  thisEntry.setVisible(true);
                  
                  Receipt receipt = new Receipt(order);
                 
               }
              else if (nameParts.length < 2)
              {
                  String message = "Please enter a first and last name.";
                  JOptionPane.showMessageDialog(null, message, "Notice!", JOptionPane.PLAIN_MESSAGE);
              }
              else if (radioSelect==0)
                {
                    String message = "Please select a meal.";
                  JOptionPane.showMessageDialog(null, message, "Notice!", JOptionPane.PLAIN_MESSAGE);
                }

          } 
            catch(NumberFormatException e)
            {
                String message = "Please enter a number for number of items.";
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
