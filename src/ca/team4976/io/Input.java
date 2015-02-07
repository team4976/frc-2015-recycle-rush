package ca.team4976.io;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;

public class Input {

    public enum Digital {
        ELEVATOR_GROUND(0),
        ELEVATOR_TOP(1),
        CONTAINER_POSITION_LASER(3);

        private DigitalInput di;

        private Digital(int id) {
            di = new DigitalInput(id);
        }

        public boolean get() {
            return di.get();
        }
    }

    //TODO: Marc fix the distancePerPulse values
    public enum DigitalEncoder {
        DRIVE_LEFT(0, 1, 2, 0.1),
        DRIVE_RIGHT(3, 4, 5, 0.1),
        ELEVATOR(6, 7, 8, 1E-10);

        private Encoder encoder;

        private DigitalEncoder(int dio1, int dio2, int dio3, double dpp) {
            encoder = new Encoder(dio1, dio2, dio3);
            encoder.setDistancePerPulse(dpp);
        }

        public double getDistance() {
            return encoder.getDistance();
        }

        public void reset() {
            encoder.reset();
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
