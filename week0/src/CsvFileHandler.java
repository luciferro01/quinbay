import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;

public class CsvFileHandler implements MyFileHandler {

    private final File file;

    public CsvFileHandler(File file) {
        this.file = file;
    }

    @Override
    public Employee read() {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema schema = csvMapper.schemaFor(Employee.class).withHeader();
            return csvMapper.readerFor(Employee.class)
                    .with(schema)
                    .<Employee>readValues(file)
                    .next(); // Read the first record
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void write(Employee emp) {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema schema = csvMapper.schemaFor(Employee.class).withHeader();
            csvMapper.writer(schema).writeValue(file, emp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}