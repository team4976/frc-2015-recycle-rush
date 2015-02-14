package ca.team4976.io;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;

public class Input {

    public enum Digital {
        ELEVATOR_GROUND(10),
        ELEVATOR_TOP(11);

        private DigitalInput di;

        private Digital(int id) {
            di = new DigitalInput(id);
        }

        public boolean get() {
            return di.get();
        }
    }

    public enum DigitalEncoder {
        DRIVE_LEFT(0, 1, 2, 9.2E-3),
        DRIVE_RIGHT(3, 4, 5, 9.2E-3),
        ELEVATOR(6, 7, 8, 2.3E-4);

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
            distance = value;
        }
        
        public void reset() {
            encoder.reset();
            distance = 0;
        }

    }

    //TODO: Marc fix analogIn port
    public enum AnalogGyro {
        DRIVE(1);

        private Gyro gyro;

        private AnalogGyro(int analogIn) {
            gyro = new Gyro(analogIn);
        }

        public double getAngle() {
            return gyro.getAngle();
        }

        public void reset() {
            gyro.reset();
        }

    }

}
