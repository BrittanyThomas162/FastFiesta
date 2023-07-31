package src.orderSystem;

import javax.swing.*;
import java.io.*;

public class Main {
  public static void main(String[] args) {

    try{
        File ofile = new File("orders.txt");
        if(ofile.createNewFile())
          System.out.println("File created: "+ ofile.getName());
        else
          System.out.println("File already exists");
        
        File cfile = new File("customer.txt");
        if(cfile.createNewFile())
          System.out.println("File created: "+ cfile.getName());
        else
          System.out.println("File already exists");
    }
    catch (IOException io)
    {
        io.printStackTrace();
        String message = "An error occurred";
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.PLAIN_MESSAGE);
    }
    
    JFrame mainMenuFrame = new JFrame("Fast Fiesta"); 
    MainMenuPanel panel = new MainMenuPanel();

    mainMenuFrame.getContentPane().add(panel);
    mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainMenuFrame.pack();
    mainMenuFrame.setVisible(true);
  
  }
}