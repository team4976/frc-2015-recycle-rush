package ca.team4976.in;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalInputs {

    public static final int ELEVATOR_GROUND = 0;
    public static final int ELEVATOR_A2B = 1;

    public static final int ELEVATOR_TEMP = 2;

    public static final DigitalInput[] inputs = new DigitalInput[3];

    public static void setDefaultInputs() {
        setInputs(ELEVATOR_GROUND, ELEVATOR_A2B, ELEVATOR_TEMP);
    }

    public static void setInputs(int di1, int di2, int di3) {
        inputs[di1] = new DigitalInput(di1);
        inputs[di2] = new DigitalInput(di2);
        inputs[di3] = new DigitalInput(di3);
    }

}
