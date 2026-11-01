# Fitness Club Management System

## Project Overview

Fitness Club Management System is a console-based Java application developed as an academic group project for the Object-Oriented Programming Techniques course.

The system provides basic fitness club management functions, including member registration, class scheduling, trainer management, equipment tracking, simulated payments, and BMI calculation.

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- JSON File Handling
- IntelliJ IDEA

## Main Features

- **Member Management:** Member registration, login, profile viewing, and account deletion.
- **Class Booking:** View available fitness classes, book or cancel sessions, and manage class capacity.
- **Trainer Portal:** Trainer login, class information, and revenue reporting.
- **Equipment Management:** Display and update fitness equipment availability.
- **Payment Simulation:** Simulated payments and receipt generation.
- **BMI Calculator:** Calculate Body Mass Index using height and weight information.

## Object-Oriented Programming Concepts

The application demonstrates:

- **Abstraction:** Abstract `User` class.
- **Inheritance:** `Member` and `Trainer` classes extend `User`.
- **Encapsulation:** Classes and manager components organise and manage application data.
- **Modularity:** Separate Java classes handle member, trainer, booking, and equipment operations.

## How to Run

1. Install a Java Development Kit (tested with JDK 11.0.21).
2. Download or clone this repository.
3. Open the project in IntelliJ IDEA or another Java-compatible IDE.
4. Open `src/MainSystem.java`.
5. Set the working directory to the project root and run the `MainSystem` class.
6. Follow the console menu to explore the system features.

Alternatively, from the project root directory, compile and run the project using:

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out MainSystem
```

The application uses only the Java standard library; no external dependencies are required. Run commands from the project root. There are 18 Java source files.

The program uses JSON files in the current working directory. `equipment.json` supplies five sample equipment records and may be updated during use. `members.json` and `trainers.json` are intentionally excluded. A fresh run starts with no members and creates seven fictional trainers in memory. Local member/trainer files may be saved during use and are ignored by Git.

## Demo Trainer Login

Select **3. Trainer & Client Portals**, then **1. Login**.

| Username | Public demo password |
| --- | --- |
| DemoAlice | DemoOnly01 |
| DemoJohn | DemoOnly02 |
| DemoMike | DemoOnly03 |
| DemoSarah | DemoOnly04 |
| DemoDavid | DemoOnly05 |
| DemoEmma | DemoOnly06 |
| DemoJames | DemoOnly07 |

Names are case-sensitive in the trainer portal. Phone and identity fields use explicit `DEMO-PHONE-01` / `DEMO-ID-01` placeholders. These are constructor seed values, not values intended for the numeric registration form. Existing local `trainers.json` takes precedence over the defaults; use a fresh extracted copy to try these accounts.

## UML

[View the original academic UML diagram](doc/UML%20Diagram.jpeg).

This image is retained as historical design documentation, not an exact API specification. For example, it labels `Schedule` as `Schedules`, lists some setters absent from the current code, and calls the trainer class-view method `viewMySchedule` rather than `viewMyClasses`. The Java source is authoritative.


## Notes

- This project is intended for academic and demonstration purposes.
- Payments are simulated and do not involve real transactions.
- Local JSON files are used for data persistence.
- Demo passwords are public. Passwords are stored and compared in plaintext; do not enter real customer details or reused passwords.
- The system has not been designed or security-hardened for real customer data.