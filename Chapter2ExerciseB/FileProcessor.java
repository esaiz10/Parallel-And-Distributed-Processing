import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor implements Runnable {
    private int threadNumber;
    private String fileName;

    public FileProcessor(int threadNumber, String fileName) {
        this.threadNumber = threadNumber;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        int tagCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                tagCount += countTags(line);
            }

            System.out.println("Thread Number: " + threadNumber +
                               ", File Name: " + fileName +
                               ", Tag Count: " + tagCount);
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
        }
    }

    private int countTags(String line) {
        int count = 0;
        int index = 0;

        while ((index = line.indexOf("<", index)) != -1) {
            int end = line.indexOf(">", index);
            if (end != -1) {
                count++;
                index = end + 1;
            } else {
                break;
            }
        }
        return count;
    }
}