package ca.team4976.io;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.io.*;
import java.util.ArrayList;

import static ca.team4976.io.NetworkVariables.Autonomous.*;

public class NetworkVariables {

    private static NetworkTable table = NetworkTable.getTable("Autonomous");

    public static void robotInit() {

        read(System.getProperty("user.home") + "/autonomous.conf");

        for(int i = 0; i < stageCount.length; i++) {

            table.putNumber("Stage_Count_" + i, stageCount[i]);
            table.putNumber("Stage_Speed_" + i, stageSpeed[i]);
            table.putNumber("Stage_Timeout_" + i, stageTimeout[i]);
        }
    }

    public static void autonomousInit() { write("autonomous_auto_save.conf"); }

    public static void disabledPeriodic() {

        for(int i = 0; i < stageCount.length; i++) {

            stageCount[i] = table.getNumber("Stage_Count_" + i, stageCount[i]);
            stageSpeed[i] = table.getNumber("Stage_Speed_" + i, stageSpeed[i]);
            stageTimeout[i] = table.getNumber("Stage_Timeout_" + i, stageTimeout[i]);
        }
    }

    public static void read(String file) {

        try {

            ArrayList<double[]> list = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                if (!line.startsWith("#")) {

                    if (line.startsWith("Stage")) {

                        line = line.substring(6);

                        int i = Integer.parseInt(line.substring(0, line.indexOf(' ')));

                        double c, s, t;

                        if (line.substring(line.indexOf("C")).contains(","))
                            c = Double.parseDouble(line.substring(line.indexOf("C") + 2,
                                    line.indexOf(",", line.indexOf("C"))));

                        else c = Double.parseDouble(line.substring(line.indexOf("C") + 2));

                        if (line.substring(line.indexOf("S")).contains(","))
                            s = Double.parseDouble(line.substring(line.indexOf("S") + 2,
                                    line.indexOf(",", line.indexOf("S"))));

                        else s = Double.parseDouble(line.substring(line.indexOf("S") + 2));

                        if (line.substring(line.indexOf("T")).contains(","))
                            t = Double.parseDouble(line.substring(line.indexOf("T") + 2,
                                    line.indexOf(",", line.indexOf("T"))));

                        else t = Double.parseDouble(line.substring(line.indexOf("T") + 2));

                        if (i > list.size() + 1) for (; i != list.size() + 1;) list.add(new double[3]);

                        list.add(new double[] {c, s, t});

                    } else System.out.println("file " + file + " isn't a properly formatted");
                }
            }

            stageCount = new double[list.size()];
            stageSpeed = new double[list.size()];
            stageTimeout = new double[list.size()];

            for (int i = 0; i < list.size(); i++) {

                stageCount[i] = list.get(i)[0];
                stageSpeed[i] = list.get(i)[1];
                stageTimeout[i] = list.get(i)[2];
            }

        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void write(String file) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));

            writer.write("# This File Contains The Autonomous Run Configuration #\n");
            writer.write("# Auto-save configuration via NetworkTable            #\n");
            writer.write("# # # # # # # # # # # # # # # # # # # # # # # # # # # #\n");

            for (int i = 0; i < stageCount.length; i++) {

                if (stageCount[i] != 0 || stageSpeed[i] != 0 || stageTimeout[i] != 0) {

                    writer.write("Stage " + i + " ");
                    writer.write("C=" + stageCount[i] + ", ");
                    writer.write("S=" + stageSpeed[i] + ", ");
                    writer.write("T=" + stageTimeout[i] + "\n");
                }
            }

            writer.close();

        } catch (IOException e) { e.printStackTrace(); }
    }

    public static class Autonomous {

        public static int finalStage = 12;

        public static double[] stageCount = new double[finalStage]; // In Meters
        public static double[] stageSpeed = new double[finalStage]; // In Decimal Percent
        public static double[] stageTimeout = new double[finalStage]; // In Seconds
    }
}
