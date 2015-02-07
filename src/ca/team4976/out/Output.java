package ca.team4976.out;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Output {

    public static enum Motor {
        DRIVE_LEFT_1(0, new Talon(0)),
        DRIVE_LEFT_2(1, new Talon(1)),
        DRIVE_RIGHT_1(2, new Talon(2)),
        DRIVE_RIGHT_2(3, new Talon(3)),
        ELEVATOR(11, new CANTalon(11)),
        GRIPPER_LEFT(12, new CANTalon(12)),
        GRIPPER_RIGHT(13, new CANTalon(13));

        public int id;
        public Object motor;

        private Motor(int id, Object motor) {
            this.id = id;
            this.motor = motor;
        }

        public void set(double speed) {
            if (motor instanceof Talon)
                ((Talon) motor).set(speed);
            else if (motor instanceof CANTalon)
                ((CANTalon) motor).set(speed);
        }

        public double getCurrent() {
            if (motor instanceof CANTalon)
                return ((CANTalon) motor).getOutputCurrent();
            return 0;
        }

    }

    public static enum PneumaticSolenoid {
        RAKE_LEFT(0),
        RAKE_RIGHT(1),
        GRIPPER_LEFT(2),
        GRIPPER_RIGHT(3);

        public int portID;
        public Solenoid solenoid;

        private PneumaticSolenoid(int portID) {
            this.portID = portID;
            solenoid = new Solenoid(20, this.portID);
        }

        public void set(boolean extend) {
            solenoid.set(extend);
        }

    }

}
