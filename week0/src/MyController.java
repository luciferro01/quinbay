import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyController {

    private static Employee generateEmployee(int id) throws ParseException {
        String firstName = "First" + id;
        String lastName = "Last" + id;
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
            MyFileHandler csvFileHandler = new CsvFileHandler( new File("employees.csv"));
            for (Employee emp: employees) {
                csvFileHandler.write(emp);
            }
        });

        Thread jsonThread = new Thread(() -> {
            MyFileHandler jsonFileHandler = new JsonFileHandler(new File("employees.json"));
            for (Employee emp: employees) {
                jsonFileHandler.write(emp);
            }
        });

        Thread xmlThread = new Thread(() -> {
            MyFileHandler xmlFileHandler = new XmlFileHandler(new File("employees.xml"));
            for (Employee emp: employees) {
                xmlFileHandler.write(emp);
            }
        });

        csvThread.start();
        jsonThread.start();
        xmlThread.start();
    }

    public static void main(String[] args) {

//        System.out.println((generateEmployee(1)));
        generatedataFiles(20);
        System.out.println("Hello, World!");
    }
}