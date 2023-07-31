package src.orderSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel
  {
      private JButton showMenu, orderOptions, customerOptions, exit;

      public MainMenuPanel()
      {
          
          JPanel headerPanel = new JPanel();
          headerPanel.add(new JLabel("~~~~ FAST FIESTA ~~~~"));
          headerPanel.add(new JLabel("ADMIN MAIN MENU"));
          headerPanel.setPreferredSize(new Dimension(200,50));
        
          JPanel buttonPanel = new JPanel();
          
          showMenu = new JButton("Food Menu");
          orderOptions = new JButton("Order Menu Options");
          customerOptions = new JButton("Customer Menu Options");
          exit = new JButton("      Exit      ");
      
          showMenu.addActionListener(new ShowMenuButnListener());
          orderOptions.addActionListener(new OrderButnListener());
          customerOptions.addActionListener(new CustomerButnListener());
          exit.addActionListener(new ExitButtonListener());

          buttonPanel.add(showMenu);
          buttonPanel.add(customerOptions);
          buttonPanel.add(orderOptions);
          buttonPanel.add(exit);
          buttonPanel.setPreferredSize(new Dimension(210,200));
        
          add(headerPanel, BorderLayout.NORTH);
          add(buttonPanel, BorderLayout.CENTER);

          setPreferredSize(new Dimension(400, 300));
      }

    private class ShowMenuButnListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            MealListing mealListing = new MealListing(); 
          
        }
    }

     private class OrderButnListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            OrderListing orderListing = new OrderListing(); 
          
        }
    }


    private class CustomerButnListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            CustomerListing customerListing = new CustomerListing();        
        }
    }
    
    private class ExitButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    } 
    
}