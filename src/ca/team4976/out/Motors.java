package ca.team4976.out;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Talon;

public class Motors {

    public static final int DRIVE_MOTOR_1 = 0;
    public static final int DRIVE_MOTOR_2 = 1;
    public static final int DRIVE_MOTOR_3 = 2;
    public static final int DRIVE_MOTOR_4 = 3;

    public static final int ELEVATOR_MOTOR = 4;

    public static final int GRIPPER_MOTOR_LEFT = 5;
    public static final int GRIPPER_MOTOR_RIGHT = 6;

    public static final Talon[] pwmMotors = new Talon[4];
    public static final CANTalon[] canMotors = new CANTalon[3];

    public static void setDefaultMotors() {
        setMotors(DRIVE_MOTOR_1, DRIVE_MOTOR_2, DRIVE_MOTOR_3, DRIVE_MOTOR_4, ELEVATOR_MOTOR, GRIPPER_MOTOR_LEFT, GRIPPER_MOTOR_RIGHT);
    }

    public static void setMotors(int pwm1, int pwm2, int pwm3, int pwm4, int can1, int can2, int can3) {
        pwmMotors[pwm1] = new Talon(pwm1);
        pwmMotors[pwm2] = new Talon(pwm2);
        pwmMotors[pwm3] = new Talon(pwm3);
        pwmMotors[pwm4] = new Talon(pwm4);

        canMotors[can1] = new CANTalon(can1);

        canMotors[can2] = new CANTalon(can2);
        canMotors[can3] = new CANTalon(can3);
    }

}
