/*
 * @authors
Faizan Ishaq Fishaq@masonlive.gmu.edu
Saad Durrani Sdurran2@masonlive.gmu.edu
Mauricio Zevallos Mzevallo@masonlive.gmu.edu 
 */
public abstract class Vehicle { //abstract super class

	private String manufacturer;
	private String model;
	
	//copy constructor
	public Vehicle(Vehicle vehicle) {
		setManufacturer(vehicle.manufacturer);
		setModel(vehicle.model);
	}
	
	//constructor that takes manufacturer and model as parameters
	public Vehicle(String manufacturer, String model) {
		setManufacturer(manufacturer);
		setModel(model);
	}
	
	//return - void. param - manufacturer name. Setter for manufacturer
	public void setManufacturer(String manu) {
		manufacturer = manu;
	}
	
	//param - none. return - String value. Getter for manufacturer
	public String getManufacturer() {
		return manufacturer;
	}
	
	//return - void. param - model name. Setter for model
	public void setModel(String mod) {
		model = mod;
	}
	
	//return - string. param - none. Getter for model
	public String getModel() {
		return model;
	}
	
	//returns string representation of object
	public String toString() {
		return manufacturer + " " + model;
	}
}
