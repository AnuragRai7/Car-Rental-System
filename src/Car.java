public class Car {
    // These variables match the columns in your MySQL table
    private int id;
    private String brand;
    private String model;
    private double pricePerDay;
    private boolean isAvailable;

    // Constructor: Used to create a new Car object
    public Car(int id, String brand, String model, double pricePerDay, boolean isAvailable) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.isAvailable = isAvailable;
    }

    // Getters: Allow other files to read these private variables
    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return isAvailable; }
}