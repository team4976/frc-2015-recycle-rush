package ca.team4976;

import ca.team4976.in.DigitalInputs;
import ca.team4976.in.Encoders;
import ca.team4976.in.Gyros;
import ca.team4976.in.Controller;
import ca.team4976.out.Motors;
import ca.team4976.sub.DriveTrain;
import ca.team4976.sub.Elevator;
import ca.team4976.sub.Gripper;
import ca.team4976.sub.Rake;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    DriveTrain drive;

    Elevator elevator;
    Gripper gripper;
    Rake rake;

    public void robotInit() {

        Motors.setDefaultMotors();

        DigitalInputs.setDefaultInputs();

        Controller.setInputPort(0);

        elevator = new Elevator();
        rake = new Rake(20, 0, 1);
        gripper = new Gripper(20, 2, 3, Motors.GRIPPER_MOTOR_LEFT, Motors.GRIPPER_MOTOR_RIGHT);
    }

    public void disabledInit() {

    }

    public void teleopInit() {
    }

    public void autonomousInit() {
    }

    public void testInit() {

    }

    public void disabledPeriodic() {
    }

    public void teleopPeriodic() {
        rake.update();
        gripper.update();
        elevator.update();
    }

    public void autonomousPeriodic() {

    }

    public void testPeriodic() {

    }

}
