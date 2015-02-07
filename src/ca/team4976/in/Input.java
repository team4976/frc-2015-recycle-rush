package ca.team4976.in;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class Input {

    //TODO: Encoders and gyros
    public enum Digital {
        ELEVATOR_GROUND(0),
        ELEVATOR_A2B(1);

        public int id;
        public DigitalInput di;

        private Digital(int id) {
            this.id = id;
            di = new DigitalInput(this.id);
        }

        public boolean get() {
            return di.get();
        }
    }

    public enum DigitalEncoder {
        ELEVATOR(6, 7, 8, 1);

        public int di1, di2, di3;
        public Encoder encoder;

        private DigitalEncoder(int di1, int di2, int di3, double dpp) {
            this.di1 = di1;
            this.di2 = di2;
            this.di3 = di3;
            encoder = new Encoder(this.di1, this.di2, this.di3);
            encoder.setDistancePerPulse(dpp);
        }

        public int get() {
            return encoder.get();
        }

    }

}
