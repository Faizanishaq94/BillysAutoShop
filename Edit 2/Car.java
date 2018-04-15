
public class Car extends Vehicle {

	public static final int INSPECTION = 75;
	public static final int HRLY_COST = 100;
	
	public Car(String manufacturer, String model) {
		super(manufacturer, model);
	}
	
	public Car(Vehicle c) {
		super(c);
	}

}
