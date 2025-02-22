import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class JsonFileHandler implements MyFileHandler {

    private final File file;

    public JsonFileHandler(File file) {
        this.file = file;
    }

    @Override
    public Employee read() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, Employee.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(Employee emp) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, emp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
