import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
public class AppointmentSystem {
	
	private Map<String, PriorityQueue<Appointment>> appts;
	private static final int MAX_APPTS = 100;
	private static final int MAX_DAILY = 5;
	
	public AppointmentSystem() {
		appts = new HashMap<String, PriorityQueue<Appointment>>();
		int count = 0;
		while(count < 5) {
			appts.put(Appointment.DAYS_OF_THE_WEEK[count], new PriorityQueue<Appointment>(5));
		}
	}
	
	public void addAppointment(Appointment app) {
		if(appts.get(app.getDay()).size() < 5) {
			appts.get(app.getDay()).add(app);
		}
		else {
			throw new IllegalArgumentException(app.getDay() + " Is maxed out with appointments!");
		}
	}
	
	public int getValues(String day) {
		return appts.get(day).size();
	}
	
	public int getApptsForTheDay(String day) {
		if(appts.get(day) != null) {
			return appts.get(day).size();
		}
		throw new IllegalArgumentException("Invalid day value given!");
	}
	
	public boolean deleteAppt(String searchCriteria, int code) { //criteria is either id or customer name, code determines which is provided
		for(String i : Appointment.DAYS_OF_THE_WEEK) {
			PriorityQueue<Appointment> pq = appts.get(i);
			for(Appointment x : pq) {
				boolean found = (code == 1) ? searchCriteria.equals(x.getID()) : searchCriteria.equals(x.getCustomerName());
				if(found) {
					pq.remove(x);
					Appointment.decrementAppts();
					return true;
				}
			}
		}
		return false;
	}
	
	public Appointment closeAppt(String searchCriteria, int code) {
		for(String i : Appointment.DAYS_OF_THE_WEEK) {
			PriorityQueue<Appointment> pq = appts.get(i);
			for(Appointment x : pq) {
				boolean found = (code == 1) ? searchCriteria.equals(x.getID()) : searchCriteria.equals(x.getCustomerName());
				if(found) {
					pq.remove(x);
					writeToClosedAppts(x);
					Appointment.decrementAppts();
					return x;
				}
			}
		}
		return null;
	}
	
	public String searchAppt(String searchCriteria, int code) {
		for(String i : Appointment.DAYS_OF_THE_WEEK) {
			PriorityQueue<Appointment> pq = appts.get(i);
			for(Appointment x : pq) {
				boolean found = (code == 1) ? searchCriteria.equals(x.getID()) : searchCriteria.equals(x.getCustomerName());
				if(found) {
					return x.toString();
				}
			}
		}
		return "No appointment found with the given criteria";
	}
	
	public void writeToOpenAppts() {
		String path = "OpenAppointments.txt";
		try {
			FileWriter fw = new FileWriter(path);
			fw.close();
			FileWriter fr = new FileWriter(path);
			for(String i : Appointment.DAYS_OF_THE_WEEK) {
				PriorityQueue<Appointment> pq = appts.get(i);
				for(Appointment x : pq) {
					fr.write(x.storeAsClosedAppt() + "\n");
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
	
	public void writeToClosedAppts(Appointment app) {
		String path = "ClosedAppointments.txt";
		try {
			FileWriter fw = new FileWriter(path, true);
			fw.write(app.storeAsClosedAppt() + "\n");
			fw.close();
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Could not store the appointment in memory. Error has occured");
		}
		catch(IOException d) {
			JOptionPane.showMessageDialog(null, "Could not store the appointment in memory. Error has occured");
		}
	}
	
	public void sortAppts(int apptType) {
		LinkedList<Appointment> apps  = (apptType == 1) ? getOpenAppts() : getClosedAppts();
		String[] options = {"By Cost","By Appointment Type","By Days of the Week"};
		String choice = (String)JOptionPane.showInputDialog(null, "Choose a sort method", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		
		switch(choice) {
		
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
	
	public void getStatistics() {
		LinkedList<Appointment> apps = getClosedAppts();
		String[] options = {"Number of Closed Appointments","Vehicles Serviced by Type","Revenue for the Day","Total Revenue"};
		String choice = (String)JOptionPane.showInputDialog(null, "Choose the Required Information", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
		
		switch(choice) {
		
		case "Number of Closed Appointments":
			JOptionPane.showMessageDialog(null, "Overall Closed Appointments - " + apps.size());
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
	
	private void getTotalRev(LinkedList<Appointment> apps) {
		String output = "Total Revenue for the Life of the Company - ";
		double revenue = 0;
		for(Appointment i : apps) {
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
	
	private void revenueForTheDay(LinkedList<Appointment> apps) {
		String[] options = {"MON","TUES","WED","THUR","FRI"};
		String choice = (String)JOptionPane.showInputDialog(null, "Revenue Total for the Day", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[4]);
		String output = "Revenue Total for the Company on " + choice + "DAYS\n\n";
		double revenue = 0;
		for(Appointment i : apps) {
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
	
	private void getType(LinkedList<Appointment> apps) {
		String[] options = {"Car", "Motorcycle"};
		String choice = (String)JOptionPane.showInputDialog(null, "Vehicle Type", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		String output = "";
		int count = 0;
		for(Appointment i : apps) {
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
	
	public LinkedList<Appointment> getOpenAppts() {
		LinkedList<Appointment> apps = new LinkedList<>();
		for(String i : Appointment.DAYS_OF_THE_WEEK) {
			PriorityQueue<Appointment> pq = appts.get(i);
			//for(Appointment x : pq) {
			apps.addAll(pq);
			//}
		}
		return apps;
	}
	
	public LinkedList<Appointment> getClosedAppts() {
		String path = "ClosedAppointments.txt";
		LinkedList<Appointment> apps = null;
		try {
			Scanner scan = new Scanner(new FileInputStream(new File(path)));
			apps = new LinkedList<>();
			while(scan.hasNextLine()) {
				String[] info = scan.nextLine().split(",");
				Appointment a = createAppt(info);
				apps.add(a);	
			}
			scan.close();
			return apps;
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Could not input closed appts. Unfortately the program will stop.");
		}
		catch(IOException d) {
			JOptionPane.showMessageDialog(null, "Could not input closed appts. Unfortately the program will stop.");
		}
		return apps;
	}
	
	
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
	
	private void sortByCost(LinkedList<Appointment> app) {
		Appointment[] appointment = (Appointment[])app.toArray();
		mergesort(appointment,new Appointment[appointment.length], 0, appointment.length - 1);
		String output = "";
		for(Appointment i : appointment) {
			output += i.toString() + "\n";
		}
		JOptionPane.showMessageDialog(null, output);
	}
	
	public void mergesort(Appointment[] arr, Appointment[] temp, int leftStart, int rightEnd) {
		if(leftStart >= rightEnd) {
			return;
		}
		int middle = (leftStart + rightEnd) / 2;
		mergesort(arr, temp, leftStart, middle);
		mergesort(arr, temp, middle + 1, rightEnd);
		mergeHalves(arr, temp, leftStart, rightEnd);
	}
	
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
	
	private void sortByDays(int apptType, LinkedList<Appointment> apps) {
		String output = "";
		if(apptType == 1) {
			while(!apps.isEmpty()) {
				output += apps.remove().toString() + "\n";
			}
			JOptionPane.showMessageDialog(null, output);
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
				output += x.toString() + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, output);
	}

}
