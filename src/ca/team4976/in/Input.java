package ca.team4976.in;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

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

    public enum DigitalEncoder {
        ELEVATOR(6, 7, 8, 1E-10);

        private Encoder encoder;

        private DigitalEncoder(int dio1, int dio2, int dio3, double dpp) {
            encoder = new Encoder(dio1, dio2, dio3);
            encoder.setDistancePerPulse(dpp);
        }

        public int get() {
            return encoder.get();
        }

        public void reset() {
            encoder.reset();
        }

    }

}
