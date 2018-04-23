/*
 * @authors
Faizan Ishaq Fishaq@masonlive.gmu.edu
Saad Durrani Sdurran2@masonlive.gmu.edu
Mauricio Zevallos Mzevallo@masonlive.gmu.edu 
 */
public abstract class Appointment implements Comparable<Appointment> { //abstract user class, implements comparable

	private Vehicle vehicle; //vehicle name
	private String day; //day of appointment
	private String id; //id of appointment
	private String customerName; //customer name
	private double cost; //cost of appt
	private String time; //time of appt
	private boolean oilChange; //if an oil change was requested
	private static int idNum = 0; //id number for current appt
	public static final String ID_VAL = "BLYAUTSHP"; //defines how a customer id will begin
	public static int apptNums = 0; //total number of appts
	public int type; //type of appt
	public static final String[] DAYS_OF_THE_WEEK = {"MON","TUES","WED","THUR","FRI"}; //defines days of the week
	public static final int OIL_CHANGE_COST = 50;
	public static final String[] TIMES = {"9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30", 
			 "14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00"}; //allowed appt times
	
	//constructor for Appointment class
	public Appointment(String name, String time, String day, boolean oil, String manufacturer, String model, int type) {
		id = ID_VAL + ++idNum;
		setDay(day);
		setName(name);
		setTime(time);
		setVehicle(manufacturer, model, type);
		cost += (oil) ? OIL_CHANGE_COST : 0;
		apptNums++;
		setType(type);
		oilChange = oil;
		
	}
	//getter for vehicle
	public Vehicle getVehicle() {
		Vehicle v = (vehicle instanceof Car) ? new Car(vehicle) : new Motorcycle(vehicle); //creates copy
		return v;

		
	}
	//setter for vehicle
	public void setVehicle(String manufacturer, String model, int type) {
		vehicle = (type == 1) ? new Car(manufacturer, model) : new Motorcycle(manufacturer, model);
	}

	//return - integer. Param - an appointment object. Compares two appointment objects
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
	
	//return - none, param - int for type. Sets type value
	public void setType(int type) {
		this.type = type;
	}
	
	//return - boolean, param - appointment object. Compares object id values
	public boolean equals(Appointment app) {
		return (id.equals(app.id)) ? true : false;
	}
	
	//return - integer. Breaks up time value into a storable integer
	public int timeComparator() {
		return Integer.parseInt(time.substring(0, time.indexOf(":")) + time.substring(time.indexOf(":") + 1));
	}
	
	//getter for the day of the Appointment
	public String getDay() {
		return day;
	}
	
	//setter for the day of the week of the appointment. Param - day, return - void
	public void setDay(String day) {
		for(String i : DAYS_OF_THE_WEEK) { //for each loop
			if (day.equals(i)) {
				this.day = day;
				return;
			}
		}
		throw new IllegalArgumentException("Invalid day value");	
	}
	
	//getter for id
	public String getID() {
		return id;
	}
	
	//setter for customer name. Param - name of the customer
	public void setCustomerName(String name) {
		customerName = name;
	}
	
	//getter for customer name, return - customer name
	public String getCustomerName() {
		return customerName;
	}
	
	//getter for the cost. Return - cost
	public double getCost() {
		return cost;
	}
	
	//param - cost. Sets the cost
	public void setCost(double cost) {
		this.cost += cost;
	}
	
	//setter for the id. param - id
	public void setID(String id) {
		this.id = id;
	}
	
	//setter for the name
	public void setName(String name) {
		if (name.equals("")) {
			throw new IllegalArgumentException("Name is required");
		}
		customerName = name;
	}
	
	//setter for the time
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
	
	//decrements the number of appointments
	public static void decrementAppts() {
		--apptNums;
	}
	
	//return - number of appts. Getter for appt number
	public static int getApptNums() {
		return apptNums;
	}
	
	//return - String. Returns a string respresentation of the appointment object
	public String toString() {
		return customerName + " | " + id + " | " + day + " @ " + time + " | Oil Change - " + oilChange + " | Vehicle: " + vehicle.toString() + " | Cost - $" + getCost();
	}
	
	//return - String. Returns a string respresentation of the object for file storage
	public String storeAsClosedAppt() {
		return customerName + "," + id + "," + day + "," + time + "," + cost + "," + oilChange + "," + vehicle.getManufacturer() + "," + vehicle.getModel() + "," + type;
	}
	
	
	
	
	
}
