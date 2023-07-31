package src.orderSystem;

public class Meal
{

  String entree;
  boolean combo;
  double cost;
  public Meal(String entree, boolean combo){
    this.entree = entree;
    this.combo = combo;
    if (entree == "burger" && combo == false)
      cost = 2.00;
    else if (entree == "burger" && combo == true)
      cost = 2.50;
    else if (entree == "pizza" && combo == false)
      cost = 3.00;
    else if (entree == "pizza" && combo == true)
      cost = 3.50;
    else if (entree == "taco" && combo == false)
      cost = 4.00;
    else if (entree == "taco" && combo == true)
      cost = 4.50;
  }

  public String toString(){
    if (getCombo() == false)
      return "Meal is " + getEntree() + " only and costs $"+getCost();
    else
      return "Meal is " + getEntree() + " combo and costs $"+getCost();
  }

  public String getEntree(){
    return entree;
  }

  public double getCost(){
    return cost;
  }

  public boolean getCombo(){
    return combo;
  }

  public void setEntree(String newEntree){
    this.entree = newEntree; 
  }

  public void setCost(double newCost){
    this.cost = newCost;
  }

  public void setCombo(boolean newCombo){
    this.combo = newCombo;
  }
  
  public int compareTo(Meal other){
    if (this.getCost() > other.getCost())
       return 1;
    if (this.getCost() == other.getCost())
       return 0;
   else
       return -1;
  }
  
}