
public class Motorcycle extends Vehicle {

	public static final int INSPECTION = 50;
	public static final int HRLY_COST = 50;
	
	public Motorcycle(String manufacturer, String model) {
		super(manufacturer, model);
	}
	
	public Motorcycle(Vehicle m) {
		super(m);
	}
	
}
