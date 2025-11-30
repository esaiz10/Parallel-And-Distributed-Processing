// SimpleThreads.java
public class SimpleThreads {

    static class Worker extends Thread {
        private final String name;

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(name + " running, iteration " + i +
                        ", thread id = " + this.getId());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    System.out.println(name + " interrupted");
                }
            }
            System.out.println(name + " finished");
        }
    }

    public static void main(String[] args) {
        System.out.println("Main thread starting");

        Worker child = new Worker("Child");
        child.start();

        for (int i = 1; i <= 5; i++) {
            System.out.println("Parent running, iteration " + i +
                    ", thread id = " + Thread.currentThread().getId());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Parent interrupted");
            }
        }

        try {
            child.join();
        } catch (InterruptedException e) {
            System.out.println("Join interrupted");
        }

        System.out.println("Main thread finished");
    }
}
