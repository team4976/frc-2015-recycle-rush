package ca.team4976;

import ca.team4976.in.Controller;
import ca.team4976.out.Output;
import ca.team4976.sub.DriveTrain;
import ca.team4976.sub.Elevator;
import ca.team4976.sub.Gripper;
import ca.team4976.sub.Rake;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    DriveTrain drive;

    Rake rake;
    Gripper gripper;
    Elevator elevator;

    public void robotInit() {
        rake = new Rake();
        //gripper = new Gripper();
        elevator = new Elevator();
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
        //gripper.update();
        elevator.update();
    }

    public void autonomousPeriodic() {

    }

    public void testPeriodic() {

    }

}
