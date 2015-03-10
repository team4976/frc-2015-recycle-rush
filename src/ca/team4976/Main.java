package ca.team4976;

import ca.team4976.io.*;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    public Rake rake;
    public Elevator elevator;
    public GripperV5 gripper;
    public DriveTrain drive;

    public void robotInit() {

        Input.AnalogGyro.DRIVE.gyroInit();
        System.out.println("Gyro has been Initialized");
        Output.Motor.ELEVATOR.enableBrake(true);
        rake = new Rake();
        elevator = new Elevator();
        gripper = new GripperV5();
        drive = new DriveTrain();
    }

    public void autonomousInit() {

        drive.arcadeDrive(0, 0);
        timeFlag = System.currentTimeMillis();
        Input.DigitalEncoder.DRIVE_LEFT.reset();
        Input.AnalogGyro.DRIVE.reset();
        state = 0;
    }

    public void teleopInit() {

        drive.arcadeDrive(0, 0);
        //Input.AnalogGyro.DRIVE.gyroInit();
        drive.useDeadBand = true;
        CustomRobotDrive.DeadBand.setDeadBandType(CustomRobotDrive.DeadBand.EXPONETIAL);
    }

    public void teleopPeriodic() {

        if (Controller.Primary.Button.START.isDownOnce() || Controller.Secondary.Button.START.isDownOnce()) {
            rake.reset();
            gripper.reset();
        }

        rake.update();
        elevator.update();
        gripper.update();

        drive.teleopArcadeDrive();
        drive.updateAutoTurn();

        System.out.println("ground: " + Input.Digital.ELEVATOR_GROUND.get() + " | top: " + Input.Digital.ELEVATOR_TOP.get());
        System.out.println("current level: " + elevator.getCurrentLevel() + " | desired level: " + elevator.getDesiredLevel());
    }

    int state = 0;
    long timeFlag = 0;


    public void autonomousPeriodic() {

        System.out.println("Encoder: " + Input.DigitalEncoder.DRIVE_LEFT.getDistance());

        switch (state) {

            case 0:

                    if (drive.back(80, 0.15) || System.currentTimeMillis() - timeFlag > 1500) {
                        Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; timeFlag = System.currentTimeMillis();
                        Output.PneumaticSolenoid.RAKE.set(true);} break;

            case 1: if (System.currentTimeMillis() - timeFlag > 2000) { state++; } break;

            case 2: if (drive.forward(160, 0.25)) { Input.AnalogGyro.DRIVE.reset(); state++; } break;

            case 3: Output.PneumaticSolenoid.RAKE.set(false); state++; break;

            case 4: if (System.currentTimeMillis() - timeFlag > 2000) { state++; } break;

            case 5: if (drive.turnRight(90, 0.2)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            case 6: if (drive.forward(270, 0.2)) { Input.AnalogGyro.DRIVE.reset(); state++; } break;

            case 7:

                if (drive.turnLeft(90, 0.2)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++;
                timeFlag = System.currentTimeMillis(); } break;

            case 8:

                if (drive.back(180, 0.15) || System.currentTimeMillis() - timeFlag > 8000) {
                    Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; timeFlag = System.currentTimeMillis();
                    Output.PneumaticSolenoid.RAKE.set(true);} break;

            case 9: if (System.currentTimeMillis() - timeFlag > 2000) { state++; } break;

            case 10: if (drive.forward(160, 0.25)) { Input.AnalogGyro.DRIVE.reset(); state++; } break;

            case 11: Output.PneumaticSolenoid.RAKE.set(false); state++; break;

            case 12: if (System.currentTimeMillis() - timeFlag > 2000) { state++; } break;

            default: drive.arcadeDrive(0, 0);
        }


//        switch (state) {
//
//            case 0:
//
//                Output.PneumaticSolenoid.RAKE.set(false);
//                if (drive.back(80, 0.15) || System.currentTimeMillis() - timeFlag > 8000) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
//
//            case 1:  if (drive.forward(20, 0.15)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
//
//            case 2:
//
//                Output.PneumaticSolenoid.RAKE.set(true);
//                if (drive.forward(180, 0.15)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
//
//            case 3: if (drive.turnRight(90, 0.15)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
//            case 4: if (drive.forward(280, 0.2)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
//            case 5: if (drive.turnLeft(90, 0.15)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; timeFlag = System.currentTimeMillis(); } break;
//
//            case 6:
//
//                Output.PneumaticSolenoid.RAKE.set(false);
//                if (drive.back(180, 0.15) /*|| System.currentTimeMillis() - timeFlag > 2000*/) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
//
//            case 7:  if (drive.forward(10, 0.15)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
//
//            case 8:
//
//                Output.PneumaticSolenoid.RAKE.set(true);
//                if (drive.forward(180, 0.15)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;
//
//            default: drive.arcadeDrive(0, 0);
//        }
    }

}
