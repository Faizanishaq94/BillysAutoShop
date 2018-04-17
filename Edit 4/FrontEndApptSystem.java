import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class FrontEndApptSystem {

	public static void main(String[] args) {
		AppointmentSystem appSystem = new AppointmentSystem();
		importAppointments(appSystem);
		JOptionPane.showMessageDialog(null, "hello");
		menuOptions(appSystem);

	}
	
	public static void importAppointments(AppointmentSystem appSystem) {
		try {
			Scanner scan = new Scanner(new File("OpenAppointments.txt"));	
			while(scan.hasNextLine()) {
				appSystem.addAppointment(appSystem.createAppt(scan.nextLine().split(",")));
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File with open appointment information not found");
		}
		catch(IOException d) {
			JOptionPane.showMessageDialog(null, "Could not read from the file");
		}
		catch(IllegalArgumentException m) {
			JOptionPane.showMessageDialog(null, m.getMessage());
		}
	}
	
	public static void menuOptions(AppointmentSystem app) {
		
		while(true) {
			String[] options = {"Add an Appointment", "Close an Appointment", "Erase an Appointment", "View Open Appointments", "View Closed Appointments", "EXIT"};
			String choice = (String)JOptionPane.showInputDialog(null, "Options", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[5]);
			switch(choice) {
			
			case "Add an Appointment":
				addAppt(app);
				break;
			case "Close an Appointment":
				closeAppt(app);
				break;
			case "Erase an Appointment":
				eraseAppt(app);
				break;
			case "View Open Appointments":
				viewOpen(app);
				break;
			case "View Closed Appointments":
				viewClosed(app); 
				break;
			case "EXIT":
				exit(app);
				return;
			}
		}

	}
	
	public static void exit(AppointmentSystem app) {
		app.writeToOpenAppts();
	}
	
	public static void viewOpen(AppointmentSystem app) {
		app.sortAppts(1);
	}
	
	public static void viewClosed(AppointmentSystem app) {
		String[] options = {"Sort CLosed Appointments", "View Closed Appointment Statistics"};
		String choice = (String)JOptionPane.showInputDialog(null, "What Would You Like to do?", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(choice.equals(options[0])) {
			app.sortAppts(2);
		}
		else {
			app.getStatistics();
		}
	}
	
	public static void addAppt(AppointmentSystem app) {
		String[] options = {"Maintenance", "Inspection"};
		String choice = (String)JOptionPane.showInputDialog(null, "Choose an Appointment Type", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		String day;
		boolean valid;
		do {
			day = (String)JOptionPane.showInputDialog(null, "Choose Appointment Day", "Pick One", JOptionPane.QUESTION_MESSAGE, null, Appointment.DAYS_OF_THE_WEEK, Appointment.DAYS_OF_THE_WEEK[4]);
			valid = (app.getValues(day) < 5) ? true : false;
			if(!valid) {
				JOptionPane.showMessageDialog(null, "That day is filled. Pick another day or return to menu");
				String[] prompt = {"Choose Another Day", "Return to Menu"};
				String toDo = (String)JOptionPane.showInputDialog(null, "DAY CHOSEN IS FULL", "Pick One", JOptionPane.QUESTION_MESSAGE, null, prompt, prompt[1]);
				if(toDo.equals(prompt[1])) {
					return;
				}
			}
			
		} while(!valid);
		
		String[] common = new String[7];
		common[3] = day;
		getCommonValues(common);
		boolean creation = (choice == options[0]) ? createMaintenance(app, common) : createInspection(app, common);
		
		if(creation) {
			JOptionPane.showMessageDialog(null, "Customer added successfully");
		}
		else {
			JOptionPane.showMessageDialog(null, "Customer could not be added. You will be returned to the menu");
		}
	}
	
	public static boolean createInspection(AppointmentSystem app, String[] val) {
		try {
			int type = Integer.parseInt(val[0]);
			String name = val[1], time = val[2], day = val[3], manufacturer = val[5], model = val[6];
			boolean oil = (val[4].equals("0")) ? true : false;
			Appointment a = new Inspection(type, name, time, day, oil, manufacturer, model);
			app.addAppointment(a);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean createMaintenance(AppointmentSystem app, String[] val) {
		int parts = 0;
		double hours = 0;
		boolean valid;
		
		do {
			valid= true;
			try {
				parts = Integer.parseInt(JOptionPane.showInputDialog("How many parts are required for the maintenance?"));
			}
			catch(NumberFormatException a) {
				JOptionPane.showMessageDialog(null, "Enter a numerical value for parts. Try again");
				valid = false;
			}
		} while(!valid);
		
		do {
			valid= true;
			try {
				hours = Double.parseDouble(JOptionPane.showInputDialog("How many hours with the appointment take?"));
			}
			catch(NumberFormatException a) {
				JOptionPane.showMessageDialog(null, "Enter a numerical value for hours. Try again");
			}
		} while(!valid);
			
		try {
			int type = Integer.parseInt(val[0]);
			String name = val[1], time = val[2], day = val[3], manufacturer = val[5], model = val[6];
			boolean oil = (val[4].equals("0")) ? true : false;
			Appointment a = new Maintenance(parts, type, hours, name, time, day, oil, manufacturer, model);
			app.addAppointment(a);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static void getCommonValues(String[] common) {
		String name, time, manufacturer, model;
		
		do {
			name = JOptionPane.showInputDialog("Enter the customers name");
			if(name.equals("")) {
				JOptionPane.showMessageDialog(null, "Name cannot be empty!");
			}
		} while(name.equals(""));
		
		common[1] = name;
		time = (String)JOptionPane.showInputDialog(null, "Pick an Appointment Time", "Pick One", JOptionPane.QUESTION_MESSAGE, null, Appointment.TIMES, Appointment.TIMES[20]);
		common[2] = time;
		
		String[] types = {"Car", "Motorcycle"};
		String type = (String)JOptionPane.showInputDialog(null, "Choose Customers Vehicle Type", "Pick One", JOptionPane.QUESTION_MESSAGE, null, types, types[1]);
		common[0] = (type.equals(types[0])) ? "1" : "2";
		common[4] = Integer.toString((JOptionPane.showConfirmDialog(null,"Add an Oil Change to the Appointment?","Oil Change",JOptionPane.YES_NO_OPTION)));
		
		do {
			manufacturer = JOptionPane.showInputDialog("Enter the Vehicles Manufacturer");
			if(manufacturer.equals("")) {
				JOptionPane.showMessageDialog(null, "This cannot be empty!");
			}
		} while(manufacturer.equals(""));
		
		do {
			model = JOptionPane.showInputDialog("Enter the Vehicles model");
			if(model.equals("")) {
				JOptionPane.showMessageDialog(null, "This cannot be empty!");
			}
		} while(model.equals(""));
		
		common[5] = manufacturer;
		common[6] = model;
	}
	
	public static void closeAppt(AppointmentSystem app) {
		boolean valid = true;
		String[] options = {"Customer ID", "Customer Name"};
		String choice = (String)JOptionPane.showInputDialog(null, "Pick a Value to Locate Appointment With", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		int searchBy = (choice.equals(options[0])) ? 1 : 2;
		String searchCriteria = "";
		
		if(searchBy == 1) {
			do {
				valid = true;
				searchCriteria = JOptionPane.showInputDialog("Enter a customer ID to search by");
				
				if(searchCriteria.length() < 10 || !searchCriteria.substring(0, 9).equals(Appointment.ID_VAL)) {
					valid = false;
					JOptionPane.showMessageDialog(null, "Invalid ID format, try again");
				}
				
				if(valid) {
					try {
						Integer.parseInt(searchCriteria.substring(9));
					}
					catch(NumberFormatException e) {
						valid = false;
						JOptionPane.showMessageDialog(null, "Invalid ID format, try again");
					}
				}
			} while(!valid);
		}
		else {
			do {
				searchCriteria = JOptionPane.showInputDialog("Enter a customer name to search by");
				if(searchCriteria.equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid Customer name, try again");
				}
			} while(searchCriteria.equals(""));
			
		}
		Appointment a = app.closeAppt(searchCriteria, searchBy);
		
		if(a == null) {
			JOptionPane.showMessageDialog(null, "No appointment was located with the provided criteria");
		}
		else {
			JOptionPane.showMessageDialog(null, "Appointment closed successfully");
		}
	}
	
	public static void eraseAppt(AppointmentSystem app) {
		boolean valid = true;
		String[] options = {"Customer ID", "Customer Name"};
		String choice = (String)JOptionPane.showInputDialog(null, "Pick a Value to Locate Appointment With", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		int searchBy = (choice.equals(options[0])) ? 1 : 2;
		String searchCriteria = "";
		
		if(searchBy == 1) {
			do {
				valid = true;
				searchCriteria = JOptionPane.showInputDialog("Enter a customer ID to search by");
				
				if(searchCriteria.length() < 10 || !searchCriteria.substring(0, 9).equals(Appointment.ID_VAL)) {
					valid = false;
					JOptionPane.showMessageDialog(null, "Invalid ID format, try again");
				}
				
				if(valid) {
					try {
						Integer.parseInt(searchCriteria.substring(9));
					}
					catch(NumberFormatException e) {
						valid = false;
						JOptionPane.showMessageDialog(null, "Invalid ID format, try again");
					}
				}
			} while(!valid);
		}
		else {
			do {
				searchCriteria = JOptionPane.showInputDialog("Enter a customer name to search by");
				if(searchCriteria.equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid Customer name, try again");
				}
			} while(searchCriteria.equals(""));
			
		}
		boolean a = app.deleteAppt(searchCriteria, searchBy);
		
		if(!a) {
			JOptionPane.showMessageDialog(null, "No appointment was located with the provided criteria");
		}
		else {
			JOptionPane.showMessageDialog(null, "Appointment deleted successfully");
		}
	}
	


}
