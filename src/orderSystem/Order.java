package src.orderSystem;

import java.sql.Date;

public class Order implements Comparable<Order>{
  
    private String cName;
    private static int count=0;
    private Meal meal;
    private int ordernumber;
    private int quantity;
    private double cost;
    private String date;

public Order(String customername,int quantity, boolean combo, String entree) {
    
    cName = customername;
    this.quantity=quantity;
    ordernumber=count;
    count+= 1;
    meal = new Meal(entree, combo);
    cost = meal.getCost();
    date = new Date(System.currentTimeMillis()).toString();
    
  }

  public Order(){}

  public String getCName()
	{
		return cName;
	}

	public void setCName(String newName) 
	{
		  cName = newName;
	}
  public int getquantity() {
    return quantity; }

  public void setquantity(int quantity){
    this.quantity= quantity;}

  public int getordernumber(){
    return ordernumber;
  }
  public void setordernumber(int ordernumber){
    this.ordernumber=ordernumber;
  }
  
  public Meal getMeal()
  {
      return meal;
  }

  public void setMeal(String entree, boolean combo){
      this.meal = new Meal(entree, combo);
      this.cost = meal.getCost();
  }

  
  public String getDate(){
    return date;
  }

  public void setDate(String newDate){
    this.date = newDate;
  }

  public int compareTo(Order other){
     return this.getordernumber() - other.getordernumber();       
          }
  
}
