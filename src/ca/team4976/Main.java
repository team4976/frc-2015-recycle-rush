package ca.team4976;

import ca.team4976.io.*;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import sun.nio.ch.Net;

public class Main extends IterativeRobot {

    public Rake rake;
    public Elevator elevator;
    public GripperV5 gripper;
    public DriveTrain drive;

    NetworkTable table;

    public void robotInit() {

        Output.Motor.ELEVATOR.enableBrake(true);
        Input.AnalogGyro.DRIVE.gyroInit();
        System.out.println("Gyro has been Initialized");
        Output.Motor.ELEVATOR.enableBrake(true);
        rake = new Rake();
        elevator = new Elevator();
        gripper = new GripperV5();
        drive = new DriveTrain();

        table = NetworkTable.getTable("auto");
        table.putNumber("maxState", 4);
    }

    public void autonomousInit() {

        drive.arcadeDrive(0, 0);
        timeFlag = System.currentTimeMillis();
        Input.DigitalEncoder.DRIVE_LEFT.reset();
        Input.AnalogGyro.DRIVE.reset();
        state = 0;
        maxState = (int) table.getNumber("maxState", 4);
    }

    public void teleopInit() {

        Input.DigitalEncoder.ELEVATOR.reset();
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

        System.out.println("Top: " + Input.DigitalInput.ELEVATOR_TOP.get());
        System.out.println("Ground: " + Input.DigitalInput.ELEVATOR_GROUND.get());
        //System.out.println("ground: " + Input.DigitalInput.ELEVATOR_GROUND.get() + " | top: " + Input.DigitalInput.ELEVATOR_TOP.get());
        //System.out.println("current level: " + elevator.getCurrentLevel() + " | desired level: " + elevator.getDesiredLevel());
    }

    int state = 0;
    long timeFlag = 0;
    int maxState = 3;

    private void stage() {

       if (state >= maxState)  state = -1;
       else state++;
    }

    public void autonomousPeriodic() {

        System.out.println("Encoder D: " + Input.DigitalEncoder.DRIVE_LEFT.getDistance());
        System.out.println("Gyro: " + Input.AnalogGyro.DRIVE.getAngle());

        switch (state) {

            case 0: Output.PneumaticSolenoid.RAKE.set(false); stage(); break;

            case 1:

                    if (drive.back(120, 0.15) || System.currentTimeMillis() - timeFlag > 2000) {
                        Input.DigitalEncoder.DRIVE_LEFT.reset(); stage(); timeFlag = System.currentTimeMillis();
                        Output.PneumaticSolenoid.RAKE.set(true);} break;

            case 2: if (System.currentTimeMillis() - timeFlag > 5000) { stage(); } break;

            case 3: if (drive.forward(230, 0.25)) { Input.AnalogGyro.DRIVE.reset(); stage(); } break;

            case 4: Output.PneumaticSolenoid.RAKE.set(false); stage(); break;

            case 5: if (System.currentTimeMillis() - timeFlag > 2000) { stage(); timeFlag = System.currentTimeMillis(); } break;

            case 6: if (drive.turnRight(90, 0.2) || System.currentTimeMillis() - timeFlag > 2000) { Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            case 7: if (drive.forward(240, 0.2)) { Input.AnalogGyro.DRIVE.reset(); stage(); } break;

            case 8:

                if (drive.turnLeft(90, 0.2)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); stage();
                timeFlag = System.currentTimeMillis(); } break;

            case 9:

                if (drive.back(180, 0.15) || System.currentTimeMillis() - timeFlag > 8000) {
                    Input.DigitalEncoder.DRIVE_LEFT.reset(); stage(); timeFlag = System.currentTimeMillis(); } break;

            case 10: if (System.currentTimeMillis() - timeFlag > 2000) { stage(); } break;

            case 11: if (drive.forward(160, 0.25)) { Input.AnalogGyro.DRIVE.reset(); stage(); } break;

            case 12: Output.PneumaticSolenoid.RAKE.set(false); stage(); break;

            case 13: if (System.currentTimeMillis() - timeFlag > 2000) { stage(); } break;

            default: drive.arcadeDrive(0, 0);
        }
    }
}
