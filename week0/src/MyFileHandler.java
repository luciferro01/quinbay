import java.util.List;

interface MyFileHandler{
    List<Employee> read();
    void write(Employee employee);
}