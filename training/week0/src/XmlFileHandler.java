import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlFileHandler implements MyFileHandler {
    private final File file;
    private final XmlMapper xmlMapper;

    public XmlFileHandler(File file) {
        this.file = file;
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public List<Employee> read() {
        List<Employee> list = new ArrayList<>();
        try {
            if (file.exists() && file.length() > 0) {
                Employee[] emp = xmlMapper.readValue(file, Employee[].class);

                // Got to know that Arrays.asList() returns a fixed-size list that does not support add or remove methods.
                // So, I changed it to ArrayList again.
                list = new ArrayList<>(Arrays.asList(emp));

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public void write(Employee emp) {
        try {
            List<Employee> list = read();
//            System.out.println(list);
//            if (list == null) {
//                list = new ArrayList<>();
//            }
            list.add(emp);
            xmlMapper.writeValue(file, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}