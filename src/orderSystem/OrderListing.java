package src.orderSystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;


public class OrderListing extends JFrame {
    private JButton     addOrderButn;
    private JButton     editOrderButn;
    private JButton     deleteOrderButn;
    private JButton     closeButn;
    private JButton     sortONumButn;
    private JButton     sortDateButn;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    private ArrayList<Order> olist;
    private OrderListing thisForm;
    private JTable table;
    private DefaultTableModel model;

    public OrderListing() {
        thisForm = this;
        setTitle("List of Orders");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        pnlDisplay.setLayout(new GridLayout(2,1));
        //pnlDisplay.setPreferredSize(new Dimension(400, 80));
      
        olist= loadOrders("orders.txt");
        String[] columnNames=  {"Order No.",
                "Date",
                "Customer",
                "Quantity", "Entree", "Unit Cost", "Sub Total"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        showTable(olist);
        
        table.setPreferredScrollableViewportSize(new Dimension(500, olist.size()*15 +50));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.setFillsViewportHeight(true);
        pnlDisplay.add(table);
        pnlDisplay.add(new JScrollPane(table));
        
        addOrderButn  = new JButton("Add Order");
        editOrderButn = new JButton("Edit Order");
        deleteOrderButn = new JButton("Delete Order");
        sortONumButn = new JButton("Sort by Order No.");
        sortDateButn  = new JButton("Sort by Date");
        closeButn   = new JButton("Close");
        
        addOrderButn.addActionListener(new PlaceOrderButnListener());
        editOrderButn.addActionListener(new EditOrderButnListener());
        deleteOrderButn.addActionListener(new DeleteOrderButnListener());
        sortONumButn.addActionListener(new SortONumButtonListener());
        sortDateButn.addActionListener(new SortDateButtonListener());
        closeButn.addActionListener(new CloseButtonListener());

        pnlCommand.setPreferredSize(new Dimension(200, 80));
        pnlCommand.add(addOrderButn);
        pnlCommand.add(editOrderButn);
        pnlCommand.add(deleteOrderButn);
        pnlCommand.add(sortONumButn);
        pnlCommand.add(sortDateButn);
        pnlCommand.add(closeButn);

        add(pnlDisplay);
        pnlDisplay.add(pnlCommand);
        setPreferredSize(new Dimension(600, 300));
        pack();
        setVisible(true);
      
    }


    private void showTable(ArrayList<Order> olist) {
      for (int i = 0; i < olist.size(); i++) {
        if (olist.size() > 0)
          addToTable(olist.get(i));
      }
    }

  private void addToTable(Order o) {
    String orderNum = Integer.toString(o.getordernumber());
    String date  = o.getDate().toString();
    String cName  = o.getCName();
    String quantity = Integer.toString(o.getquantity());
    String entree = o.getMeal().getEntree();
    if (o.getMeal().getCombo() == true)
        entree += " combo";
    String cost = Double.toString(o.getMeal().getCost()); 
    String total = Double.toString(o.getquantity() * o.getMeal().getCost());
    String[] item = { orderNum,"" + date,"" + cName,"" + quantity,"" + entree,""+ cost,"" + total};
    model.addRow(item);

  }

  public void addOrder(Order o)
  {
      olist.add(o);
      addToTable(o);
      appendToOFile(o);
  }

    public Order findOrder(int ordernumber)
    {
        Order retval =  null;
        for (Order o:olist)
            if (o.getordernumber() == ordernumber){
                retval = o;
                break;
            }
        return retval;
    }

    public void updateOrderData(Order o, int orderNum, String newName, int newQuantity, String newEntree, boolean newCombo)
    {  
        if (o!= null)  {
            o.setCName(newName);
            o.setquantity(newQuantity);
            o.setMeal(newEntree, newCombo);

            String oNum = Integer.toString(orderNum);
            String newQuant = Integer.toString(newQuantity);
            String newComboM = "0";
            if (newCombo)
              newComboM = "1";
            String newCost = Double.toString(o.getMeal().getCost());
            
            editRecord(oNum, newName,newQuant, newEntree, newComboM, newCost);

        }
     }
  
    public void removeOrder(Order o, int oNum)
  {
      int index =-1; 
        for (int i=0; i<olist.size(); i++){
            if (o==olist.get(i)){
                index = i;
                break;
            }
        }     
        if (index >= 0){
            olist.remove(index);
        }
        
        String orderNum = Integer.toString(oNum);
        removeRecord(orderNum);
  }
  
  public void clearTable()
  {
     int rows = model.getRowCount(); 
     for(int i = rows - 1; i >=0; i--)
    {
       model.removeRow(i); 
    }
  }

  private ArrayList<Order> loadOrders(String ofile)
  {
        Scanner oscan = null;
        ArrayList<Order> olist = new ArrayList<Order>();
        try
        {
            oscan  = new Scanner(new File(ofile));
            while(oscan.hasNext())
            {
                String [] nextLine = oscan.nextLine().split(":");
                int oNum = Integer.parseInt(nextLine[0]);
                String date = nextLine[1];
                String cName = nextLine[2];
                int quantity = Integer.parseInt(nextLine[3]);
                String entree = nextLine[4];
                boolean combo = false;
                if (nextLine[5].equals("1"))
                    combo = true;
                double cost = Double.parseDouble(nextLine[6]);
                
                Order o = new Order(cName, quantity, combo, entree);
                o.setordernumber(oNum);           
                o.setDate(date);
                o.getMeal().setCost(cost);
                olist.add(o);
            }

            oscan.close();
        }
        catch(IOException e)
        {
             System.out.println(e.getMessage());
        }
        return olist;
    }

  private void appendToOFile(Order order) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
    
    try {
          fw = new FileWriter("orders.txt", true);
          bw = new BufferedWriter(fw);
          pw = new PrintWriter(bw);
          String orderData = Integer.toString(order.getordernumber()) +":" + order.getDate() +":" + order.getCName() + ":" + Integer.toString(order.getquantity())+ ":" + order.getMeal().getEntree();
          if (order.getMeal().getCombo() == true)
            orderData += ":1:";
          else 
            orderData += ":0:";
          orderData += Double. toString(order.getMeal().getCost()) ;
          pw.println(orderData);
    
          System.out.println("Data Successfully appended into file");
          pw.flush();
          pw.close();
          bw.close();
          fw.close();

        }
      catch (IOException io) {
            System.out.println("An error occurred.");
            io.printStackTrace();
        }
      
  }
  

  private void editRecord(String editTerm, String newName, String newQuantity, String newEntree, String newCombo, String newCost) {
        String filePath = "orders.txt";
        String tempFile = "temp.txt";
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);
        Scanner oscan = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
        String orderNum, date, cName, quantity, entree, combo, cost;
        
        try{
          
            fw = new FileWriter(tempFile, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            oscan = new Scanner(new File(filePath));
            oscan.useDelimiter("[:\n]");

            while (oscan.hasNext())
            {
                orderNum = oscan.next();
                date = oscan.next();
                cName = oscan.next();
                quantity = oscan.next();
                entree = oscan.next();
                combo = oscan.next();
                cost = oscan.next();

                if (orderNum.equals(editTerm))
                {
                    pw.println(orderNum + ":" + date + ":" + newName + ":" + newQuantity + ":" + newEntree + ":" + newCombo + ":" + newCost);
                }
                else 
                {
                    pw.println(orderNum + ":" + date + ":" + cName + ":" + quantity + ":" + entree + ":" + combo + ":" + cost);                  
                }
            }

            oscan.close();
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);

        }
        catch (IOException io) {
            System.out.println("An error occurred.");
            io.printStackTrace();
        }
      
    }

  private void removeRecord(String removeTerm) 
  {
      String filePath = "orders.txt";
      String tempFile = "temp.txt";
      File oldFile = new File(filePath);
      File newFile = new File(tempFile);
      Scanner oscan = null;
      FileWriter fw = null;
      BufferedWriter bw = null;
      PrintWriter pw = null;
      String orderNum, date, cName, quantity, entree, combo, cost;

      try{
            fw = new FileWriter(tempFile, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            oscan = new Scanner(new File(filePath));
            oscan.useDelimiter("[:\n]");

            while (oscan.hasNext())
            {
                orderNum = oscan.next();
                date = oscan.next();
                cName = oscan.next();
                quantity = oscan.next();
                entree = oscan.next();
                combo = oscan.next();
                cost = oscan.next();

                if (!orderNum.equals(removeTerm))
                {
                    pw.println(orderNum + ":" + date + ":" + cName + ":" + quantity + ":" + entree + ":" + combo + ":" + cost);                  
                }
            }
            oscan.close();
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);

        }
        catch (IOException io) {
            System.out.println("An error occurred.");
            io.printStackTrace();
        }
    }
  
  private class DateOrder implements Comparator<Order> {
    public int compare(Order o1, Order o2)
    {
        //Date is stored in format yyyy-mm-dd. This splits the dates into 3 parts  
        String [] dateParts1 = o1.getDate().split("-");
        String [] dateParts2 = o2.getDate().split("-");
        //Compares by year
        int retval = dateParts1[0].compareTo(dateParts2[0]);
        //if year is the same, compare month
            if (retval == 0)
                retval = dateParts1[1].compareTo(dateParts2[1]);
        //if month is the same compare day
                if (retval == 0)
                    retval = dateParts1[2].compareTo(dateParts2[2]);
        
        return retval;
    }
  }

  private class PlaceOrderButnListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            OrderEntry orderEntry = new OrderEntry(thisForm);   
            model.setRowCount(0);
            showTable(olist);
        }
    }

    private class EditOrderButnListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            OrderEdit orderEdit = new OrderEdit(thisForm);   
            
            model.setRowCount(0);
            thisForm.setVisible(false);
            olist= loadOrders("orders.txt");
            clearTable();
            showTable(olist);
            thisForm.pack();
            thisForm.setVisible(true);
            
        }
    }

    private class DeleteOrderButnListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            model.setRowCount(0);
            OrderDelete orderDelete = new OrderDelete(thisForm); 
            olist= loadOrders("orders.txt");
            clearTable();
            showTable(olist);
            thisForm.pack();
            thisForm.setVisible(true);
        }
    }

    private class SortONumButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            model.setRowCount(0);
            Collections.sort (olist);
            showTable(olist);
        }
    }

    private class SortDateButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            model.setRowCount(0);
            Collections.sort (olist, new DateOrder().reversed());
            showTable(olist);
        }
    }
    private class CloseButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            thisForm.setVisible(false);
        }
    }
  
}

