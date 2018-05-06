/*
 * @authors
Faizan Ishaq Fishaq@masonlive.gmu.edu
Saad Durrani Sdurran2@masonlive.gmu.edu
Mauricio Zevallos Mzevallo@masonlive.gmu.edu 
 */
public class Maintenance extends Appointment { //extends Appointment class
	
	private int numParts; //number of parts
	private double numHours; //number of hours
	public static final int COST_PER_PART = 50; //cost associated with each part
	
	
	/*
	 * Constructor that takes parts, vehicle type, hours, name, time, day, if oil change, manufacturer and model as parameters
	 */
	public Maintenance(int parts, int vehicleType, double hrs, String name, String time, String day, boolean oil, String manufacturer, String model) {
		super(name, time, day, oil, manufacturer, model, vehicleType); //passing respective values to super class constructor
		setParts(parts);
		setHrs(hrs);
		setCost(vehicleType);
	}
	
	//@return - void, @param - integer for the type of vehicle. Setting the cost here
	public void setCost(int type) {
		int hrly = (type == 1) ? Car.HRLY_COST : Motorcycle.HRLY_COST;
		super.setCost(numHours * hrly + numParts * COST_PER_PART);
	}
	
	//@return - void, @param - number of parts, sets the number of parts
	public void setParts(int parts) {
		if(parts >= 0)
			numParts = parts;
		else
			throw new IllegalArgumentException("Parts cannot be a negative value");
	}
	
	//@return - void, @param - hours, setter for hours
	public void setHrs(double hrs) {
		if(hrs <= 0) {
			throw new IllegalArgumentException("Hours must be a positive value");
		}
		numHours = hrs;
	}
	
	//@param - none, @return - parts, getter for the parts
	public int getParts() {
		return numParts;
	}
	
	//@param - none, @return - number of hours. getter for hours
	public double getHrs() {
		return numHours;
	}
	
	//@return - String representation of object, @param - none
	public String toString() {
		return "Maintenance -- " + super.toString();
	}
	
	//@return - String representation of object to easily store in a file
	public String storeAsClosedAppt() {
		return "Maintenance," + numParts + "," + numHours + "," + super.storeAsClosedAppt();
	}
	
}
