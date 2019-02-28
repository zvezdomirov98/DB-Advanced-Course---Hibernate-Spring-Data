package p04_Telephony;

public class Smartphone implements CallingDevice, BrowsingDevice{
    private String model;

    public Smartphone(String model) {
        this.model = model;
    }


    @Override
    public String browse(String url) {
        String response = "";
        if(url.matches(".*[0-9]+.*")) {
            response = "Invalid URL!";
        } else {
            response = "Browsing: " + url + "!";
        }
        return response;
    }

    @Override
    public String call(String phoneNumber) {
        String response = "";
        if (phoneNumber.matches("[^\\d]+")) {
            response = "Invalid number!";
        } else {
            response = "Calling... " + phoneNumber;
        }
        return response;
    }
}
