package ca.team4976;

import ca.team4976.in.Controller;
import ca.team4976.sub.Elevator;
import ca.team4976.sub.Gripper;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Main extends IterativeRobot {

    Elevator elevator;
    Gripper gripper;

    public void robotInit() {
        Controller.init(1);
        elevator = new Elevator();
        gripper = new Gripper();
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
        elevator.checkController();
    }

    public void autonomousPeriodic() {

    }

    public void testPeriodic() {

    }

}
