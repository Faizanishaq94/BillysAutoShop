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
			writeToOpenAppts(app);
		}
		else {
			throw new IllegalArgumentException(app.getDay() + " Is maxed out with appointments!");
		}
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
	
	public void writeToOpenAppts(Appointment app) {
		
	}
	
	public void writeToClosedAppts(Appointment app) {
		
	}
	
	public void sortOpenedAppts() {
		String[] options = {"By Cost","By Appointment Type","By Days of the Week"};
		String choice = (String)JOptionPane.showInputDialog(null, "Choose a sort method", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		
		switch(choice) {
		
			case "By Cost":
				sortByCost();
				break;
			case "By Appointment Type":
				sortByType();
				break;
			case "By Days of the Week":
				sortByDays();
				break;
		}
	}
	
	public String sortByCost() {
		
	}
	
	public String sortByType() {
		
	}
	
	public String sortByDays() {
		
	}

}
