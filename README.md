# Java Email Client (Multithreaded)

This is a command-line based email client implemented in Java. The email client allows you to manage two types of recipients: official and personal. You can add recipients, send emails, and manage birthday greetings for recipients. What sets this application apart is its multithreaded capability for sending birthday greetings in the background. Here's how to use the email client:

## Getting Started

To run the email client, follow these steps:

1\. Clone this GitHub repository to your local machine.

2\. Compile the Java code.

```shell

javac App.java

```

3\. Run the email client.

```shell

java App

```
## Dependencies

This application relies on the following libraries:

- `javax.mail` package for email functionality. You will need to include the `javax.mail.jar` in your project's classpath.

    You can download the `javax.mail.jar` from the following link: [javax.mail.jar](link-to-javax.mail.jar)

- `activation` library for additional functionality. You will need to include the `activation.jar` in your project's classpath.

    You can download the `activation.jar` from the following link: [activation.jar](link-to-activation.jar)

Make sure to add both JAR files to your project to enable the required functionality.


## Features

### 1. Adding a New Recipient

You can add a new recipient by entering their details in the following format:

- Official Recipient: `Official: name,email,designation`

- Official Friend: `Office_friend: name,email,designation,birthday (yyyy/MM/dd)`

- Personal Recipient: `Personal: name,nick-name,email,birthday (yyyy/MM/dd)`

Follow the on-screen instructions to add a new recipient. The recipient details will be saved in the `clientList.txt` file.

### 2. Sending an Email

You can send an email to a recipient by following these steps:

- Select a recipient from the list.

- Enter the email subject and message.

The email will be sent to the selected recipient, and a copy will be saved in the `sentMails.txt` file.

### 3. Printing Birthday Recipients

You can view a list of recipients who have birthdays on a specific date. Enter the date in the format `yyyy/MM/dd`, and the email client will display the matching recipients' names and email addresses.

### 4. Printing Email Details

You can view details of all the emails sent on a specific date. Enter the date in the format `yyyy/MM/dd`, and the email client will display the sender's name, recipient's email address, and email subject.

### 5. Printing the Number of Recipient Objects

You can check the total number of recipient objects currently in the application.

### 6. Multithreaded Birthday Greetings

One of the standout features of this email client is its ability to send birthday greetings in the background. When the email client is started, a separate thread runs in the background, checking for recipients with birthdays on the current day. If there are recipients with birthdays, birthday greetings are sent automatically. This ensures that you never miss sending birthday wishes to your contacts.

## Notes

- Official friends and personal recipients receive different birthday messages.

- All personal recipients receive the same message, and all office friends receive the same message.

- Emails sent by the email client are saved in the `sentMails.txt` file using object serialization.

## Acknowledgments

This email client was developed as part of an assignment. Some code and concepts were provided as a starting point.

Please note that this README provides an overview of the email client's functionality. For detailed usage instructions, follow the on-screen prompts when running the program.