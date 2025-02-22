public class MyCollection {
    public Employee[] employees = new Employee[300];
    private int writeCounter = 0;
    private int readCounter = 0;

    //Getters & Setter

    public int getWriteCounter(){
        return writeCounter;
    }
    public int getReadCounter(){
        return readCounter;
    }
    public void setWriteCounter(int writeCounter){
        this.writeCounter = writeCounter;
    }

    public void setReadCounter(int readCounter){
        this.readCounter = readCounter;
    }

    // Methods

    public void addEmployee(Employee employee) {
        employees[writeCounter] = employee;
        writeCounter++;
    }

    public Employee getEmployee() {
        Employee employee = employees[readCounter];
        readCounter++;
        return employee;
    }
}
