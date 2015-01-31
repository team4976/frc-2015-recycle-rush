package ca.team4976;

import ca.team4976.in.Controller;
import ca.team4976.sub.Elevator;
import ca.team4976.sub.Gripper;
import ca.team4976.sub.Rake;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Main extends IterativeRobot {

    Elevator elevator;
    Gripper gripper;
    Rake rake;

    public void robotInit() {
        Controller.init(1);
        elevator = new Elevator();
        gripper = new Gripper();
        rake = new Rake();
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
        elevator.update();
        gripper.update();
        rake.update();
    }

    public void autonomousPeriodic() {

    }

    public void testPeriodic() {

    }

}
