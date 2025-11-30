// ChainThreads.java
public class ChainThreads {

    static class ChainWorker extends Thread {
        private final int depth;
        private final int maxDepth;

        public ChainWorker(int depth, int maxDepth) {
            this.depth = depth;
            this.maxDepth = maxDepth;
        }

        @Override
        public void run() {
            System.out.println("ChainWorker at depth " + depth + " starting, thread id = " + this.getId());

            if (depth < maxDepth) {
                ChainWorker child = new ChainWorker(depth + 1, maxDepth);
                child.start();
            }

            for (int i = 1; i <= 2; i++) {
                System.out.println("Depth " + depth + " working, iteration " + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    System.out.println("ChainWorker at depth " + depth + " interrupted");
                }
            }

            System.out.println("ChainWorker at depth " + depth + " finished");
        }
    }

    public static void main(String[] args) {
        int maxDepth = 5;
        System.out.println("Starting chain of threads with max depth " + maxDepth);

        ChainWorker first = new ChainWorker(1, maxDepth);
        first.start();
        try {
            first.join();
        } catch (InterruptedException e) {
            System.out.println("Main join interrupted");
        }

        System.out.println("Main finished");
    }
}
