/*
 * @authors
Faizan Ishaq Fishaq@masonlive.gmu.edu
Saad Durrani Sdurran2@masonlive.gmu.edu
Mauricio Zevallos Mzevallo@masonlive.gmu.edu 
 */
public class Motorcycle extends Vehicle { //extends the vehicle class

	public static final int INSPECTION = 50; //cost for inspection
	public static final int HRLY_COST = 50; //hourly cost
	
	//constructor - takes manufacturer and model and parameters
	public Motorcycle(String manufacturer, String model) {
		super(manufacturer, model);
	}
	//constructor - creates a copy of the object
	public Motorcycle(Vehicle m) {
		super(m);
	}
	
}
