package ca.team4976;

import ca.team4976.io.*;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    public Rake rake;
    public Elevator elevator;
    public Gripper gripper;
    public DriveTrain drive;

    public void robotInit() {
        rake = new Rake();
        elevator = new Elevator();
        gripper = new Gripper();
        drive = new DriveTrain();
    }

    public void autonomousInit() {
    }

    public void teleopInit() {
        Input.AnalogGyro.DRIVE.gyroInit();
        drive.useDeadBand = true;
        CustomRobotDrive.DeadBand.setDeadBandType(CustomRobotDrive.DeadBand.EXPONETIAL);
    }

    public void teleopPeriodic() {

        if (Controller.Primary.Button.START.isDownOnce() || Controller.Secondary.Button.START.isDownOnce()) {
            rake.reset();
            gripper.reset(elevator);
        }

        rake.update();
        elevator.update();
        gripper.update(elevator);

        drive.teleopArcadeDrive();
        drive.updateAutoTurn();

        System.out.println("ground: " + Input.Digital.ELEVATOR_GROUND.get() + " | top: " + Input.Digital.ELEVATOR_TOP.get());
        System.out.println("current level: " + elevator.getCurrentLevel() + " | desired level: " + elevator.getDesiredLevel());
    }

    public void autonomousPeriodic() {

    }

}
