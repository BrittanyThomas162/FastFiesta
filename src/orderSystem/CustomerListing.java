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
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;


public class CustomerListing extends JFrame {

    ArrayList<Customer> clist;
    private JPanel pnlDisplay;
    private JPanel pnlButton;
    private JButton closeButton;
    private JButton nameSortButton;
    private JButton removeCustomerButton;
    private JButton addCustomerButton;
    private JButton editCustomerButton;

    private JScrollPane scrollpane; 
  
    private JTable table;
    private DefaultTableModel model;

    private CustomerListing cListing;

  public CustomerListing()
  {
  
        cListing = this;
        setTitle("List of Customers");
        pnlDisplay = new JPanel();
        pnlButton = new JPanel();
        pnlDisplay.setLayout(new GridLayout(2,1));
      
        pnlDisplay.setPreferredSize(new Dimension(400, 250));

        clist = loadCustomers("customer.txt");
        String[] columnNames=  {"Customer Names",
                "Customer Address", "Customer Number"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        
      
         showTable(clist);

        scrollpane = new JScrollPane(table);
       
         
         pnlDisplay.add(scrollpane);

        closeButton = new JButton("Close");
        nameSortButton = new JButton("Sort by Name");
        removeCustomerButton = new JButton("Remove Customer");
        addCustomerButton = new JButton("Add Customer");
        editCustomerButton = new JButton("Edit Customer");
    
        closeButton.addActionListener(new CloseButtonListener());
        nameSortButton.addActionListener(new NameSortButtonListener());
        removeCustomerButton.addActionListener(new RemoveCustomerButtonListener());
        addCustomerButton.addActionListener(new AddCustomerButtonListener());
        editCustomerButton.addActionListener(new EditCustomerButtonListener());

        
        pnlButton.add(nameSortButton);
        pnlButton.add(editCustomerButton);
        pnlButton.add(addCustomerButton); 
        pnlButton.add(removeCustomerButton); 
        pnlButton.add(closeButton);
    
        add(pnlDisplay);
        pnlDisplay.add(pnlButton); 
      

        pack();
        setVisible(true);
  }

   public void updateCustomerData(String oName, String newName, String newAddress, String newNumber)
  {

    
              
       Customer editCustomer = findCustomer(oName);

  
       editCustomer.setName(newName);
       editCustomer.setAddress(newAddress);
       editCustomer.setNumber(newNumber);
 
      editRecord(oName, newName, newAddress, newNumber);
   }
  
   private void editRecord( String editTerm, String newName, String newAddress, String newNumber)
  {
    String filepath = "customer.txt";
    String tempFile = "temp.txt";
    File oldFile = new File(filepath);
    File newFile = new File(tempFile);
    String name = "";
    String address = "";
    String number = "";

    try
      {
        FileWriter fw = new FileWriter(tempFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        Scanner cScanner = new Scanner(new File(filepath));
        cScanner.useDelimiter("[:\n]");
        while(cScanner.hasNext())
        {
          name = cScanner.next();
          address = cScanner.next();
          number = cScanner.next();
          if(name.equals(editTerm))
          {
            pw.println(newName+":"+newAddress+":"+newNumber);
          }
           else 
                {
                    pw.println(name + ":" + address + ":" + number);                  
                }
            }
        
            cScanner.close();
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);
        }
        
        catch (IOException io) {
            System.out.println("An error occurred.");
            io.printStackTrace();
        }
      
        
      }

    private void removeRecord( String removeTerm)
  {
    String filepath = "customer.txt";
    String tempFile = "temp.txt";
    File oldFile = new File(filepath);
    File newFile = new File(tempFile);
    String name = "";
    String address = "";
    String number = "";

    try
      {
        FileWriter fw = new FileWriter(tempFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        Scanner cScanner = new Scanner(new File(filepath));
        cScanner.useDelimiter("[:\n]");
        while(cScanner.hasNext())
        {
          name = cScanner.next();
          address = cScanner.next();
          number = cScanner.next();
          if(!name.equals(removeTerm))
          { 
            pw.println(name + ":" + address + ":" + number);  
          }
           
            }
        
            cScanner.close();
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);
        }
        
        catch (IOException io) {
            System.out.println("An error occurred.");
            io.printStackTrace();
        }
      
        
      }
  
  private ArrayList<Customer> loadCustomers(String pfile)
    {
        Scanner pscan = null;
        ArrayList<Customer> clist = new ArrayList<Customer>();
        try
        {
            pscan  = new Scanner(new File(pfile));
            while(pscan.hasNext())
            {
                String [] nextLine = pscan.nextLine().split(":");
              
                String name = nextLine[0];
                String address = nextLine[1];
                String number = nextLine[2];
               
                Customer c = new Customer(name, address, number);
                clist.add(c);
            
           }

            pscan.close();
        }
        catch(IOException e)
        {}
        return clist;
    }


  
  public ArrayList<Customer> getClist()
  {
    return clist;
  }

  public Customer findCustomer(String CstmrName)
    {
        Customer retval =  null;
        for (Customer c:clist)
            if (c.getName().equals(CstmrName)){
                retval = c;
                break;
            }
        return retval;
    }

   private void appendToCFile(Customer customer) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
    
    try {
          fw = new FileWriter("customer.txt", true);
          bw = new BufferedWriter(fw);
          pw = new PrintWriter(bw);
          String customerData = customer.getName() +":" + customer.getAddress() +":" + customer.getNumber();
          
          pw.println(customerData);
    
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
  
  
   private void addToTable(Customer c)
    {
        String[] item={c.getName(),""+ c.getAddress(),""+c.getNumber()};
        model.addRow(item);     
      

    }

  private void showTable(ArrayList<Customer> clist)
    {
       for(Customer c: clist)
       {
           addToTable(c);
       }
        
    }
  
  public void addCustomer(Customer c)
    {
        clist.add(c);
        addToTable(c);
        appendToCFile(c);
        

    }

  public void removeCustomer(Customer c)
  {
    clist.remove(c);
    cListing.clearTable();
    cListing.showTable(clist);
    removeRecord(c.getName());
  }
  
  public void clearTable()
  {
   
   int rows = model.getRowCount(); 
   for(int i = rows - 1; i >=0; i--)
{
   model.removeRow(i); 
}
  }
  

        private class CloseButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            cListing.setVisible(false);
        }
    }

  private class NameSortButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      Collections.sort(clist);
      clearTable();
      showTable(clist);
      cListing.pack();
      cListing.setVisible(true);
    }
  }

  private class RemoveCustomerButtonListener implements ActionListener
  {

    public void actionPerformed(ActionEvent event)
    {
      new CustomerDelete(cListing);
      clearTable();
      showTable(clist);
      cListing.pack();
      cListing.setVisible(true);
    }
  }

  private class AddCustomerButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      new CustomerEntry(cListing);
    }
  }

  private class EditCustomerButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      CustomerEdit CustomerEdit = new CustomerEdit(cListing);
      
                  clist= loadCustomers("customer.txt");
            model.setRowCount(0);
            
      clearTable();
      showTable(clist);
      cListing.pack();
      cListing.setVisible(true);
    
    }
  }
  
}