import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
     // start email Client
        Email_Client ec = new Email_Client();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Enter option type: \n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application\n");

            String opt = scanner.next();
            try {
                int option = Integer.parseInt(opt);

                // clear Console
                System.out.print("\033[H\033[2J");
                System.out.flush();

                switch (option) {
                    case 1:
                        // input format - Official: nimal,nimal@gmail.com,ceo
                        // Use a single input to get all the details of a recipient
                        // code to add a new recipient
                        // store details in clientList.txt file
                        // Hint: use methods for reading and writing files

                        System.out.println("Enter Recipient's Details: \n");
                        String fileInput = System.console().readLine();
                        String[] data = fileInput.split(": ");
                        String[] details = data[1].split(",");
                        String name = details[0];
                        String email = details[1];

                        if (data[0].equals("Official")) {
                            String designation = details[2];
                            ec.addRecipient(name, email, designation);

                        } else if (data[0].equals("Office_friend")) {
                            String designation = details[2];
                            LocalDate birthday = LocalDate.parse(details[3], formatter);
                            ec.addRecipient(name, email, designation, birthday);

                        } else if (data[0].equals("Personal")) {
                            String nick_name = details[2];
                            LocalDate birthday = LocalDate.parse(details[3], formatter);
                            ec.addRecipient(name, nick_name, birthday, email);

                        } else {
                            // Error Message...
                            System.out.println("Input is Invalid!...");
                            break;
                        }

                        try {
                            FileWriter recipients = new FileWriter("clientList.txt", true);
                            BufferedWriter buffer = new BufferedWriter(recipients);
                            buffer.write(fileInput);
                            buffer.newLine();
                            buffer.close();
                            System.out.println("\nRecipient added successfully...");
                        } catch (IOException e) {
                            System.out.println("Unexpected error occurred");
                            e.printStackTrace();
                        }

                        // hold screen
                        System.out.println("\nPress Enter to return back to Main Menu");
                        System.console().readLine();

                        // clear Console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        break;
                    case 2:
                        // input format - email, subject, content
                        // code to send an email
                        ec.sendMail();

                        // hold screen
                        System.out.println("\nPress Enter to return back to Main Menu");
                        System.console().readLine();

                        // clear Console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        break;
                    case 3:
                        // input format - yyyy/MM/dd (ex: 2018/09/17)
                        // code to print recipients who have birthdays on the given date
                        System.out.print("Enter Date: ");
                        String input = System.console().readLine();
                        LocalDate date = LocalDate.parse(input, formatter);

                        ArrayList<Recipient> birthdays = ec.getBirthdayRecipientsOnDate(date);

                        System.out.println("\n");
                        Formatter fmtr1 = new Formatter();
                        String col_sizes1 = "%30s %60s\n";
                        fmtr1.format(col_sizes1 + "\n", "Name", "Email Address");
                        for (Recipient recipient : birthdays) {

                            fmtr1.format(col_sizes1, recipient.getName(), recipient.getEmail());
                        }
                        System.out.println(fmtr1);

                        // hold screen
                        System.out.println("\nPress Enter to return back to Main Menu");
                        System.console().readLine();

                        // clear Console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        break;
                    case 4:
                        // input format - yyyy/MM/dd (ex: 2018/09/17)
                        // code to print the details of all the emails sent on the input date
                        System.out.print("Enter Date: ");
                        String input1 = System.console().readLine();
                        LocalDate date1 = LocalDate.parse(input1, formatter);

                        // get mails on selected date from emailClient
                        ArrayList<SentMail> sentMails = ec.getSentMailsOnDate(date1);

                        System.out.println("\n");
                        Formatter fmtr = new Formatter();
                        String col_sizes = "%30s %60s %50s\n";
                        fmtr.format(col_sizes + "\n", "Name", "Email Address", "Subject");
                        for (SentMail mail : sentMails) {

                            fmtr.format(col_sizes, mail.getRecipient().getName(), mail.getRecipient().getEmail(),
                                    mail.getSubject());
                        }
                        System.out.println(fmtr);

                        // hold screen
                        System.out.println("\nPress Enter to return back to Main Menu");
                        System.console().readLine();

                        // clear Console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        break;
                    case 5:
                        // code to print the number of recipient objects in the application
                        System.out.println("Number of Recipients in the application: " + Recipient.getRecipientCount());

                        // hold screen
                        System.out.println("\nPress Enter to return back to Main Menu");
                        System.console().readLine();

                        // clear Console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        break;
                    default:
                        System.out.println("Input is Invalid!...");

                        System.out.println("\nPress Enter to return back to Main Menu");
                        System.console().readLine();

                        // clear Console
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                }
            } catch (NumberFormatException e) {
                System.out.println("Input is Invalid!...");
                // hold screen
                System.out.println("\nPress Enter to return back to Main Menu");
                System.console().readLine();

                // clear Console
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            // start email client
            // code to create objects for each recipient in clientList.txt
            // use necessary variables, methods and classes

        }
        // scanner.close();
    }
}
