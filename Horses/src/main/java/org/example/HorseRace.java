package org.example;

import java.util.Random;

public class HorseRace {

    static class Horse extends Thread {
        private int horseNumber;
        private int distanceTraveled;
        private Random random;

        public Horse(int horseNumber) {
            this.horseNumber = horseNumber;
            this.distanceTraveled = 0;
            this.random = new Random();
        }

        @Override
        public void run() {
            while (distanceTraveled < 100) {
                int stepSize = random.nextInt(5) + 6;
                distanceTraveled += stepSize;

                System.out.println("Horse #" + horseNumber + " is at position: " + distanceTraveled);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }

            System.out.println("*** Horse #" + horseNumber + " finished! ***");
        }
    }

    public static void main(String[] args) {
        int numberOfHorses = 2;

        if (args.length > 0) {
            try {
                int userInput = Integer.parseInt(args[0]);
                if (userInput >= 2 && userInput <= 5) {
                    numberOfHorses = userInput;
                } else {
                    System.out.println("Please choose between 2 and 5 horses. Using default: 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Using default: 2 horses");
            }
        }

        System.out.println("\n🐴 Starting race with " + numberOfHorses + " horses! 🐴\n");

        Horse[] horses = new Horse[numberOfHorses];
        for (int i = 0; i < numberOfHorses; i++) {
            horses[i] = new Horse(i + 1);
        }

        for (int i = 0; i < numberOfHorses; i++) {
            horses[i].start();
        }

        for (int i = 0; i < numberOfHorses; i++) {
            try {
                horses[i].join();
            } catch (InterruptedException e) {
                System.out.println("Race was interrupted!");
            }
        }

        System.out.println("\n🏁 Race complete! 🏁");
    }
}
