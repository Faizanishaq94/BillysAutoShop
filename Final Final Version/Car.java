/*
 * @authors
Faizan Ishaq Fishaq@masonlive.gmu.edu
Saad Durrani Sdurran2@masonlive.gmu.edu
Mauricio Zevallos Mzevallo@masonlive.gmu.edu 
 */
public class Car extends Vehicle { //extends the vehicle class

	public static final int INSPECTION = 75; //cost for inspection
	public static final int HRLY_COST = 100; //cost for hourly cost
	
	//constructor that takes manufacturer and model as parameters
	public Car(String manufacturer, String model) {
		super(manufacturer, model);
	}
	//constructor that creates a copy
	public Car(Vehicle c) {
		super(c);
	}

}
