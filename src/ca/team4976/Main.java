package ca.team4976;

import ca.team4976.io.Input;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    Rake rake;
    Elevator elevator;
    GripperV2 gripper;
    DriveTrain drive;

    public void robotInit() {
        rake = new Rake();
        elevator = new Elevator();
        gripper = new GripperV2();
        drive = new DriveTrain();
    }

    public void teleopInit() {

    }

    public void autonomousInit() {

    }

    public void teleopPeriodic() {
        rake.update();
        elevator.update();
        gripper.update(elevator);
        drive.teleopArcadeDrive();
        System.out.println("ground: " + Input.Digital.ELEVATOR_GROUND.get() + " | top: " + Input.Digital.ELEVATOR_TOP.get());
        System.out.println("current level: " + elevator.getCurrentLevel() + " | desired level: " + elevator.getDesiredLevel());
    }

    public void autonomousPeriodic() {

    }

}
