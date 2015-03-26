package ca.team4976.sub;

import ca.team4976.io.Input;
import ca.team4976.io.Output;

/**
* @author Marc Levesque
* @version 1.1.1
*/
public class CustomRobotDrive {

    public boolean useDeadBand = false; // boolean to determine is dead band is in use.

    /**
     * @param steering takes a value from -1 to 1 and determines the difference at which the wheels spin
     * @param throttle takes a value from -1 to 1 and determines the speed at which the wheels spin
     */
    public void arcadeDrive(double steering, double throttle) {

        Output.Motor.DRIVE_LEFT_1.set(throttle + steering);
        Output.Motor.DRIVE_LEFT_2.set(throttle + steering);
        Output.Motor.DRIVE_RIGHT_1.set(-throttle + steering);
        Output.Motor.DRIVE_RIGHT_2.set(-throttle + steering);
    }

    /**
     * @param speed takes a value from 0 to 1 to finalize the output speed.
     * @param error take a value of the distance to target.
     *
     * @return is a value of speed based on the error and max speed.
     */
    public double ramp(double speed, double error) {

        if (error > 45) return speed; // returns speed if error is greater then 45 cm/degrees.

        double rampedValue = speed * error / 45;

        if (rampedValue < 0.1) rampedValue = 0.1;

        return rampedValue; // makes error a value from 0 - 1 then multiples the square root to speed.
    }

    public double frictionRamp() {

        return 5 + 10 * Input.AnalogGyro.DRIVE.getRate() / 130;
    }


    /**
     * DeadBand enum contains methods for evaulating deadband and setting the deadband type.
     */
    public static enum DeadBand {

        LINEAR,
        CONCENTRIC,
        CONCENTRIC_ADVANCED,
        EXPONETIAL;

        public static DeadBand TYPE = DeadBand.LINEAR;
        public static double deadBandZone = 0.2;


        /**
         * @param deadBandType is the Type of dead band to be used.
         *
         *        LINEAR: if and axis is within the dead band it gets reset to 0.
         *        CONCENTRIC: if the parabola of both axis' are within the dead band both axis' get reset to 0.
         *        CONCENTRIC_ADVANCED: first we check CONCENTRIC then we CHECK LINEAR.
         */
        public static void setDeadBandType(DeadBand deadBandType) {
            TYPE = deadBandType;
        }

        /**
         * @param size is a value from 0 - 1 of the zone at which the deadband will be applied.
         */
        public static void setDeadBandZone(double size) { deadBandZone = size;  }


        /**
         * @param x is the first axis with values from -1 to 1.
         * @param y is the second axis with values from -1 to 1.
         *
         * @return is both axis combined and reset to zero if applicable.
         */
        public static double[] evaluateDeadBand(double x, double y) {

            switch (TYPE) { // checks which type of deadband we are using

                case LINEAR:

                    if (x > -deadBandZone && x < deadBandZone) x = 0; // resets x to 0 if its inside the bounds.
                    if (y > -deadBandZone && y < deadBandZone) y = 0; // resets y to 0 if its inside the bounds.

                case CONCENTRIC:

                    if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < deadBandZone) { // if the parabola of axis x and y are within the bounds reset both to 0.
                        x = 0;
                        y = 0;
                    }

                    return new double[]{x, y};

                case CONCENTRIC_ADVANCED:

                    if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < deadBandZone) { // if the parabola of axis x and y are within the bounds reset both to 0.
                        x = 0;
                        y = 0;
                    }

                    if (x > -deadBandZone && x < deadBandZone) x = 0; // resets x to 0 if its inside the bounds.
                    if (y > -deadBandZone && y < deadBandZone) y = 0; // resets y to 0 if its inside the bounds.

                    return new double[]{x, y};

                case EXPONETIAL:

                    return new double[] {Math.pow(x, 2), y};

                default:
                    return new double[]{0, 0};
            }
        }
    }
}
