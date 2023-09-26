import java.time.LocalDate;

public class OfficialFriend extends Official implements Greetable {
    transient private LocalDate birthday;
    transient private static String birthday_greet = "Wish you a Happy Birthday. Anushna";

    public OfficialFriend(String name, String email_address, String designation, LocalDate birthday) {
        super(name, email_address, designation);
        this.birthday = birthday;
    }

    public void sendBirthdayGreeting() {
        // birthday greeting for office friend
        try {
            Mailer.send(this.getEmail(), "Birthday Greeting!...", birthday_greet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public static String getBirthdayGreeting() {
        return birthday_greet;
    }
}
