package erpsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Lupe
 */
public class HRApp implements ERPSystem {

    // globals used for starting program
    private String country;
    private String region;
    private static final HRApp INSTANCE = new HRApp();
    private HRApp(){}

    // Singleton Pattern
    public static HRApp getInstance() {
        return INSTANCE;
    }

    public static void main(String args[]) throws FileNotFoundException
    {
        // create an instance of hrapp to use
        HRApp hr = new HRApp();
        // sets country and region bsaed on user input
        // we will call getInstance() each time for usage as Singleton
        hr.getInstance().setCountry(hr.getInstance().countrySelection());
        if (hr.getInstance().getCountry().equals("US"))
        {
            hr.getInstance().setRegion(hr.getInstance().regionSelection());
        }
        else
        {
            hr.getInstance().setRegion(hr.getInstance().getCountry());
        }
        // shows menu of actions that can be performed
        hr.getInstance().openApp();
    }

    @Override
    public void openApp()
    {
        // list of employees, reviews, and events
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<String> reviews = new ArrayList<>();
        ArrayList<Calendar> calendar = new ArrayList<>();

        try
        {
            // if the file is not found or broken
            employees = readEmployees();
            reviews = readReviews();
        }

        catch(FileNotFoundException ex)
        {
            System.err.println("An error occurred while reading the file.");
        }

        // lets add our first default events
        Calendar first = new Calendar("12/12/2018","Company wide meeting",30);
        calendar.add(first);
        printMenu();

        // reads in user input for menu
        Scanner usrIn = new Scanner(System.in);
        String userInput = usrIn.nextLine();
        Scanner menu;

        // since "8" quits, we will keep running until as long as input is not "8"
        while (!userInput.equals("8"))
        {
            switch (userInput)
            {
                // finds an employee based on name and then prints info
                case "1":
                    System.out.println("Please enter the full name of the employee in the format FIRST LAST.");
                    menu = new Scanner(System.in);
                    boolean found = false;
                    // we will use toUpperCase() to make sure that case is not sensitive
                    String employee = menu.nextLine().toUpperCase();
                    for (int i = 0; i < employees.size(); i++)
                    {
                        // if we find a match, we will flip the boolean and display the employee's info
                        if (employees.get(i).getName().toUpperCase().equals(employee))
                        {
                            found = true;
                            displayEmployeeInfo(employees.get(i));
                            break;
                        }
                    }

                    // if we can't find the employee then let the end user know
                    if (!found)
                    {
                        System.out.println("Employee '"+employee+"' does not exist.");
                    }

                    break;

                // email the company or an individual employee
                case "2":
                    // read in a message and store it
                    System.out.println("What is your message?");
                    menu = new Scanner(System.in);
                    String message = menu.nextLine();
                    // decide if we should send to every employee in the list or just one
                    System.out.println("Do you want to message the COMPANY or an EMPLOYEE?");
                    String answer = menu.nextLine().toUpperCase();
                    switch (answer)
                    {
                        // if company answered then we read the list of employees and sned an email to each of them
                        case "COMPANY":
                            for (int k = 0; k < employees.size(); k++)
                            {
                                sendEmail(employees.get(k).getEmail());
                            }
                            break;
                        // if employee was answered, we need to find them in the list
                        case "EMPLOYEE":
                            System.out.println("Please enter the full name of the employee in the format FIRST LAST.");
                            boolean found2 = false;
                            menu = new Scanner(System.in);
                            String name = menu.nextLine().toUpperCase();
                            for (int i = 0; i < employees.size(); i++)
                            {
                                // get the employee's email address and call sendEmail() to message them
                                if (employees.get(i).getName().toUpperCase().equals(name))
                                {
                                    sendEmail(employees.get(i).getEmail());
                                    found2 = true;
                                }
                            }
                            if (!found2)
                            {
                                System.out.println("Employee '"+name+"' does not exist.");
                            }
                            break;
                        default:
                            System.out.println("Invalid input, terminating action. Please try again.");
                            break;
                    }
                    break;

                // finds performance review based on full name input
                case "3":
                    System.out.println("Please enter the full name of the employee in the format FIRST LAST.");
                    menu = new Scanner(System.in);
                    String performance = menu.nextLine().toUpperCase();
                    boolean found3 = false;
                    // loop through and try to match the name
                    for (int i = 0; i < reviews.size(); i++)
                    {
                        String[] split = reviews.get(i).split(",");
                        // if matched then we print their performance ratings
                        if (split[0].toUpperCase().equals(performance))
                        {
                            found3 = true;
                            System.out.println("Employee '"+split[0]+"' was last given a performance rating of "+split[1]);
                        }
                    }
                    if (!found3)
                    {
                        System.out.println("Employee '"+performance+"' does not exist.");
                    }
                    break;

                // view the calendar or make a new event
                case "4":
                    System.out.println("Would you like to view the CALENDAR or SCHEDULE an employee appointment?");
                    menu = new Scanner(System.in);
                    String schedule = menu.nextLine().toUpperCase();
                    // either display the calendar or schedule something
                    switch (schedule) {
                        case "CALENDAR":
                            if(!calendar.isEmpty())
                            {
                                for (int j = 0; j < calendar.size(); j++)
                                {
                                    System.out.println(calendar.get(j).toString());
                                }
                            }
                            else
                            {
                                System.out.println("There are no events to display.");
                            }
                            break;
                        // ask for info for new event
                        case "SCHEDULE":
                            try{
                                System.out.println("Please enter the date of the event using MM/DD/YYYY.");
                                String date = menu.nextLine();
                                System.out.println("Please enter the event.");
                                String event = menu.nextLine();
                                System.out.println("Please enter the duration in hours of the event.");
                                int duration = Integer.parseInt(menu.nextLine().replaceAll("\\s+",""));
                                Calendar c = new Calendar(date, event, duration);
                                calendar.add(c);
                                break;
                            }
                            catch(NumberFormatException e)
                            {
                                System.err.println(e);
                                System.out.println("Not a number. Please try again.");
                            }
                        default:
                            System.out.println("Invalid input, terminating action. Please try again.");
                            break;
                    }
                    break;

                // process changes in employment
                case "5":
                    System.out.println("Would you like to process a NEW HIRE, TERMINATION, or RETIREMENT?");
                    menu = new Scanner(System.in);
                    String process = menu.nextLine().toUpperCase();
                    switch (process) {
                        // a new hire will add a new person to the list
                        case "NEW HIRE":
                            addEmployee(employees);
                            break;
                        // a termination will remove an existing person from the list
                        case "TERMINATION":
                            removeEmployee(employees);
                            System.out.println("Termination process complete.");
                            break;
                        // a retirement will remove an existing person and process their 401K
                        case "RETIREMENT":
                            removeEmployee(employees);
                            System.out.println("401K has been processed.");
                            break;
                        default:
                            System.out.println("Invalid input, terminating action. Please try again.");
                            break;
                    }
                    break;

                // view the list of employees
                case "6":
                    viewEmployees(employees);
                    break;
                //view the country and region
                case "7":
                    viewLocale();
                    break;
                // anything besides 1,2,3,4,5,6,7,8 is invalid input and will show the menu again
                default:
                    System.out.println("Invalid input, please try again.");
            }

            printMenu();
            userInput = usrIn.nextLine();
        }
    }

    // sets the country
    @Override
    public void setCountry(String country)
    {
        this.country = country;
        System.out.println("Country has been set to "+country+".");
    }

    // returns the current country
    @Override
    public String getCountry()
    {
        return this.country;
    }

    // sets the region
    @Override
    public void setRegion(String region)
    {
        this.region = region;
        System.out.println("Region has been set to "+region+".");
    }

    // returns the current region
    @Override
    public String getRegion()
    {
        return this.region;
    }

    // prompts the user to select their country
    public String countrySelection()
    {
        System.out.println(
                            "Please enter the letter from the list to select a country:\n\n" +
                            "A. US\n" +
                            "B. DE\n" +
                            "C. NZ"
                    );
        Scanner countries = new Scanner(System.in);
        String letter = countries.nextLine().toUpperCase();

        // loop until a valid choice is selected
        OUTER:
        while (true) {
            switch (letter) {
                case "A":
                    letter = "US";
                    break OUTER;
                case "B":
                    letter = "DE";
                    break OUTER;
                case "C":
                    letter = "NZ";
                    break OUTER;
                default:
                    System.out.println("Please try entering the letter again.");
                    letter = countries.nextLine().toUpperCase();
                    break;
            }
        }
        return letter;
    }

    // if the country is US<, we select a region
    public String regionSelection()
    {
        System.out.println(
                            "Please enter the letter from the list to select a region:\n\n" +
                            "A. NORTHEAST\n" +
                            "B. SOUTHEAST\n" +
                            "C. MIDWEST\n" +
                            "D. SOUTHWEST\n" +
                            "E. NORTHWEST"
                    );
        Scanner regions = new Scanner(System.in);
        String selection = regions.nextLine().toUpperCase();

        // loop until a valid choice is selected
        OUTER:
        while (true) {
            switch (selection) {
                case "A":
                    selection = "NORTHEAST";
                    break OUTER;
                case "B":
                    selection = "SOUTHEAST";
                    break OUTER;
                case "C":
                    selection = "MIDWEST";
                    break OUTER;
                case "D":
                    selection = "SOUTHWEST";
                    break OUTER;
                case "E":
                    selection = "NORTHWEST";
                    break OUTER;
                default:
                    System.out.println("Please try entering the letter again.");
                    selection = regions.nextLine().toUpperCase();
                    break;
            }
        }

        return selection;
    }

    // prints the menu
    public void printMenu()
    {
        System.out.println(
            "Choose an option:\n\n" +
            "1. Display employee information\n" +
            "2. Send email\n" +
            "3. Check employee performance\n" +
            "4. Employee Scheduling\n" +
            "5. New Hire/Temination/Retirement\n" +
            "6. View all employees\n" +
            "7. View locale\n" +
            "8. Exit");
    }

    // reads in file of employees
    public ArrayList<Employee> readEmployees() throws FileNotFoundException
    {
        ArrayList <Employee> list = new ArrayList<>();
        File file = new File("employees.txt");
        Scanner sc = new Scanner(file);
        // reads each line from the file and splits on the comma
        // creates a new Employee based on the string split
        while (sc.hasNext())
        {
            String readIn = sc.nextLine();
            String[] split = readIn.split(",");
            Employee e = new Employee(split[0], split[1], split[2], split[3], split[4], split[5], Integer.parseInt(split[6]));
            list.add(e);
        }
        return list;
    }

    // prints the information of an employee
    public void displayEmployeeInfo(Employee e)
    {
        System.out.println(e.printEmployee());
    }

    // reads in an email address and send message to that address
    public void sendEmail(String email)
    {
        System.out.println("Message successfully sent to: "+email+".");
    }

    // reads in all of the info needed for adding a new employee
    public void addEmployee(ArrayList<Employee> ae)
    {
        System.out.println("Please enter the full name of the employee in the format FIRST LAST.");
        Scanner add = new Scanner(System.in);
        String name = add.nextLine();
        System.out.println("Please enter the employee's address.");
        String address = add.nextLine();
        System.out.println("Please enter the employee's email.");
        String email = add.nextLine();
        System.out.println("Please enter the employee's date of birth as MM/DD/YYYY.");
        String dob = add.nextLine();
        System.out.println("Please enter the employee's gender.");
        String gender = add.nextLine();
        System.out.println("Please enter the employee's ethnicity.");
        String ethnicity = add.nextLine();
        System.out.println("Please enter the employee's age as a numeral.");
        int age = Integer.parseInt(add.nextLine());
        Employee temp = new Employee(name,address,email,dob,gender,ethnicity,age);
        ae.add(temp);
    }

    // removes an employee from the list of all employees
    public void removeEmployee(ArrayList<Employee> ae)
    {
        System.out.println("Please enter the full name of the employee in the format FIRST LAST.");
        Employee removeMe = searchEmployees(ae);
        if (removeMe != null)
        {
            ae.remove(removeMe);
        }
    }

    // reads in the file with performance reviews
    public ArrayList<String> readReviews() throws FileNotFoundException
    {
        File file = new File("performance_reviews.txt");
        Scanner reviews = new Scanner(file);
        ArrayList<String> s = new ArrayList<>();
        while (reviews.hasNext())
        {
            s.add(reviews.nextLine());
        }

        return s;
    }

    // prints all of the employees in the list
    public void viewEmployees(ArrayList<Employee> ae)
    {
        for (int y = 0; y < ae.size(); y++)
        {
            System.out.println(ae.get(y).printEmployee());
        }
    }

    // prints the selected country and region
    public void viewLocale()
    {
        System.out.println("You are located in the "+getCountry()+ " in the region of "+getRegion()+".");
    }

    // searches for employee and returns if found
    public Employee searchEmployees(ArrayList<Employee> ae)
    {
        Scanner search = new Scanner(System.in);
        boolean found = false;
        // we will use toUpperCase() to make sure that case is not sensitive
        String emp = search.nextLine().toUpperCase();
        for (int i = 0; i < ae.size(); i++)
        {
            // if we find a match, we will flip the boolean and display the employee's info
            if (ae.get(i).getName().toUpperCase().equals(emp))
            {
                return (ae.get(i));
            }
        }

        return null;
    }
}
