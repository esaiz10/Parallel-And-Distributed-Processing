// FanThreads.java
public class FanThreads {

    static class Worker extends Thread {
        private final int workerId;

        public Worker(int workerId) {
            this.workerId = workerId;
        }

        @Override
        public void run() {
            System.out.println("Worker " + workerId + " starting, thread id = " + this.getId());
            for (int i = 1; i <= 3; i++) {
                System.out.println("Worker " + workerId + " iteration " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("Worker " + workerId + " interrupted");
                }
            }
            System.out.println("Worker " + workerId + " finished");
        }
    }

    public static void main(String[] args) {
        int numWorkers = 5;
        System.out.println("Parent starting fan of " + numWorkers + " threads");

        Thread[] workers = new Thread[numWorkers];

        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new Worker(i + 1);
            workers[i].start();
        }

        for (int i = 1; i <= 3; i++) {
            System.out.println("Parent working, iteration " + i);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                System.out.println("Parent interrupted");
            }
        }

        for (int i = 0; i < numWorkers; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                System.out.println("Join interrupted for worker " + (i + 1));
            }
        }

        System.out.println("Parent finished");
    }
}
