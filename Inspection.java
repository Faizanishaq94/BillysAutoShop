
public class Inspection extends Appointment {

	public Inspection(int vehicleType, String name, String time, String day, boolean oil, String manufacturer, String model) {
		super(name, time, day, oil, manufacturer, model, vehicleType);
		int cost = (vehicleType == 1) ? Car.HRLY_COST : Motorcycle.HRLY_COST;
		setCost(cost);
	}
}
