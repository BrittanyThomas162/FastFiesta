package src.orderSystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class MealListing extends JFrame {
    
    private JPanel pnlDisplay;
    private JPanel pnlButton;
    private JButton closeButton;

    private JTable table;
    private DefaultTableModel model;

    private MealListing mlListing;
    
  public MealListing() {

        mlListing = this;
        setTitle("List of Meals Available");
        pnlDisplay = new JPanel();
        pnlButton = new JPanel();
        pnlDisplay.setLayout(new GridLayout(2,1));
        //pnlDisplay.setPreferredSize(new Dimension(400, 80));
        pnlDisplay.setPreferredSize(new Dimension(400, 250));
        String[] columnNames=  {"Entree Name",
                "Entree Price"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        
       
         String[] item1={"Burger Only","2.00"};
         model.addRow(item1);
         String[] item2={"Burger Combo","2.50"};
         model.addRow(item2);
         String[] item3={"Pizza Only","3.00"};
         model.addRow(item3);
         String[] item4={"Pizza Combo","3.50"};
         model.addRow(item4);
         String[] item5={"Tacos Only","4.00"};
         model.addRow(item5);
         String[] item6={"Tacos Combo","4.50"};
         model.addRow(item6);

         pnlDisplay.add(table);
         pnlDisplay.add(new JScrollPane(table));
    
        closeButton = new JButton("Close");

        closeButton.addActionListener(new CloseButtonListener());

        pnlButton.add(closeButton);
      
        add(pnlDisplay);
        pnlDisplay.add(pnlButton);

        pack();
        setVisible(true);
  
    }

  private class CloseButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            mlListing.setVisible(false);
        }
    }
}