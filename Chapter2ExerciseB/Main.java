public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main file1.html file2.html ...");
            return;
        }

        Thread[] threads = new Thread[args.length];

        for (int i = 0; i < args.length; i++) {
            String fileName = args[i];
            FileProcessor fp = new FileProcessor(i + 1, fileName);
            threads[i] = new Thread(fp);
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
