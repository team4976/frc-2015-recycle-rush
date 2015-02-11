package ca.team4976.io;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;

public class Output {

    public static enum Motor {
        DRIVE_LEFT_1(0, 1.0, new Talon(0)),
        DRIVE_LEFT_2(1, 1.0, new Talon(1)),
        DRIVE_RIGHT_1(2, 1.0, new Talon(2)),
        DRIVE_RIGHT_2(3, 1.0, new Talon(3)),
        ELEVATOR(11, 0.5, new CANTalon(11)),
        GRIPPER_LEFT(12, 0.5, new CANTalon(12)),
        GRIPPER_RIGHT(13, 0.5,new CANTalon(13));

        public int id;
        public Object motor;

        public double modifier;

        private Motor(int id, double modifier, Object motor) {
            this.id = id;
            this.motor = motor;
            this.modifier = modifier;
        }

        public void set(double speed) {
            if (motor instanceof Talon)
                ((Talon) motor).set(speed * modifier);
            else if (motor instanceof CANTalon)
                ((CANTalon) motor).set(speed * modifier);
        }

        public double getCurrent() {
            if (motor instanceof CANTalon)
                return ((CANTalon) motor).getOutputCurrent();
            return 0;
        }

    }

    public static enum PneumaticSolenoid {
        RAKE_LEFT(0, 1),
        RAKE_RIGHT(6, 7),
        GRIPPER_PNEUMATIC(2, 3),
        GRIPPER_KICKER(4, 5);

        public int port1, port2;
        public DoubleSolenoid solenoid;

        private PneumaticSolenoid(int port1, int port2) {
            this.port1 = port1;
            this.port2 = port2;
            solenoid = new DoubleSolenoid(20, this.port1, this.port2);
        }

        public void set(boolean extend) {
            if (extend)
                set(DoubleSolenoid.Value.kForward);
            else
                set(DoubleSolenoid.Value.kReverse);
        }

        public void set (DoubleSolenoid.Value value) {
            solenoid.set(value);
        }

    }

}
