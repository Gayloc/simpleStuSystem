public class Address {
    private final String street;
    private final String city;
    private final String state;
    private final String zip;

    public Address(String state, String city, String street, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Address() {
        this.street = "";
        this.city = "";
        this.state = "";
        this.zip = "";
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    @Override
    public String toString() {
        if(street.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty()) {
            return "";
        }
        return street + " " + city + " " + state + " " + zip;
    }
}
