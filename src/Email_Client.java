import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;


public class Email_Client {
    private ArrayList<Recipient> recipients;
    private ArrayList<Greetable> greetable_recipients;

    private static File f = new File("sentMails.txt");

    public Email_Client() {
        // read files and load recipients to application on startup

        this.recipients = new ArrayList<Recipient>();
        this.greetable_recipients = new ArrayList<Greetable>();

        // Load Recipients to application on startup
        try {
            File recipients_list = new File("clientList.txt");
            FileReader fr = new FileReader(recipients_list);
            BufferedReader buffer = new BufferedReader(fr);
            String record;
            while ((record = buffer.readLine()) != null) {

                String[] data = record.split(": ");
                String[] details = data[1].split(",");
                String name = details[0];
                String email = details[1];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                if (data[0].equals("Official")) {
                    String designation = details[2];
                    this.addRecipient(name, email, designation);
                } else if (data[0].equals("Office_friend")) {
                    String designation = details[2];
                    LocalDate birthday = LocalDate.parse(details[3], formatter);
                    this.addRecipient(name, email, designation, birthday);
                } else {
                    String nick_name = details[2];
                    LocalDate birthday = LocalDate.parse(details[3], formatter);
                    this.addRecipient(name, nick_name, birthday, email);
                }
            }
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start a seperate thread for sending birthday greeting.
        BackgroundStartup bc = new BackgroundStartup(this);
        bc.start();

    }

    public void sendBirthdayGreetings() {

        ArrayList<SentMail> today_mails = new ArrayList<SentMail>();
        today_mails = this.getSentMailsOnDate(LocalDate.now());
        int no_of_greetings_today = 0;
        for (SentMail mail : today_mails) {
            if ((mail.getRecipient() instanceof Greetable) && mail.getSubject().equals("Birthday Greeting!...")) {
                no_of_greetings_today++;
            }
        }

        int no_of_bDays_today = 0;
        ArrayList<Greetable> bDays_today = new ArrayList<Greetable>();
        int cur_mon = LocalDate.now().getMonthValue();
        int cur_day = LocalDate.now().getDayOfMonth();

        for (Greetable bday : greetable_recipients) {
            int month = bday.getBirthday().getMonthValue();
            int day = bday.getBirthday().getDayOfMonth();
            if (month == cur_mon && day == cur_day) {
                no_of_bDays_today++;
                bDays_today.add(bday);
            }
        }

        if (no_of_greetings_today < no_of_bDays_today) {
            for (int i = no_of_greetings_today; i < no_of_bDays_today; i++) {
                Greetable g_receiver = bDays_today.get(i);
                g_receiver.sendBirthdayGreeting();
                String msg;
                Recipient receiver;

                if (g_receiver instanceof Official) {
                    msg = OfficialFriend.getBirthdayGreeting();
                    OfficialFriend o_receiver = (OfficialFriend) g_receiver;
                    receiver = o_receiver;
                } else {
                    msg = Personal.getBirthdayGreeting();
                    Personal p_receiver = (Personal) g_receiver;
                    receiver = p_receiver;
                }

                SentMail sm = new SentMail(receiver, "Birthday Greeting!...", msg, LocalDate.now());
                try {
                    this.saveMail(sm);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public void addRecipient(String name, String email_address, String designation) {
        // create official recipient and add to arraylist
        Official recipient = new Official(name, email_address, designation);
        recipients.add(recipient);
    }

    public void addRecipient(String name, String email_address, String designation, LocalDate birthday) {
        // create office_friend recipient and add to arraylist
        OfficialFriend recipient = new OfficialFriend(name, email_address, designation, birthday);
        recipients.add(recipient);
        greetable_recipients.add(recipient);
    }

    public void addRecipient(String name, String email_address, LocalDate birthday, String nick_name) {
        // create personal recipient and add to arraylist
        Personal recipient = new Personal(name, nick_name, email_address, birthday);
        recipients.add(recipient);
        greetable_recipients.add(recipient);
    }

    public void sendMail() {

        System.out.println("Recipient List");

        // print Recipient List
        int no = 0;
        Formatter fmtr = new Formatter();
        String col_sizes = "%10s %30s %60s\n";
        fmtr.format(col_sizes + "\n", "No.", "Name", "Email Address");
        for (Recipient rec : recipients) {
            fmtr.format(col_sizes, no, rec.getName(), rec.getEmail());
            no++;
        }
        System.out.println(fmtr);

        // select Recipient
        System.out.print("\n\nEnter Recipient No: ");
        int rec_no = Integer.parseInt(System.console().readLine());

        // clear console
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // compose mail
        System.out.println("To : " + recipients.get(rec_no).getEmail());
        System.out.print("Subject : ");
        String subject = System.console().readLine();
        System.out.print("Message : ");
        String message = System.console().readLine();

        System.out.println("\nSending mail, Please wait!....\n");

        // sending Email to selected recipient
        Recipient receiver = recipients.get(rec_no);
        receiver.sendMail(subject, message);

        // save sent mail to hard disk
        SentMail sm = new SentMail(receiver, subject, message, LocalDate.now());
        try {
            this.saveMail(sm);
            System.out.println("Mail Saved Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveMail(SentMail sm) throws IOException {// throws here from method

        // save mail on hard disk using serialization
        try {
            FileOutputStream fout = new FileOutputStream("sentMails.txt", true);

            if (f.length() == 0) {
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(sm);
                // out.flush();
                out.close();
            } else {
                MyObjectOutputStream out = null;
                out = new MyObjectOutputStream(fout);
                out.writeObject(sm);
                out.close();

            }
            fout.close();
        } catch (Exception e) {
            throw new IOException(e);
        }

    }

    public ArrayList<SentMail> getSentMailsOnDate(LocalDate date) {

        ArrayList<SentMail> mails = new ArrayList<SentMail>();

        try {
            f.createNewFile();
        } catch (Exception e) {
        }

        if (f.length() != 0) {
            try {

                FileInputStream fin = new FileInputStream("sentMails.txt");
                ObjectInputStream in = new ObjectInputStream(fin);

                SentMail mail = null;
                while (fin.available() != 0) {
                    mail = (SentMail) in.readObject();

                    if (date.compareTo(mail.getSentDate()) == 0) {
                        mails.add(mail);
                    }
                }
                in.close();
                fin.close();
            } catch (Exception e) {

                System.out.println("Error Occurred" + e);
                e.printStackTrace();
            }
        }

        return mails;

    }

    public ArrayList<Recipient> getBirthdayRecipientsOnDate(LocalDate date) {

        ArrayList<Recipient> greetables_onDate = new ArrayList<Recipient>();

        int cur_mon = date.getMonthValue();
        int cur_day = date.getDayOfMonth();

        for (Greetable recipient : greetable_recipients) {

            int month = recipient.getBirthday().getMonthValue();
            int day = recipient.getBirthday().getDayOfMonth();
            if (month == cur_mon && day == cur_day) {
                greetables_onDate.add((Recipient) recipient);
            }
            
        }
        return greetables_onDate;

    }
}
