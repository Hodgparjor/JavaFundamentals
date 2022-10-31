

public class task6 {
    public static void main(String[] arg) {
        System.out.println("Elo");
    }
}

class TelephoneNumber implements Comparable<TelephoneNumber> {
    private String countryCode;
    private String localNumber;

    public TelephoneNumber(String countryCode, String localNumber) {
        this.countryCode = countryCode;
        this.localNumber = localNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    @Override
    public int compareTo(TelephoneNumber comparedTelNum) {
        if(this.countryCode.compareTo(comparedTelNum.countryCode) == 0) {
            return (this.localNumber.compareTo(comparedTelNum.localNumber));
        }
        return this.countryCode.compareTo(comparedTelNum.countryCode);
    }
}

abstract class TelephoneEntry {
    private String name;
    private String city;
    private String street;
    private String streetNumber;
    private String postalCode;
    private TelephoneNumber telephoneNumber;
    public abstract void description();

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public TelephoneNumber getTelephoneNumber() {
        return telephoneNumber;
    }
}

// Class Person has to contain information about the name, last name, and address (including TelephoneNumber)
class Person extends TelephoneEntry {
    private String  lastName;

    @Override
    public void description() {
        System.out.println("------------------------------\n"
                         + "           PERSON\n"
                         + "Name: " + this.getName() + "\n"
                         + "Last name: " + lastName + "\n"
                         + "Addres:\n"
                         + "  City: " + this.getCity() + "\n"
                         + "  Street: " + this.getStreet() + this.getStreetNumber() + "\n"
                         + "  Postal code: " + this.getPostalCode() + "\n"
                         + "  Telephone Number: +" + this.getTelephoneNumber().getCountryCode() + " " + this.getTelephoneNumber().getLocalNumber()
                         + "\n------------------------------");
    }

    public String getLastName() {
        return lastName;
    }
}
// Class Company has to have the name and address (including TelephoneNumber)
class Company extends TelephoneEntry {
    @Override
    public void description() {
        System.out.println("------------------------------\n"
                         + "           COMPANY\n"
                         + "Name: " + this.getName() + "\n"
                         + "Addres:\n"
                         + "  City: " + this.getCity() + "\n"
                         + "  Street: " + this.getStreet() + this.getStreetNumber() + "\n"
                         + "  Postal code: " + this.getPostalCode() + "\n"
                         + "  Telephone Number: +" + this.getTelephoneNumber().getCountryCode() + " " + this.getTelephoneNumber().getLocalNumber()
                         + "\n------------------------------");
        
    }
}