package org.example;

import java.util.Random;

public class BuildingSearch {

    private static final int MAX_FLOORS = 1000;
    private static final int FLOOR_SIZE = 10;

    private static boolean[][][] building = new boolean[MAX_FLOORS][FLOOR_SIZE][FLOOR_SIZE];

    private static volatile boolean personFound = false;

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("\nUsage: java BuildingSearch #floors(1-" + MAX_FLOORS + ")");
            return;
        }

        int floorCount;
        try {
            floorCount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid floor count.");
            return;
        }

        if (floorCount < 1 || floorCount > MAX_FLOORS) {
            System.err.println("Number of floors out of range.");
            return;
        }

        hidePerson(floorCount);
        runSearch(floorCount);
    }

    private static void hidePerson(int floorCount) {
        for (int f = 0; f < floorCount; f++) {
            for (int r = 0; r < FLOOR_SIZE; r++) {
                for (int c = 0; c < FLOOR_SIZE; c++) {
                    building[f][r][c] = false;
                }
            }
        }

        Random rand = new Random();
        int f = rand.nextInt(floorCount);
        int r = rand.nextInt(FLOOR_SIZE);
        int c = rand.nextInt(FLOOR_SIZE);

        building[f][r][c] = true;
    }

    private static void runSearch(int floorCount) {
        FloorSearchThread[] workers = new FloorSearchThread[floorCount];
        
        for (int i = 0; i < floorCount; i++) {
            workers[i] = new FloorSearchThread(i);
            workers[i].start();
        }

        for (int i = 0; i < floorCount; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                return;
            }

            int room = workers[i].getFoundRoom();
            if (room == -1) {
                continue;
            } else {
                System.out.println("Floor " + i + ": Person found in room " + room + ".");

                if (room < 10) {
                    System.err.println(i + "0" + room);
                } else {
                    System.err.println(i + "" + room);
                }

                personFound = true;

                for (int j = 0; j < floorCount; j++) {
                    if (j != i && workers[j].isAlive()) {
                        workers[j].interrupt();
                    }
                }

                System.err.println();
                System.err.println("All remaining searches have been cancelled.");
                break;
            }
        }
    }

    private static class FloorSearchThread extends Thread {
        private final int floor;
        private int foundRoom = -1;

        FloorSearchThread(int floor) {
            this.floor = floor;
        }

        public int getFoundRoom() {
            return foundRoom;
        }

        @Override
        public void run() {
            for (int r = 0; r < FLOOR_SIZE; r++) {
                for (int c = 0; c < FLOOR_SIZE; c++) {
                    if (personFound || isInterrupted()) {
                        System.err.println("Floor " + floor + ": Search interrupted.");
                        return;
                    }

                    int roomNumber = r * FLOOR_SIZE + c;
                    System.err.println("Floor " + floor + ": Searching room " + roomNumber + ".");

                    if (building[floor][r][c]) {
                        foundRoom = roomNumber;
                        personFound = true;
                        return;
                    }
                }
            }

            System.err.println("Floor " + floor + ": Person not found.");
        }
    }
}