package src.orderSystem;

public class Customer implements Comparable<Customer> {
	
  private String name;
	private String address;
	private String number;
	
	public Customer(String name, String address, String number)
	{
		this.name = name;
		this.address = address;
		this.number = number;
	
	}

  public Customer() {}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public int compareTo(Customer other){
     return this.getName().compareTo(other.getName());          
 }

  public String toString()
  {
    return getName()+" , "+getAddress()+" , "+getNumber();
  }

}