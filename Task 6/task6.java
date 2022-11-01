import java.util.*;

public class task6 {
    public static void main(String[] arg) {
        Person p1 = new Person("Marek", "Sczesny", "Kijanki Male", "Klasztorna",
                       "29/31", "12-345", new TelephoneNumber("48", "501506353"));
        Person p2 = new Person("Katarzyna", "Malinowska", "Piskornica", "Targowa",
                       "3B", "53-985", new TelephoneNumber("48", "296175970"));
        Person p3 = new Person("Kretek", "Pepik", "Brno", "Syrowa",
                       "8", "88-747", new TelephoneNumber("420", "884668101"));
        Company c1 = new Company("Kredkopol", "Lodz", "Zgierska", "294A",
                                 "90-525", new TelephoneNumber("48", "123456789"));
        Company c2 = new Company("Durr", "Hamburg", "Kleine-strasse",
                         "77", "11-888", new TelephoneNumber("42", "096964383"));
        // p1.description();
        // p2.description();
        // p3.description();
        // c1.description();
        // c2.description();

        TreeMap<TelephoneNumber, TelephoneEntry> directory = new TreeMap<TelephoneNumber, TelephoneEntry>();
        directory.put(p1.getTelephoneNumber(), p1);
        directory.put(p2.getTelephoneNumber(), p2);
        directory.put(p3.getTelephoneNumber(), p3);
        directory.put(c1.getTelephoneNumber(), c1);
        directory.put(c2.getTelephoneNumber(), c2);
        System.out.println(directory);
        Iterator<Map.Entry<TelephoneNumber, TelephoneEntry>> it = directory.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<TelephoneNumber, TelephoneEntry> pair = it.next();
            pair.getValue().description();
        }
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setTelephoneNumber(TelephoneNumber telephoneNumber){
        this.telephoneNumber = telephoneNumber;
    }
}

// Class Person has to contain information about the name, last name, and address (including TelephoneNumber)
class Person extends TelephoneEntry {
    private String  surname;

    Person(String name, String surname, String city, String street, String streetNumber, String postalCode, TelephoneNumber telephoneNumber) {
        this.setName(name);
        this.setSurname(surname);
        this.setCity(city);
        this.setStreet(street);
        this.setStreetNumber(streetNumber);
        this.setPostalCode(postalCode);
        this.setTelephoneNumber(telephoneNumber);
    }

    Person() {
        this.setName("-");
        this.setSurname("-");
        this.setCity("-");
        this.setStreet("-");
        this.setStreetNumber("-");
        this.setPostalCode("-");
        this.setTelephoneNumber(new TelephoneNumber("-", "-"));
    }

    @Override
    public void description() {
        if (this.getName() != null && this.getSurname() != null && this.getCity() != null && this.getStreet() != null
            && this.getStreetNumber() != null && this.getPostalCode() != null && this.getTelephoneNumber() != null
            && this.getTelephoneNumber().getCountryCode() != null && this.getTelephoneNumber().getLocalNumber() != null) {
            System.out.println("------------------------------\n"
                            + "           PERSON\n"
                            + "Name: " + this.getName() + "\n"
                            + "Surname: " + surname + "\n"
                            + "Addres:\n"
                            + "  City: " + this.getCity() + "\n"
                            + "  Street: " + this.getStreet() + " " + this.getStreetNumber() + "\n"
                            + "  Postal code: " + this.getPostalCode() + "\n"
                            + "  Telephone: +" + this.getTelephoneNumber().getCountryCode() + " " + this.getTelephoneNumber().getLocalNumber()
                            + "\n------------------------------");
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
// Class Company has to have the name and address (including TelephoneNumber)
class Company extends TelephoneEntry {

    Company(String name, String city, String street, String streetNumber, String postalCode, TelephoneNumber telephoneNumber) {
        setName(name);
        setCity(city);
        setStreet(street);
        setStreetNumber(streetNumber);
        setPostalCode(postalCode);
        setTelephoneNumber(telephoneNumber);
    }

    Company() {
        this.setName("-");
        this.setCity("-");
        this.setStreet("-");
        this.setStreetNumber("-");
        this.setPostalCode("-");
        this.setTelephoneNumber(new TelephoneNumber("-", "-"));
    }

    @Override
    public void description() {
        if (this.getName() != null && this.getCity() != null && this.getStreet() != null
            && this.getStreetNumber() != null && this.getPostalCode() != null && this.getTelephoneNumber() != null
            && this.getTelephoneNumber().getCountryCode() != null && this.getTelephoneNumber().getLocalNumber() != null) {
            System.out.println("------------------------------\n"
                            + "           COMPANY\n"
                            + "Name: " + this.getName() + "\n"
                            + "Addres:\n"
                            + "  City: " + this.getCity() + "\n"
                            + "  Street: " + this.getStreet() + " " + this.getStreetNumber() + "\n"
                            + "  Postal code: " + this.getPostalCode() + "\n"
                            + "  Telephone Number: +" + this.getTelephoneNumber().getCountryCode() + " " + this.getTelephoneNumber().getLocalNumber()
                            + "\n------------------------------");
        }
    }
}