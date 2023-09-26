public class BackgroundStartup extends Thread {
    private Email_Client ec;

    public BackgroundStartup(Email_Client ec) {
        this.ec = ec;
    }

    public void run() {
        ec.sendBirthdayGreetings();

    }
}
