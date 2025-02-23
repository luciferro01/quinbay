import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyController {

    private static final MyCollection collection = new MyCollection();

    private static Employee generateEmployee(int id) throws ParseException {
        String firstName = "First" + id;
        String lastName = "Last" + id;
        //TODO: It is not being implemented into the Employee class while writing to the file acting just like normal string
        Date dob = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        double experience = id % 10 + 1;
        return new Employee(firstName, lastName, dob, experience);
    }

    private static void generatedataFiles(int input){
        List<Employee> employees = new ArrayList<>();

        for (int i = 1; i <= input; i++) {
            try {
                employees.add(generateEmployee(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Thread csvThread = new Thread(() -> {
            new File("employees.csv").delete();
            MyFileHandler csvFileHandler = new CsvFileHandler( new File("employees.csv"));
            for (Employee emp: employees) {
                csvFileHandler.write(emp);
            }
        });

        Thread jsonThread = new Thread(() -> {
            new File("employees.json").delete();
            MyFileHandler jsonFileHandler = new JsonFileHandler(new File("employees.json"));
            for (Employee emp: employees) {
                jsonFileHandler.write(emp);
            }
        });

        Thread xmlThread = new Thread(() -> {
            new File("employees.xml").delete();
            MyFileHandler xmlFileHandler = new XmlFileHandler(new File("employees.xml"));
            for (Employee emp: employees) {
                xmlFileHandler.write(emp);
            }
        });

        csvThread.start();
        jsonThread.start();
        xmlThread.start();

        try {
            csvThread.join();
            jsonThread.join();
            xmlThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

//        System.out.println((generateEmployee(1)));
        generatedataFiles(100);

        Thread csvReaderThread = new Thread(() -> {
            CsvFileHandler csvHandler = new CsvFileHandler(new File("employees.csv"));
            List<Employee> employees = csvHandler.read();
            for (Employee emp : employees) {
                collection.addEmployee(emp);
            }
        });

        Thread xmlReaderThread = new Thread(() -> {
            XmlFileHandler xmlHandler = new XmlFileHandler(new File("employees.xml"));
            List<Employee> employees = xmlHandler.read();
            for (Employee emp : employees) {
                collection.addEmployee(emp);
            }
        });

        Thread jsonReaderThread = new Thread(() -> {
            JsonFileHandler jsonHandler = new JsonFileHandler(new File("employees.json"));
            List<Employee> employees = jsonHandler.read();
            for (Employee emp : employees) {
                collection.addEmployee(emp);
            }
        });

        // Start reader threads
        csvReaderThread.start();
        xmlReaderThread.start();
        jsonReaderThread.start();

        // Wait for reader threads to finish
        try {
            csvReaderThread.join();
            xmlReaderThread.join();
            jsonReaderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Just for testing purposes nothing personal
//        for (Employee emp : collection.employees) {
//            {
//                System.out.println(emp);
//            }
//        }
    }
}