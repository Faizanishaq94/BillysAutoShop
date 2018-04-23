/*
 * @authors
Faizan Ishaq Fishaq@masonlive.gmu.edu
Saad Durrani Sdurran2@masonlive.gmu.edu
Mauricio Zevallos Mzevallo@masonlive.gmu.edu 
 */
public class Inspection extends Appointment { //subclass of Appointment

	/*
	 * constructor that takes vehicleType, name, time, day, if oil change, manufacturer and model as parameters
	 */
	public Inspection(int vehicleType, String name, String time, String day, boolean oil, String manufacturer, String model) {
		super(name, time, day, oil, manufacturer, model, vehicleType); //respective parameters passed to super class
		int cost = (vehicleType == 1) ? Car.HRLY_COST : Motorcycle.HRLY_COST; //setting the cost
		setCost(cost); 
	}
	
	//@return - String respresentation of the object
	public String toString() {
		return "Inspection -- " + super.toString();
	}
	//@return - String representation of how to store the object
	public String storeAsClosedAppt() {
		return "Inspection," + super.storeAsClosedAppt();
	}
}
