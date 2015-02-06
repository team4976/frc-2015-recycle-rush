package ca.team4976.sub;

import ca.team4976.out.Motors;

public class CustomRobotDrive {

    public boolean useDeadBand = false;

    public void arcadeDrive(double steering, double throttle) {

        Motors.pwmMotors[0].set(throttle + steering);
        Motors.pwmMotors[1].set(throttle + steering);
        Motors.pwmMotors[2].set(-throttle - steering);
        Motors.pwmMotors[3].set(-throttle - steering);
    }

    public double ramp(double speed, double error) {

        if (error > 45) return speed;

        else return speed * Math.sqrt(error);
    }

    public static enum DeadBand {

        LINEAR,
        CONCENTRIC,
        CONCENTRIC_ADVANCED;

        public static DeadBand DEADBANDTYPE = DeadBand.LINEAR;
        public static double deadBandZone = 0.2;

        public static void setDeadBandType(DeadBand deadBandType) { DEADBANDTYPE = deadBandType; }

        public static void setDeadBandZone(double size) { deadBandZone = size; }

        public static double[] evaluteDeadBand(double x, double y) {

            switch (DEADBANDTYPE) {

                case LINEAR:

                    if (x > -deadBandZone && x < deadBandZone) x = 0;
                    if (y > -deadBandZone && y < deadBandZone) y = 0;

                case CONCENTRIC:

                    if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < deadBandZone) { x = 0; y = 0; }

                    return new double[] {x, y};

                case CONCENTRIC_ADVANCED:

                    if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < deadBandZone) { x = 0; y = 0; }

                    if (x > -deadBandZone && x < deadBandZone) x = 0;
                    if (y > -deadBandZone && y < deadBandZone) y = 0;

                    return new double[] {x, y};

                default: return new double[] { 0, 0 };
            }
        }
    }
}
