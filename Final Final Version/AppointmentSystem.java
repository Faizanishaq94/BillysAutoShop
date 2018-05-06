/*
 * @authors
Faizan Ishaq Fishaq@masonlive.gmu.edu
Saad Durrani Sdurran2@masonlive.gmu.edu
Mauricio Zevallos Mzevallo@masonlive.gmu.edu 
 */
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
public class AppointmentSystem {
	
	private Map<String, PriorityQueue<Appointment>> appts; //map with key as days, values as priorityqueues for each day of the week
	private Map<String, Appointment> apptsByName; //map with key as customer names, values as Appointment objects
	private Map<String, Appointment> apptsByID; //map with key as customer names, values as Appointment objects
	
	//constructor
	public AppointmentSystem() {
		apptsByID = new HashMap<>(); //initalizes hashmap
		apptsByName = new HashMap<>(); //initalizes hashmap
		appts = new HashMap<String, PriorityQueue<Appointment>>(); //initalizes hashmap
		int count = 0;
		while(count < 5) { //initalizes a priorityqueue in the map with keys for each day of the week
			appts.put(Appointment.DAYS_OF_THE_WEEK[count], new PriorityQueue<Appointment>(5));
			count++;
		}
	}
	
	//param - Appointment object. Adds appt object to the hashmap
	public void addAppointment(Appointment app) {
		if(appts.get(app.getDay()).size() < 5) {
			appts.get(app.getDay()).add(app);
			apptsByID.put(app.getID(), app);
			apptsByName.put(app.getCustomerName(), app);
		}
		else {
			throw new IllegalArgumentException(app.getDay() + " Is maxed out with appointments!");
		}
	}
	
	//param - String for day. Return - num of appts for that day. Returns number of appointments for the provided day
	public int getValues(String day) {
		return appts.get(day).size();
	}
	
	//param - String for day. Return - num of appts for that day. Returns number of appointments for the provided day
	public int getApptsForTheDay(String day) {
		if(appts.get(day) != null) {
			return appts.get(day).size();
		}
		throw new IllegalArgumentException("Invalid day value given!");
	}
	
	/*
	 * param - day and time
	 * return - true or false
	 * sees if the appointment time for the provided day is taken, if yes
	 * returns false, otherwise returns true
	 */
	public boolean checkApptTimeSlot(String day, String time) {
		for(Appointment i : appts.get(day)) {
			if (i.getTime().equals(time))
				return false;
		}
		return true;
	}
	
	//return - boolean, param - criteria to search by and the type of criteria provided
	//searches through the map for the provided criteria and deletes the object if it exists
	public boolean deleteAppt(String searchCriteria, int code) { //criteria is either id or customer name, code determines which is provided
		Appointment a;
		if (code ==1)
			a = apptsByID.get(searchCriteria);
		else
			a = apptsByName.get(searchCriteria);
		if (a == null)
			return false;
		apptsByName.remove(a.getCustomerName());
		apptsByID.remove(a.getID());
		appts.remove(a.getDay());
		Appointment.decrementAppts();
		return true;
	}
	
	/*
	 * return - Appointment object. param - search criteria and type of criteria provided.
	 * searches through the map and if the criteria is found closes the object and adds it to
	 * the closed appointments file
	 */
	public Appointment closeAppt(String searchCriteria, int code) {
		Appointment a;
		if (code == 1)
			a = apptsByID.get(searchCriteria);
		else
			a = apptsByName.get(searchCriteria);
		if (a != null) {
			apptsByID.remove(a.getID());
			apptsByName.remove(a.getCustomerName());
			appts.remove(a.getDay());
			writeToClosedAppts(a);
			Appointment.decrementAppts();
		}
		return a;
	}
	
	/*
	 * @param - search criteria and type of criteria to search by. Return - toString of the object or error if not found
	 * Searches for the requested value, if found all the information is returned for the value for an error message
	 * if the specified value is not found
	 */
	public String searchAppt(String searchCriteria, int code) {
		Appointment a = (code == 1) ? apptsByID.get(searchCriteria) : apptsByName.get(searchCriteria);
		if (a != null) 
			return a.toString();
		return "No appointment found with the given criteria"; 
	}
	
	//writes all open appointments to the openappointments file
	public void writeToOpenAppts() {
		String path = "OpenAppointments.txt"; //file path
		try {
			FileWriter fw = new FileWriter(path); //creates filewriter
			fw.close(); //closes file writer so the file is overwritten and our file is now empty
			FileWriter fr = new FileWriter(path);
			for(String i : Appointment.DAYS_OF_THE_WEEK) { //looping through days of the week
				PriorityQueue<Appointment> pq = appts.get(i);
				if (pq != null) {
					for(Appointment x : pq) { 
						fr.write(x.storeAsClosedAppt() + "\n"); //adding object to file
					}
				}
			}
			fr.close();
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Could not store the appointment in memory. Error has occured");
		}
		catch(IOException d) {
			JOptionPane.showMessageDialog(null, "Could not store the appointment in memory. Error has occured");
		}
	}
	
	//param - an appointment object. Writing the passed object to the closed appointments file
	public void writeToClosedAppts(Appointment app) {
		String path = "ClosedAppointments.txt"; //path
		try {
			FileWriter fw = new FileWriter(path, true); //creates file writer
			fw.write(app.storeAsClosedAppt() + "\n"); //writes to file
			fw.close();
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Could not store the appointment in memory. Error has occured");
		}
		catch(IOException d) {
			JOptionPane.showMessageDialog(null, "Could not store the appointment in memory. Error has occured");
		}
	}
	
	//param - appointment types to sort. Sorts appointments based on specified method
	public void sortAppts(int apptType) {
		LinkedList<Appointment> apps  = (apptType == 1) ? getOpenAppts() : getClosedAppts(); //creates linked list of appts
		String[] options = {"By Cost","By Appointment Type","By Days of the Week"}; 
		
		//prompts for sort type
		String choice = (String)JOptionPane.showInputDialog(null, "Choose a sort method", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		if(choice == null)
			return; //if not sort type chosen
		if(apps.size() == 0) { //if no appointments
			String type = (apptType == 1) ? "Open Appointments" : "Closed Appointments";
			JOptionPane.showMessageDialog(null, "There Are Currently No " + type + ".");
			return;
		}
		
		switch(choice) { //calls certain method based on sort type chosen
		
			case "By Cost":
				sortByCost(apps);
				break;
			case "By Appointment Type":
				sortByType(apps);
				break;
			case "By Days of the Week":
				sortByDays(apptType, apps);
				break;
		}
	}
	
	/*
	 * returns statistics on closed appointments based on the type of statistic chosen
	 */
	public void getStatistics() {
		LinkedList<Appointment> apps = getClosedAppts();
		String[] options = {"Number of Closed Appointments","Vehicles Serviced by Type","Revenue for the Day","Total Revenue"};
		String choice = (String)JOptionPane.showInputDialog(null, "Choose the Required Information", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
		if (choice == null)
			return;
		switch(choice) { //calls certain method based on choice
		
		case "Number of Closed Appointments":
			JOptionPane.showMessageDialog(null, "Overall Closed Appointments - " + apps.size()); //number of closed appts
			break;
		case "Vehicles Serviced by Type":
			getType(apps);
			break;
		case "Revenue for the Day":
			revenueForTheDay(apps);
			break;
		case "Total Revenue":
			getTotalRev(apps);
		}
	}
	
	//param - linked list of appointments. Returns the total revenue of all appointments in the linked list
	private void getTotalRev(LinkedList<Appointment> apps) {
		String output = "Total Revenue for the Life of the Company - ";
		if(apps.size() == 0) {
			JOptionPane.showMessageDialog(null, output + "$0.00");
			return;
		}
		double revenue = 0;
		for(Appointment i : apps) { //loops thru and adds cost to each to the total
			if(i instanceof Maintenance) {
				revenue += i.getCost() - (((Maintenance)i).getParts() * 25);
			}
			else {
				revenue += i.getCost();
			}
		}
		output += revenue;
		JOptionPane.showMessageDialog(null, output);
	}
	
	//param - list of appointments. Provides revenue for the day based on the day they chose to view it for
	private void revenueForTheDay(LinkedList<Appointment> apps) {
		String[] options = {"MON","TUES","WED","THUR","FRI"};
		String choice = (String)JOptionPane.showInputDialog(null, "Revenue Total for the Day", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[4]);
		if(choice == null)
			return; //if choice is not provided
		String output = "Revenue Total for the Company on " + choice + "DAYS\n\n";
		if(apps.size() == 0) {
			JOptionPane.showMessageDialog(null, output + "$0.00");
			return;
		}
		
		double revenue = 0;
		for(Appointment i : apps) { //loops thru appts for that day and adds them to total cost
			if(i.getDay().equals(choice)) {
				if(i instanceof Maintenance) {
					revenue += i.getCost() - (((Maintenance)i).getParts() * 25);
				}
				else {
					revenue += i.getCost();
				}
			}
		}
		output += "$" + revenue;
		JOptionPane.showMessageDialog(null, output);
		
		
	}
	
	//param - list of appointments. Returns appointment information for all appointments with the type of vehicle chosen
	private void getType(LinkedList<Appointment> apps) {
		String[] options = {"Car", "Motorcycle"};
		String choice = (String)JOptionPane.showInputDialog(null, "Vehicle Type", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(choice == null)
			return; //if choice is not chosen
		String output = "";
		int count = 0;
		for(Appointment i : apps) { //loops through all values in list
			if(choice.equals(options[0]) && i.getVehicle() instanceof Car) {
				output += i.toString() + "\n";
				count++;
			}
			else if(choice.equals(options[1]) && i.getVehicle() instanceof Motorcycle) {
				output += i.toString() + "\n";
				count++;
			}
		}
		JOptionPane.showMessageDialog(null, "Total Number of " + choice + "'s Serviced - " + count + "\n\n" + output);
	}
	
	//return - list of appointments. Loops through all open appts and adds them to the list
	public LinkedList<Appointment> getOpenAppts() {
		LinkedList<Appointment> apps = new LinkedList<>();
		for(String i : Appointment.DAYS_OF_THE_WEEK) { //loops thru all keys in map
			PriorityQueue<Appointment> pq = appts.get(i);
			//for(Appointment x : pq) {
			apps.addAll(pq); //adding all appts in that priorityqueue to the list
			//}
		}
		return apps;
	}
	
	//return - a list of appointment objects
	public LinkedList<Appointment> getClosedAppts() {
		String path = "ClosedAppointments.txt"; //file path
		LinkedList<Appointment> apps = null;
		try {
			Scanner scan = new Scanner(new FileInputStream(new File(path))); //opening scanner
			apps = new LinkedList<>();
			while(scan.hasNextLine()) {
				String[] info = scan.nextLine().split(",");
				Appointment a = createAppt(info); //creating appointment object from each line in the file
				apps.add(a); //adding object to the list
			}
			scan.close();
			return apps;
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Could not input closed appts. Unfortately the program will stop.");
		}
		return apps;
	}
	
	/*
	 * param - an array with information populated about the object using input from a file. 
	 * Appointment objects are stored in a certain way when they are being written to a file
	 * for storage. Once we need to input them into the program for storage as objects at the
	 * beginning of the program, this method is called which has its own method of adding them
	 * back in properly.
	 */
	public Appointment createAppt(String[] info) {
		if (info[0].equals("Maintenance")) {
			int parts = Integer.parseInt(info[1]);
			double hours = Double.parseDouble(info[2]);
			String name = info[3];
			String id = info[4];
			String day = info[5];
			String time = info[6];
			double cost = Double.parseDouble(info[7]);
			boolean oil = (info[8].equalsIgnoreCase("true")) ? true : false;
			String manufacturer = info[9];
			String model = info[10];
			int type = Integer.parseInt(info[11]);
			Appointment a = new Maintenance(parts, type, hours, name, time, day, oil, manufacturer, model);
			a.setID(id);
			a.setCost(cost);
			return a;
		}
		
			String name = info[1];
			String id = info[2];
			String day = info[3];
			String time = info[4];
			double cost = Double.parseDouble(info[5]);
			boolean oil = (info[6].equalsIgnoreCase("true")) ? true : false;
			String manufacturer = info[7];
			String model = info[8];
			int type = Integer.parseInt(info[9]);
			Appointment a = new Inspection(type, name, time, day, oil, manufacturer, model);
			a.setID(id);
			a.setCost(cost);
			return a;
	}
	
	/*
	 * param - a list of appointment objects
	 * We are sorting each object by cost using mergesort
	 */
	private void sortByCost(LinkedList<Appointment> app) {
		Appointment[] appointment = new Appointment[app.size()]; //creating an array
		int count = 0;
		while(!app.isEmpty()) {
			appointment[count] = app.remove();
			count++;
		}
		mergesort(appointment,new Appointment[appointment.length], 0, appointment.length - 1); //calling mergesort
		String output = "";
		for(Appointment i : appointment) {
			output += i.toString() + "\n";
		}
		JOptionPane.showMessageDialog(null, output);
	}
	
	// param - array of appointments, a temporary array for sorting, and a leftstart and right end index
	public void mergesort(Appointment[] arr, Appointment[] temp, int leftStart, int rightEnd) {
		if(leftStart >= rightEnd) {
			return;
		}
		int middle = (leftStart + rightEnd) / 2;
		mergesort(arr, temp, leftStart, middle);
		mergesort(arr, temp, middle + 1, rightEnd);
		mergeHalves(arr, temp, leftStart, rightEnd);
	}
	// param - array of appointments, a temporary array for sorting, and a leftstart and right end index
	//merges two sorted halves of the array after sorting them
	public static void mergeHalves(Appointment[] arr, Appointment[] temp, int leftStart, int rightEnd) {
		int leftEnd = (rightEnd + leftStart) / 2;
		int rightStart = leftEnd + 1;
		int size = rightEnd - leftStart + 1;
		
		int left = leftStart;
		int right = rightStart;
		int index = leftStart;
		
		while(left <= leftEnd && right <= rightEnd) {
			if(arr[left].getCost() <= arr[right].getCost()) {
				temp[index] = arr[left];
				left++;
			}
			else {
				temp[index] = arr[right];
				right++;
			}
			index++;
		}
		
		System.arraycopy(arr, left, temp, index, leftEnd - left + 1);
		System.arraycopy(arr, right, temp, index, rightEnd - right + 1);
		System.arraycopy(temp, leftStart, arr, leftStart, size);
	}
	
	/*
	 * param - list of appointment objects
	 * sorts the objects for type. It will separate maintenance and inspection appointments
	 */
	private void sortByType(LinkedList<Appointment> apps) {
		String maintenance = "Maintenance Appointments\n\n";
		String inspection = "Inspection Appointments\n\n";
		
		for(Appointment i : apps) {
			if(i instanceof Maintenance) {
				maintenance += ((Maintenance)(i)).toString() + "\n";
			}
			else {
				inspection += ((Inspection)(i)).toString() + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, maintenance + "\n\n" + inspection);
	}
	
	/*
	 * param - appointment type we are working with and a list of appointments
	 * Sorts appointments into their respective days, then outputs appointment info
	 * to the user in order from monday to friday
	 */
	private void sortByDays(int apptType, LinkedList<Appointment> apps) {
		String mon = "MONDAY\n\n", tues = "TUESDAY\n\n", wed = "WEDNESDAY\n\n", thur = "THURSDAY\n\n", fri = "FRIDAY\n\n";
		if(apptType == 1) {
			while(!apps.isEmpty()) {
				//output += apps.remove().toString() + "\n";
				Appointment a = apps.remove();
				String out = a.toString() + "\n";
				switch(a.getDay()) {
				case "MON":
					mon += out;
					break;
				case "TUES":
					tues += out;
					break;
				case "WED":
					wed += out;
					break;
				case "THUR":
					thur += out;
					break;
				case "FRI":
					fri += out;
					break;
				}
			}
			JOptionPane.showMessageDialog(null, mon + "\n" + tues + "\n" + wed + "\n" + thur + "\n" + fri);
			return;
		}
		Map<String, ArrayList<Appointment>> appointments = new HashMap<>();
		
		for(String i : Appointment.DAYS_OF_THE_WEEK) {
			appointments.put(i, new ArrayList<Appointment>());
		}
		
		Iterator<Appointment> it = apps.iterator();
		while(it.hasNext()) {
			Appointment a = (Appointment) it.next();
			appointments.get(a.getDay()).add(a);
		}
		
		for(String i : Appointment.DAYS_OF_THE_WEEK) {
			ArrayList<Appointment> appz = appointments.get(i);
			for(Appointment x : appz) {
				String out = x.toString() + "\n";
				switch(x.getDay()) {
				case "MON":
					mon += out;
					break;
				case "TUES":
					tues += out;
					break;
				case "WED":
					wed += out;
					break;
				case "THUR":
					thur += out;
					break;
				case "FRI":
					fri += out;
					break;
				}
			}
		}
		JOptionPane.showMessageDialog(null, mon + "\n" + tues + "\n" + wed + "\n" + thur + "\n" + fri);
	}

}
