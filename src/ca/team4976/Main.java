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

        drive.arcadeDrive(0, 0);
        Input.AnalogGyro.DRIVE.reset();
        state = 0;
    }

    public void teleopInit() {

        drive.arcadeDrive(0, 0);
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

    int state = 0;

    public void autonomousPeriodic() {

        System.out.println("Encoder: " + Input.DigitalEncoder.DRIVE_LEFT.getDistance());

        switch (state) {

            case 0:

                Output.PneumaticSolenoid.RAKE.set(false);
                if (drive.back(80, 0.15)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            case 1:

                Output.PneumaticSolenoid.RAKE.set(true);
                if (drive.forward(200, 0.15)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            case 2: if (drive.turnRight(90, 0.15)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
            case 3: if (drive.forward(300, 0.4)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
            case 4: if (drive.turnLeft(90, 0.15)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            case 5:

                Output.PneumaticSolenoid.RAKE.set(false);
                if (drive.back(200, 0.15)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            case 7:

                Output.PneumaticSolenoid.RAKE.set(true);
                if (drive.forward(400, 0.15)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            default: drive.arcadeDrive(0, 0);
        }
    }

}
