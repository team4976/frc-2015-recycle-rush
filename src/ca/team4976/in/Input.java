package ca.team4976.in;

import edu.wpi.first.wpilibj.DigitalInput;

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

    public enum Analog {

    }

}
