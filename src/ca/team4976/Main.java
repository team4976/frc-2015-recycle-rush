package ca.team4976;

import ca.team4976.io.Input;
import ca.team4976.io.Output;
import ca.team4976.sub.Elevator;
import ca.team4976.sub.Gripper;
import ca.team4976.sub.Rake;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    Rake rake;
    Gripper gripper;
    Elevator elevator;

    public void robotInit() {
        rake = new Rake();
        //gripper = new Gripper();
        //elevator = new Elevator();
    }

    public void teleopInit() {

    }

    public void autonomousInit() {

    }

    public void teleopPeriodic() {
        rake.update();
        //gripper.update();
        //elevator.update();
    }

    public void autonomousPeriodic() {

    }

}
