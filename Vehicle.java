
public abstract class Vehicle {

	private String manufacturer;
	private String model;
	
	public Vehicle(Vehicle vehicle) {
		setManufacturer(vehicle.manufacturer);
		setModel(vehicle.model);
	}
	
	public Vehicle(String manufacturer, String model) {
		setManufacturer(manufacturer);
		setModel(model);
	}

	public void setManufacturer(String manu) {
		manufacturer = manu;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public void setModel(String mod) {
		model = mod;
	}
	
	public String getModel() {
		return model;
	}
	
}
