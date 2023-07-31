package src.orderSystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class Receipt extends JFrame {

  Order cOrder;
  private JPanel pnlDisplay;
  private JPanel headerPanel;
  private JTable table;
  private DefaultTableModel model;

  public Receipt (Order o) {
    cOrder = o;
    setTitle( "Receipt");
    
    headerPanel = new JPanel();
    headerPanel.add(new JLabel("FAST FIESTA"));
    String oNum = Integer.toString(cOrder.getordernumber());
    headerPanel.add(new JLabel("Order #: " +  oNum));
    java.util.Date date = new java.util.Date();    
    headerPanel.add(new JLabel(date.toString()));
    headerPanel.add(new JLabel("Customer: " + cOrder.getCName()));
    headerPanel.setPreferredSize(new Dimension(100, 50));
    headerPanel.setLayout(new GridLayout(4,1));
    
    String[] columnNames = { "Quantity", "Food Item", "Cost" };
    model = new DefaultTableModel(columnNames, 0);
    table = new JTable(model);
    addToTable(cOrder);
    table.setPreferredScrollableViewportSize(new Dimension(300, 50));
    table.setFillsViewportHeight(true); 
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    JPanel pnlTable = new JPanel();
    pnlTable.add(table);
    pnlTable.add(new JScrollPane(table));
    pnlTable.setPreferredSize(new Dimension(100, 100));
    
    pnlDisplay=new JPanel();
    pnlDisplay.add(new JLabel("SubTotal:"));
    String subtotal = ("$ " + String.format("%,.2f", getSubTotal()));
    pnlDisplay.add(new JLabel(subtotal));
    pnlDisplay.add(new JLabel("Discount:"));
    String discount = ("$ " + String.format("%,.2f", getDiscount()));
    pnlDisplay.add(new JLabel(discount));
    pnlDisplay.add(new JLabel("Total:"));
    String total = ("$ " + String.format("%,.2f",getTotal()));
    pnlDisplay.add(new JLabel(total)); 
    pnlDisplay.setLayout(new GridLayout(3,4));
    pnlDisplay.setPreferredSize(new Dimension(200, 50));

    add(headerPanel, BorderLayout.NORTH);
    add(pnlTable, BorderLayout.CENTER);
    pnlTable.add(pnlDisplay, BorderLayout.SOUTH);
    setPreferredSize(new Dimension(350, 350));
    pack();
    setVisible(true);
  }

  private void addToTable(Order o) {
    String quantity = Integer.toString(o.getquantity());
    String entree = o.getMeal().getEntree();
    if (o.getMeal().getCombo() == true)
        entree += " combo";
    String cost = Double.toString(o.getMeal().getCost());          
    String[] item = { quantity,"" + entree,""+ cost };
    model.addRow(item);

  }
  public double getSubTotal(){
      double subTotal = (cOrder.getMeal().getCost() * cOrder.getquantity());
    return subTotal;
  }

  public double getDiscount() 
  {
      return (0.05 * getSubTotal());
  }
  
 public double getTotal(){
    double total = getSubTotal() - getDiscount();
    return total;
  }
  
}