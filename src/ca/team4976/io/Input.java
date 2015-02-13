package ca.team4976.io;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;

public class Input {

    public enum Digital {
        ELEVATOR_GROUND(4),
        ELEVATOR_TOP(5);
        //CONTAINER_POSITION_LASER(9);

        private DigitalInput di;

        private Digital(int id) {
            di = new DigitalInput(id);
        }

        public boolean get() {
            return di.get();
        }
    }

    public enum DigitalEncoder {
        //DRIVE_LEFT(0, 1, 2, 9.2E-3),
        //DRIVE_RIGHT(3, 4, 5, 9.2E-3),
        ELEVATOR(6, 7, 8, 1);

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
