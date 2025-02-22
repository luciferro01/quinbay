import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MyController {

    private static Employee generateEmployee(int id) throws ParseException {
        String firstName = "First" + id;
        String lastName = "Last" + id;
        Date dob = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        double experience = id % 10 + 1;
        return new Employee(firstName, lastName, dob, experience);
    }

    public static void main(String[] args) throws ParseException {

        System.out.println((generateEmployee(1)));
        System.out.println("Hello, World!");
    }
}