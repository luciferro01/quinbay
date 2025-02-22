import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;

public class XMLFileHandler implements MyFileHandler {
    private final File file;

    public XMLFileHandler(File file) {
        this.file = file;
    }

    @Override
    public Employee read() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(file, Employee.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(Employee emp) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(file, emp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}