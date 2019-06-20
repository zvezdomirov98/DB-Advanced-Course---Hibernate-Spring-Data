package p03_Ferrari;

public class Ferrari implements Car{
    private String model;
    private String brand;
    private String driverName;

    public Ferrari(String driverName){
        this.model = "488-Spider";
        this.brand = "p03_Ferrari.Ferrari";
        this.driverName = driverName;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public String pushBrakePedal() {
        return "Brakes!";
    }

    @Override
    public String pushGasPedal() {
        return "Zadu6avam sA!";
    }
}
