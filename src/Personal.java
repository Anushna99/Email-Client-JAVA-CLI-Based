import java.time.LocalDate;

public class Personal extends Recipient implements Greetable{
    transient private LocalDate birthday;
    transient private String nick_name;
    transient private static String birthday_greet = "Hugs and love on your birthday. Anushna";

    public Personal(String name, String nick_name, String email_address, LocalDate birthday) {
        super(name, email_address);
        this.nick_name = nick_name;
        this.birthday = birthday;
    }

    public void sendBirthdayGreeting() {
        // birthday greeting for personal recipient
        try {
            Mailer.send(this.getEmail(), "Birthday Greeting!...", birthday_greet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public String getNickName() {
        return this.nick_name;
    }

    public static String getBirthdayGreeting() {
        return birthday_greet;
    }
}
