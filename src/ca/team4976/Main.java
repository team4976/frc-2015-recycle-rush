package ca.team4976;

import ca.team4976.in.AdvancedEncoder;
import ca.team4976.in.AdvancedGyro;
import ca.team4976.in.Controller;
import ca.team4976.out.AdvancedMotorController;
import ca.team4976.sub.DriveTrain;
import ca.team4976.sub.Elevator;
import ca.team4976.sub.Gripper;
import ca.team4976.sub.Rake;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    Elevator elevator;
    Gripper gripper;
    DriveTrain drive;
    Rake rake;

    public void robotInit() {

        AdvancedGyro.gyros[1] = new Gyro(1);

        AdvancedEncoder.encoders[0] = new Encoder(0, 1, 2);
        AdvancedEncoder.encoders[1] = new Encoder(3, 4, 5);

        AdvancedMotorController.motors[0] = new Talon(0);
        AdvancedMotorController.motors[1] = new Talon(1);
        AdvancedMotorController.motors[2] = new Talon(2);
        AdvancedMotorController.motors[3] = new Talon(3);

        Controller.setInputPort(0);
        //elevator = new Elevator();
        //gripper = new Gripper();
        rake = new Rake();
    }

    public void disabledInit() {

    }

    public void teleopInit() {

        AdvancedGyro.gyros[1].reset();
        AdvancedEncoder.encoders[0].reset();
    }

    public void autonomousInit() {
    }

    public void testInit() {

    }

    public void disabledPeriodic() {
    }

    public void teleopPeriodic() {
        rake.update();
    }

    public void autonomousPeriodic() {

    }

    public void testPeriodic() {

    }

}
