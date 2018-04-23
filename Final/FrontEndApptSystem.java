/*
 * @authors
Faizan Ishaq Fishaq@masonlive.gmu.edu
Saad Durrani Sdurran2@masonlive.gmu.edu
Mauricio Zevallos Mzevallo@masonlive.gmu.edu 
 */
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class FrontEndApptSystem {

	public static void main(String[] args) {
		AppointmentSystem appSystem = new AppointmentSystem(); //creating appointment system object
		importAppointments(appSystem); //imports appts from file
		menuOptions(appSystem); //prompts for menu options

	}
	
	/*
	 * param - appointmentsystem object
	 * imports all appointments in the open appointments file and reads
	 * them into memory as appointment objects. 
	 */
	public static void importAppointments(AppointmentSystem appSystem) {
		try {
			Scanner scan = new Scanner(new File("OpenAppointments.txt")); //creates scanner
			while(scan.hasNextLine()) {
				appSystem.addAppointment(appSystem.createAppt(scan.nextLine().split(","))); //scans in line by line
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File with open appointment information not found");
		}
		catch(IOException d) {
			JOptionPane.showMessageDialog(null, "Could not read from the file");
		}
		catch(IllegalArgumentException m) { //in case object creation fails
			JOptionPane.showMessageDialog(null, m.getMessage());
		}
	}
	
	/*
	 * param - an appointmentsystem object
	 * Will prompt the user for the option they would like to perform then calls
	 * the respective method to perform the action. This continutes until the user
	 * decides to exit
	 */
	public static void menuOptions(AppointmentSystem app) {
		
		while(true) { //continues until exit condition
			String[] options = {"Add an Appointment", "Close an Appointment", "Erase an Appointment", "View Open Appointments", "View Closed Appointments", "EXIT"};
			String choice = (String)JOptionPane.showInputDialog(null, "Options", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[5]);
			if(choice == null) {
				exit(app); //if choice is null
				return;
			}
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
	
	//param - appointmentsystem object. Writes open appointments to file
	public static void exit(AppointmentSystem app) {
		app.writeToOpenAppts();
	}
	
	//param - appointmentsystem object. Calls the method to sort appointments
	public static void viewOpen(AppointmentSystem app) {
		app.sortAppts(1);
	}
	
	//param - appointmentsystem object. Prompts user for what they would like to do with closed appointments then calls the respective method
	public static void viewClosed(AppointmentSystem app) {
		String[] options = {"Sort Closed Appointments", "View Closed Appointment Statistics"};
		String choice = (String)JOptionPane.showInputDialog(null, "What Would You Like to do?", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(choice == null) 
			return; // if choice is null
		if(choice.equals(options[0])) {
			app.sortAppts(2); //if sort option is chosen
		}
		else {
			app.getStatistics(); //if statistics option is chosen
		}
	}
	
	/*
	 * param - appointmentsystem object
	 * prompts the user for appointment type and based off of their choice
	 * prompts to required info and creates the object
	 */
	public static void addAppt(AppointmentSystem app) {
		String[] options = {"Maintenance", "Inspection"};
		String choice = (String)JOptionPane.showInputDialog(null, "Choose an Appointment Type", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(choice == null)
			return; //if choice is null
		String day;
		boolean valid;
		do {
			day = (String)JOptionPane.showInputDialog(null, "Choose Appointment Day", "Pick One", JOptionPane.QUESTION_MESSAGE, null, Appointment.DAYS_OF_THE_WEEK, Appointment.DAYS_OF_THE_WEEK[4]);
			if(day == null)
				return; //if day is null
			valid = (app.getValues(day) < 5) ? true : false;
			if(!valid) {
				JOptionPane.showMessageDialog(null, "That day is filled. Pick another day or return to menu");
				String[] prompt = {"Choose Another Day", "Return to Menu"};
				String toDo = (String)JOptionPane.showInputDialog(null, "DAY CHOSEN IS FULL", "Pick One", JOptionPane.QUESTION_MESSAGE, null, prompt, prompt[1]);
				if(toDo.equals(prompt[1]) || toDo == null) {
					return;
				}
			}
			
		} while(!valid);
		
		String[] common = new String[7];
		common[3] = day;
		if(!getCommonValues(common)) 
			return;
		boolean creation = (choice == options[0]) ? createMaintenance(app, common) : createInspection(app, common);
		
		if(creation) {
			JOptionPane.showMessageDialog(null, "Customer added successfully"); //if customer added successfully
		}
		else {
			JOptionPane.showMessageDialog(null, "Customer could not be added. You will be returned to the menu"); //if not added successfully
		}
	}
	
	/*
	 * params - appointmentsystem object and a String array to store info for the object before its creation
	 * return - boolean
	 * prompts the user for the required information for object creation and then creates it, returns
	 * true if successful otherwise returns false
	 */
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
	
	/*
	 * params - appointmentsystem object and a String array to store info for the object before its creation
	 * return - boolean
	 * prompts the user for the required information for object creation and then creates it, returns
	 * true if successful otherwise returns false
	 */
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
	
	/*
	 * return - true or false based on if information is properly entered
	 * param - a String array to store values
	 * Prompts the user to enter information about the appointment that is
	 * common across all appointment types.
	 */
	public static boolean getCommonValues(String[] common) {
		String name, time, manufacturer, model, type;
		
		do {
			name = JOptionPane.showInputDialog("Enter the customers name");
			if(name.equals("")) {
				JOptionPane.showMessageDialog(null, "Name cannot be empty!");
			}
		} while(name.equals(""));
		
		common[1] = name;
		time = (String)JOptionPane.showInputDialog(null, "Pick an Appointment Time", "Pick One", JOptionPane.QUESTION_MESSAGE, null, Appointment.TIMES, Appointment.TIMES[20]);
		if(time == null)
			return false; //if time is null
		common[2] = time;
		
		String[] types = {"Car", "Motorcycle"};
		type = (String)JOptionPane.showInputDialog(null, "Choose Customers Vehicle Type", "Pick One", JOptionPane.QUESTION_MESSAGE, null, types, types[1]);
		if(type == null)
			return false; //if type is null
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
		return true;
	}
	
	/*
	 * param - an appointmentsystem object
	 * asks the user how they would like to search for an appointment to close, then
	 * calls the respective method to close the appointment. If the close is sucessful 
	 * the user is told, otherwise they are told that the appt was not found
	 */
	public static void closeAppt(AppointmentSystem app) {
		boolean valid = true;
		String[] options = {"Customer ID", "Customer Name"};
		String choice = (String)JOptionPane.showInputDialog(null, "Pick a Value to Locate Appointment With", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(choice == null)
			return; //if choice is null
		int searchBy = (choice.equals(options[0])) ? 1 : 2;
		String searchCriteria = "";
		
		if(searchBy == 1) {
			do { //validates that the search criteria given is valid
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
			do { //validates that the search criteria given is valid
				searchCriteria = JOptionPane.showInputDialog("Enter a customer name to search by");
				if(searchCriteria.equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid Customer name, try again");
				}
			} while(searchCriteria.equals(""));
			
		}
		Appointment a = app.closeAppt(searchCriteria, searchBy);
		
		if(a == null) { //if appointment with criteria given is not found
			JOptionPane.showMessageDialog(null, "No appointment was located with the provided criteria");
		}
		else { //if closing was successful
			JOptionPane.showMessageDialog(null, "Appointment closed successfully");
		}
	}
	
	/*
	 * param - an appointmentsystem object
	 * asks the user how they would like to search for an appointment to erase, then
	 * calls the respective method to erase the appointment. If the erase is successful 
	 * the user is told, otherwise they are told that the appt was not found
	 */
	public static void eraseAppt(AppointmentSystem app) {
		boolean valid = true;
		String[] options = {"Customer ID", "Customer Name"};
		String choice = (String)JOptionPane.showInputDialog(null, "Pick a Value to Locate Appointment With", "Pick One", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(choice == null)
			return; //if choice is null
		int searchBy = (choice.equals(options[0])) ? 1 : 2;
		String searchCriteria = "";
		
		if(searchBy == 1) {
			do { //validates that the search criteria given is valid
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
			do { //validates that the search criteria given is valid
				searchCriteria = JOptionPane.showInputDialog("Enter a customer name to search by");
				if(searchCriteria.equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid Customer name, try again");
				}
			} while(searchCriteria.equals(""));
			
		}
		boolean a = app.deleteAppt(searchCriteria, searchBy);
		
		if(!a) { //if appointment with criteria given is not found
			JOptionPane.showMessageDialog(null, "No appointment was located with the provided criteria");
		}
		else { //is deletion was successful
			JOptionPane.showMessageDialog(null, "Appointment deleted successfully");
		}
	}
	


}
