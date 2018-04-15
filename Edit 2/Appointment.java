import java.util.*;
public abstract class Appointment implements Comparable<Appointment> {

	private Vehicle vehicle;
	private String day;
	private String id;
	private String customerName;
	private double cost;
	private String time;
	private boolean oilChange;
	private static int idNum = 0;
	public static final String ID_VAL = "BLYAUTSHP";
	public static int apptNums = 0;
	public int type;
	public static final String[] DAYS_OF_THE_WEEK = {"MON","TUES","WED","THUR","FRI"};
	public static final int OIL_CHANGE_COST = 50;
	
	public Appointment(String name, String time, String day, boolean oil, String manufacturer, String model, int type) {
		id = ID_VAL + ++idNum;
		setDay(day);
		setName(name);
		setTime(time);
		setVehicle(manufacturer, model, type);
		cost += (oil) ? OIL_CHANGE_COST : 0;
		apptNums++;
		setType(type);
		
	}
	
	public Vehicle getVehicle() {
		Vehicle v = (vehicle instanceof Car) ? new Car(vehicle) : new Motorcycle(vehicle);
		return v;

		
	}
	
	public void setVehicle(String manufacturer, String model, int type) {
		vehicle = (type == 1) ? new Car(manufacturer, model) : new Motorcycle(manufacturer, model);
	}

	@Override
	public int compareTo(Appointment o) {
		if(timeComparator() < o.timeComparator()) {
			return -1;
		}
		else if(timeComparator() > o.timeComparator()) {
			return 1;
		}
		return 0;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public boolean equals(Appointment app) {
		return (id.equals(app.id)) ? true : false;
	}
	
	public int timeComparator() {
		return Integer.parseInt(time.substring(0, time.indexOf(":")) + time.substring(time.indexOf(":") + 1));
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		for(String i : DAYS_OF_THE_WEEK) {
			if (day.equals(i)) {
				this.day = day;
				return;
			}
		}
		throw new IllegalArgumentException("Invalid day value");	
	}
	
	public String getID() {
		return id;
	}
	
	public void setCustomerName(String name) {
		customerName = name;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost += cost;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		if (name.equals("")) {
			throw new IllegalArgumentException("Name is required");
		}
		customerName = name;
	}
	
	public void setTime(String time) {
		if(time.charAt(0) == '0' || time.length() > 5 || time.length() < 4 || (time.indexOf(":") < 1 || time.indexOf(":") > 2)) {
			throw new IllegalArgumentException("Invalid time format");
		}
		try {
			Integer.parseInt(time.substring(0, time.indexOf(":")) + time.substring(time.indexOf(":") + 1));
			this.time = time;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Invalid Time format");
		}
	}
	
	public static void decrementAppts() {
		--apptNums;
	}
	
	public static int getApptNums() {
		return apptNums;
	}
	
	public String toString() {
		return customerName + " | " + id + " | " + day + " @ " + time + " | Oil Change - " + oilChange + " | Vehicle: " + vehicle.toString();
	}
	
	public String storeAsClosedAppt() {
		return customerName + "," + id + "," + day + "," + time + "," + cost + "," + oilChange + "," + vehicle.getManufacturer() + "," + vehicle.getModel() + "," + type;
	}
	
	
	
	
	
}
