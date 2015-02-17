package ca.team4976;

import ca.team4976.io.*;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    Rake rake;
    Elevator elevator;
    GripperV3 gripper;
    DriveTrain drive;

    public void robotInit() {
        rake = new Rake();
        elevator = new Elevator();
        gripper = new GripperV3();
        drive = new DriveTrain();
    }

    public void autonomousInit() {
    }

    public void teleopInit() {
        Input.AnalogGyro.DRIVE.gyroInit();
        drive.useDeadBand = true;
        CustomRobotDrive.DeadBand.setDeadBandType(CustomRobotDrive.DeadBand.LINEAR);
    }

    public void teleopPeriodic() {
        rake.update();
        elevator.update();
        gripper.update(elevator);

        drive.teleopArcadeDrive();
        drive.updateAutoTurn();

        if (Controller.Primary.Button.START.isDownOnce()) {
            gripper.reset(elevator);
            rake.reset();
        }

        System.out.println("ground: " + Input.Digital.ELEVATOR_GROUND.get() + " | top: " + Input.Digital.ELEVATOR_TOP.get());
        System.out.println("current level: " + elevator.getCurrentLevel() + " | desired level: " + elevator.getDesiredLevel());
    }

    public void autonomousPeriodic() {

    }

}
