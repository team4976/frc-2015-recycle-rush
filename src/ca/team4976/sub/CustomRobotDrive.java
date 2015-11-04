package ca.team4976.sub;

import ca.team4976.io.Output;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CustomRobotDrive {

    public void arcadeDrive(double steering, double throttle) {

        Output.Motor.DRIVE_LEFT_1.set(throttle + steering);
        Output.Motor.DRIVE_LEFT_2.set(throttle + steering);
        Output.Motor.DRIVE_RIGHT_1.set(-throttle + steering);
        Output.Motor.DRIVE_RIGHT_2.set(-throttle + steering);
    }

    public void directDrive(double left, double right) {

        Output.Motor.DRIVE_LEFT_1.set(left);
        Output.Motor.DRIVE_LEFT_2.set(left);
        Output.Motor.DRIVE_RIGHT_1.set(-right);
        Output.Motor.DRIVE_RIGHT_2.set(-right);
    }

    public class PID {

        double kp, ki, kd;

        NetworkTable table = NetworkTable.getTable("auto");

        long startTimeMillis = 0;
        double previousError = 0;
        double i = 0;

        public PID(double kp, double ki, double kd) { this.kp = kp; this.ki = ki; this.kd = kd; }

        public void setConstants(double kp, double ki, double kd) { this.kp = kp; this.ki = ki; this.kd = kd; }

        public double getPID(double error, double speed) {

            kp = table.getNumber("kp", 0);
            ki = table.getNumber("ki", 0);
            kd = table.getNumber("kd", 0);

            if (startTimeMillis == 0) startTimeMillis = System.currentTimeMillis();

            double dt = ((System.currentTimeMillis() - startTimeMillis) / 1000);

            i += error * dt;
            double d = (error - previousError) / dt;

            previousError = error;

            double returnVal = ((error * kp) + (i * ki) + (d * kd));

            return returnVal > speed ? speed : returnVal;
        }

        public void reset() {

            startTimeMillis = 0;
            previousError = 0;
            i = 0;
        }
    }
}
