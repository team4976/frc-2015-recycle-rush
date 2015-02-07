package ca.team4976.in;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {

    private static Joystick joystick;

    public static enum Button {
        A(1),
        B(2),
        X(3),
        Y(4),
        LEFT_BUMPER(5),
        RIGHT_BUMPER(6),
        BACK(7),
        START(8),
        LEFT_STICK(9),
        RIGHT_STICK(10);

        private int port;
        private boolean wasReleased;

        private Button(int port) {
            this.port = port;
            wasReleased = true;
        }

        public boolean isDown() {
            return joystick.getRawButton(port);
        }

        public boolean isDownOnce() {
            if (wasReleased && isDown()) {
                wasReleased = false;
                return true;
            } else if (!isDown())
                wasReleased = true;

            return false;
        }
    }

    public static enum Trigger {
        LEFT(2),
        RIGHT(3);

        public int axis;

        private Trigger(int axis) {
            this.axis = axis;
        }

        public double value() {
            return joystick.getRawAxis(axis);
        }

        public static double totalValue(Trigger one, Trigger two) {
            return (joystick.getRawAxis(one.axis) - joystick.getRawAxis(two.axis));
        }

        public double value(double min, double max) {
            double value = joystick.getRawAxis(axis);
            if (value > min && value < max)
                return 0;
            return value;
        }

        public static double totalValue(Trigger one, Trigger two, double min, double max) {
            double value = (joystick.getRawAxis(one.axis) - joystick.getRawAxis(two.axis));
            if (value > min && value < max)
                return 0;
            return value;
        }
    }

    public static enum Stick {
        LEFT(0, 1),
        RIGHT(4, 5);

        public int hAxis, vAxis;

        private Stick(int hAxis, int vAxis) {
            this.hAxis = hAxis;
            this.vAxis = vAxis;
        }

        public double horizontal() {
            return joystick.getRawAxis(hAxis);
        }

        public double vertical() {
            return joystick.getRawAxis(vAxis);
        }

        public double horizontal(double min, double max) {
            double value = joystick.getRawAxis(hAxis);
            if (value > min && value < max)
                return 0;
            return value;
        }

        public double vertical(double min, double max) {
            double value = joystick.getRawAxis(vAxis);
            if (value > min && value < max)
                return 0;
            return value;
        }

    }

    public static enum DPad {
        NOT(-1),
        NORTH(0),
        NORTH_EAST(45),
        EAST(90),
        SOUTH_EAST(135),
        SOUTH(180),
        SOUTH_WEST(225),
        WEST(270),
        NORTH_WEST(315);

        public int angle;
        public boolean wasReleased;

        private DPad(int angle) {
            this.angle = angle;
            wasReleased = true;
        }

        public boolean isDown() {
            return isDown(0);
        }

        public boolean isDown(int port) {
            return (joystick.getPOV(port) == angle);
        }

        public boolean isDownOnce() {
            return isDownOnce(0);
        }

        public boolean isDownOnce(int port) {
            if (wasReleased && isDown(port)) {
                wasReleased = false;
                return true;
            } else if (!isDown(port))
                wasReleased = true;

            return false;
        }

    }

}
