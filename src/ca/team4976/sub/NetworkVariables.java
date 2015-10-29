package ca.team4976.sub;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.io.*;

import static ca.team4976.sub.NetworkVariables.Autonomous.*;

public class NetworkVariables implements Runnable {

    public static void robotInit() {

        NetworkTable table = NetworkTable.getTable("Autonomous");

        for (int i = 0; i < finalStage; i++) {

            try {

                BufferedReader reader = new BufferedReader(new FileReader(new File("/Autonomous.txt")));

                String line = reader.readLine();

                stageDistance[i] = Double.parseDouble(line.substring(0, line.indexOf(" ")));
                stageSpeed[i] = Double.parseDouble(line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" ")));
                stageTimeout[i] = Double.parseDouble(line.substring(line.lastIndexOf(" ") + 1));

            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void run() {

        while (true) {

            try {

                Thread.sleep(1000 * 60 * 15);

                save("/AutoSaveAutonomous.txt");

            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static void save(String file) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

            for (int i = 0; i < Autonomous.finalStage; i++)
                writer.write(stageDistance[i] + " " + stageSpeed[i] + " " + stageTimeout[0]);

            writer.close();

        } catch (IOException e) { e.printStackTrace(); }
    }

    public static class Autonomous {

        public static int finalStage = 0;

        public static double[] stageDistance = new double[finalStage]; // In Meters
        public static double[] stageSpeed = new double[finalStage]; // In Decimal Percent
        public static double[] stageTimeout = new double[finalStage]; // In Seconds
    }
}
