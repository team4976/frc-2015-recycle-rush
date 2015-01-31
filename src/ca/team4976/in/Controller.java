package ca.team4976.in;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {

    private static Joystick joystick;

    public static enum Button {
        _360_A(1),
        _360_B(2),
        _360_X(3),
        _360_Y(4),
        _360_LEFT_BUMPER(5),
        _360_RIGHT_BUMPER(6),
        _360_BACK(7),
        _360_SELECT(8),
        _360_LEFT_STICK(9),
        _360_RIGHT_STICK(10);

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
        _360_LEFT(2),
        _360_RIGHT(3);

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
        _360_LEFT(0, 1),
        _360_RIGHT(4, 5);

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

    public static void init(int joyPort) {
        joystick = new Joystick(joyPort);
    }

}
