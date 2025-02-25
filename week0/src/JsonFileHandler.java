import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonFileHandler implements MyFileHandler {

    private final File file;
    private ObjectMapper objectMapper;

    public JsonFileHandler(File file) {
        this.file = file;
        objectMapper = new ObjectMapper();
    }

    private static Employee[] employees;
    private static int readCount = 0;
    private static int writeCount = 0;

    @Override
    public Employee read() {
        Employee employee = null;
        try {

            if (file.exists() && file.length() > 0) {
                Employee[] employees = objectMapper.readValue(file, Employee[readCount].class);
//                employee = objectMapper.readValue(file, Employee[readCount].class);

                // This is another way to convert array to list
                //list = Arrays.asList(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return list;
        return employee;

    }

    @Override
    public void write(Employee employee) {
        try {
            // Find a better option to append to the file instead of reading and writing the whole file
            // Tried something with jsonNode (ObjectNode) in DataGenerator file.
            // TODO: Find a better way to append to the file
            List<Employee> list = read();
            list.add(employee);
            objectMapper.writeValue(file, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
