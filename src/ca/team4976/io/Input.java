package ca.team4976.io;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDSource;

public class Input {

    public enum DigitalInput {
        ELEVATOR_GROUND(3),
        ELEVATOR_TOP(4),
        GRIPPER_LASER(12);

        private edu.wpi.first.wpilibj.DigitalInput di;

        private DigitalInput(int id) {
            di = new edu.wpi.first.wpilibj.DigitalInput(id);
        }

        public boolean get() {
            return di.get();
        }
    }

    public enum DigitalOutput {

        LED(5);

        private edu.wpi.first.wpilibj.DigitalOutput di;

        private DigitalOutput(int id) {
            di = new edu.wpi.first.wpilibj.DigitalOutput(id);
        }

        public void set(boolean value) { di.set(value); }
    }

    public enum DigitalEncoder {
        DRIVE_LEFT(0, 1, 2, 0.025),
        //DRIVE_RIGHT(3, 4, 5, 0.025),
        ELEVATOR(6, 7, 8,  2E-1);

        private Encoder encoder;
        private double distance;

        private DigitalEncoder(int dio1, int dio2, int dio3, double dpp) {
            encoder = new Encoder(dio1, dio2, dio3);
            encoder.setDistancePerPulse(dpp);
        }

        public double getDistance() {
            distance += encoder.getDistance();
            encoder.reset();
            return distance;
        }

        public void set(double value) {
            encoder.reset();
            distance = value;
        }
        
        public void reset() {
            encoder.reset();
            distance = 0;
        }

    }

    public enum AnalogGyro implements PIDSource {
        DRIVE(0);

        private Gyro gyro;

        private AnalogGyro(int analogIn) {
            gyro = new Gyro(analogIn);
        }

        public double getAngle() {
            return gyro.getAngle();
        }

        public void gyroInit() { gyro.initGyro(); }

        public void reset() {
            gyro.reset();
        }

        public double getRate() { return gyro.getRate(); }

        @Override
        public double pidGet() { return getAngle(); }
    }

}
