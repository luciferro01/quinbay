import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHandler implements MyFileHandler {

    private final File file;
    private final CsvMapper csvMapper;

    // Need of schema while using CSV with Jackson
    private final CsvSchema schema;

    public CsvFileHandler(File file) {
        this.file = file;
        csvMapper = new CsvMapper();
        schema = csvMapper.schemaFor(Employee.class).withHeader();
    }

    //    private static MappingIterator<Employee> mappingIterator = csvMapper.readerFor(Employee.class).with(schema)
//            .readValues(file);
    private static MappingIterator<Employee> mappingIterator;

    @Override
    public Employee read() {
        // List<Employee> list = new ArrayList<>();

        try {
            if (file.exists() && file.length() > 0) {
                mappingIterator = csvMapper.readerFor(Employee.class).with(schema).readValues(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        return list;
        return mappingIterator.next();
    }

    @Override
    public void write(Employee emp) {
        try {
//            List<Employee> list = read();
//            list.add(emp);
//            csvMapper.writer(schema).writeValue(file, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}