package ca.team4976;

import ca.team4976.in.*;
import ca.team4976.sub.Elevator;
import ca.team4976.sub.Gripper;
import ca.team4976.sub.Rake;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    Elevator elevator;
    Gripper gripper;
    Rake rake;

    public void robotInit() {
        rake = new Rake();
        gripper = new Gripper();
        elevator = new Elevator();
        Controller.Button.A.isDown();
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
        //rake.update();
        //gripper.update();
        elevator.update();
    }

    public void autonomousPeriodic() {

    }

    public void testPeriodic() {

    }

}
