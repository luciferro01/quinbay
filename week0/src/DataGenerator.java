import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class DataGenerator {
    public static void main(String[] args) throws IOException {

        //Not working in this way because it is overwriting the file


        //JSON
//        JsonFileHandler jsonFileHandler = new JsonFileHandler(new File("employee.json"));
//
//        for (int i = 0; i < 100; i++) {
//            Employee emp = new Employee("Json Emp", i + "", new Date(), 5.5);
//            jsonFileHandler.write(emp);
//        }
//
//        //CSV
//        CsvFileHandler csvFileHandler = new CsvFileHandler(new File("employee.csv"));
//        for (int i = 0; i < 100; i++) {
//            Employee emp = new Employee("CSV Emp", i + "", new Date(), 5.5);
//            csvFileHandler.write(emp);
//        }
//
//        //XML
//        XmlFileHandler xmlFileHandler = new XmlFileHandler(new File("employee.xml"));
//        for (int i = 0; i < 100; i++) {
//            Employee emp = new Employee("XML Emp", i + "", new Date(), 5.5);
//            xmlFileHandler.write(emp);
//        }


        //Trying another way to create the arrayList/List and then writing to the file
        //JSON
        JsonFileHandler jsonFileHandler = new JsonFileHandler(new File("employee.json"));
        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectNode jsonNode = jsonMapper.createObjectNode();
        ObjectNode[] jsonNodeArray = new ObjectNode[100];

        for (int i = 0; i < 100; i++) {
            jsonNodeArray[i] = jsonMapper.createObjectNode().put("FirstName", i);
        }
        jsonMapper.writeValue(new File("employee.json"), jsonNodeArray);

    }
}
