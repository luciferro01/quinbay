class Singleton {
    private static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null)
                    instance = new Singleton();
            }
        }
        return instance;
    }

    public void print() {
        System.out.println("Singleton class");
    }

    public static void main(String args[]) {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                Singleton s1 = Singleton.getInstance();
                s1.print();
            }
        }, "Thread 1");

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                Singleton s2 = Singleton.getInstance();
                s2.print();
            }
        }, "Thread 2");
        t1.start();
        t2.start();
        Singleton s1 = Singleton.getInstance();
        s1.print();
        Singleton s2 = Singleton.getInstance();
        s2.print();
    }
}