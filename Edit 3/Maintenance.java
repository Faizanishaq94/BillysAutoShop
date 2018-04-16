
public class Maintenance extends Appointment {
	
	private int numParts;
	private double numHours;
	public static final int COST_PER_PART = 50;
	
	
	public Maintenance(int parts, int vehicleType, double hrs, String name, String time, String day, boolean oil, String manufacturer, String model) {
		super(name, time, day, oil, manufacturer, model, vehicleType);
		setParts(parts);
		setHrs(hrs);
		setCost(vehicleType);
	}
	
	public void setCost(int type) {
		int hrly = (type == 1) ? Car.HRLY_COST : Motorcycle.HRLY_COST;
		super.setCost(numHours * hrly + numParts * COST_PER_PART);
	}
	
	public void setParts(int parts) {
		if(parts >= 0)
			numParts = parts;
		else
			throw new IllegalArgumentException("Parts cannot be a negative value");
	}
	
	public void setHrs(double hrs) {
		if(hrs <= 0) {
			throw new IllegalArgumentException("Hours must be a positive value");
		}
		numHours = hrs;
	}
	
	public int getParts() {
		return numParts;
	}
	
	public double getHrs() {
		return numHours;
	}
	
	public String toString() {
		return "Maintenance -- " + super.toString();
	}
	
	public String storeAsClosedAppt() {
		return "Maintenance," + numParts + "," + numHours + "," + super.storeAsClosedAppt();
	}
	
}
