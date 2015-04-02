package ca.team4976;

import ca.team4976.io.*;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Main extends IterativeRobot {

    public Rake rake;
    public Elevator elevator;
    public Claw claw;
    public DriveTrain drive;

    private boolean fireRakeOnStart;
    private int state = 0;
    private long timeFlag = 0;
    private int maxState = 3;

    private double  stageOneDriveSpeed,         stageOneDriveDistance,  stageOneDriveTimeout;
    private int     stageTwoDelay;
    private double  stageThreeDriveSpeedA,       stageThreeDriveDistanceA;
    private double  stageThreeDriveSpeedB,       stageThreeDriveDistanceB;
    private int     stageFourRakeReturnDelay;
    private double  stageFiveTurnRightSpeed,    stageFiveTurnRightAngle;
    private double  stageSixDriveSpeed,         stageSixDriveDistance;
    private double  stageSevenTurnLeftSpeed,    stageSevenTurnLeftAngle;
    private double  stageEightDriveSpeed,       stageEightDriveDistance;
    private int     stageNineDelay;
    private double  stageTenDriveSpeed,         stageTenDriveDistance;

    NetworkTable table;

    public void robotInit() {

        Output.Motor.ELEVATOR.enableBrake(true);
        Output.Digital.LED.set(true);

        Input.AnalogGyro.DRIVE.gyroInit();
        System.out.println("Gyro has been Initialized");
        Output.Motor.ELEVATOR.enableBrake(true);
        rake = new Rake();
        elevator = new Elevator();
        claw = new Claw();
        drive = new DriveTrain();

        table = NetworkTable.getTable("auto");

        table.putNumber("maxState", 7);
        table.putBoolean("fireRakeOnStart", true);

        table.putNumber("stageOneDriveSpeed", 0.25);
        table.putNumber("stageOneDriveDistance", 120);
        table.putNumber("stageOneDriveTimeout", 1000);
        table.putNumber("stageTwoDelay", 1000);
        table.putNumber("stageThreeDriveSpeedA", 0.1);
        table.putNumber("stageThreeDriveSpeedB", 0.5);
        table.putNumber("stageThreeDriveDistanceA", 10);
        table.putNumber("stageThreeDriveDistanceB", 130);
        table.putNumber("stageFourRakeReturnDelay", 2000);
        table.putNumber("stageFiveTurnRightSpeed", 0.4);
        table.putNumber("stageFiveTurnRightAngle", 90);
        table.putNumber("stageSixDriveSpeed", 0.5);
        table.putNumber("stageSixDriveDistance", 330);
        table.putNumber("stageSevenTurnLeftSpeed", 0.4);
        table.putNumber("stageSevenTurnLeftAngle", 90);
        table.putNumber("stageEightDriveSpeed", 0.15);
        table.putNumber("stageEightDriveDistance", 120);
        table.putNumber("stageNineDelay", 1000);
        table.putNumber("stageTenDriveSpeed", 0.25);
        table.putNumber("stageTenDriveDistance", 120);

    }

    public void autonomousInit() {

        drive.arcadeDrive(0, 0);
        timeFlag = System.currentTimeMillis();
        Input.DigitalEncoder.DRIVE_LEFT.reset();
        Input.AnalogGyro.DRIVE.reset();
        state = 0;

        maxState = (int) table.getNumber("maxState", 4);
        fireRakeOnStart = table.getBoolean("fireRakeOnStart", true);

        stageOneDriveSpeed = table.getNumber("stageOneDriveSpeed", 0.25);
        stageOneDriveDistance = table.getNumber("stageOneDriveDistance", 120);
        stageOneDriveTimeout = table.getNumber("stageOneDriveTimeout", 1000);
        stageTwoDelay = (int) table.getNumber("stageTwoDelay", 1000);
        stageThreeDriveSpeedA = table.getNumber("stageThreeDriveSpeedA", 0.5);
        stageThreeDriveSpeedB = table.getNumber("stageThreeDriveSpeedB", 0.5);
        stageThreeDriveDistanceA = table.getNumber("stageThreeDriveDistanceA", 180);
        stageThreeDriveDistanceB = table.getNumber("stageThreeDriveDistanceB", 180);
        stageFourRakeReturnDelay = (int) table.getNumber("stageFourRakeReturnDelay", 2000);
        stageFiveTurnRightSpeed = table.getNumber("stageFiveTurnRightSpeed", 0.4);
        stageFiveTurnRightAngle = table.getNumber("stageFiveTurnRightAngle", 90);
        stageSixDriveSpeed = table.getNumber("stageSixDriveSpeed", 0.5);
        stageSixDriveDistance = table.getNumber("stageSixDriveDistance", 330);
        stageSevenTurnLeftSpeed = table.getNumber("stageSevenTurnLeftSpeed", 0.4);
        stageSevenTurnLeftAngle = table.getNumber("stageSevenTurnLeftAngle", 90);
        stageEightDriveSpeed = table.getNumber("stageEightDriveSpeed", 0.15);
        stageEightDriveDistance = table.getNumber("stageEightDriveDistance", 120);
        stageNineDelay = (int) table.getNumber("stageNineDelay", 1000);
        stageTenDriveSpeed = table.getNumber("stageTenDriveSpeed", 0.25);
        stageTenDriveDistance = table.getNumber("stageTenDriveDistance", 120);
    }

    public void teleopInit() {

        Input.DigitalEncoder.ELEVATOR.reset();
        drive.arcadeDrive(0, 0);
        drive.useDeadBand = true;
        CustomRobotDrive.DeadBand.setDeadBandType(CustomRobotDrive.DeadBand.EXPONETIAL);
    }

    public void teleopPeriodic() {

//        if (Controller.getResetOnce()) {
//
//            rake.reset();
//            claw.reset();
//        }

        rake.update();
        elevator.update();
        claw.update();

        drive.teleopArcadeDrive();
        drive.updateAutoTurn();
    }


    private void stage() {

       if (state >= maxState)  state = -1;
       else state++;
    }

    public void autonomousPeriodic() {

        System.out.println("Encoder D: " + Input.DigitalEncoder.DRIVE_LEFT.getDistance());
        System.out.println("Gyro: " + Input.AnalogGyro.DRIVE.getAngle());

        switch (state) {

            case 0: Output.PneumaticSolenoid.RAKE.set(fireRakeOnStart); stage(); break;

            case 1:

                    if (drive.back(stageOneDriveDistance, stageOneDriveSpeed) || System.currentTimeMillis() - timeFlag > stageOneDriveTimeout) {
                        Input.DigitalEncoder.DRIVE_LEFT.reset(); stage(); timeFlag = System.currentTimeMillis();
                        Output.PneumaticSolenoid.RAKE.set(true);} break;

            case 2: if (System.currentTimeMillis() - timeFlag > stageTwoDelay) { stage(); } break;

            case 3: if (drive.forward(stageThreeDriveDistanceA, stageThreeDriveSpeedA)) { Input.AnalogGyro.DRIVE.reset(); stage(); } break;

            case 4: if (drive.forward(stageThreeDriveDistanceB, stageThreeDriveSpeedB)) { Input.AnalogGyro.DRIVE.reset(); stage(); } break;

            case 5: Output.PneumaticSolenoid.RAKE.set(false); timeFlag = System.currentTimeMillis(); stage(); break;

            case 6: if (System.currentTimeMillis() - timeFlag > stageFourRakeReturnDelay) { stage(); timeFlag = System.currentTimeMillis(); } break;

            case 7: if (drive.turnRight(stageFiveTurnRightAngle, stageFiveTurnRightSpeed)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); stage(); } break;

            case 8:
                if (drive.forward(stageSixDriveDistance, stageSixDriveSpeed)) { Input.AnalogGyro.DRIVE.reset(); stage(); } break;

            case 9:

                if (drive.turnLeft(stageSevenTurnLeftAngle, stageSevenTurnLeftSpeed)) { Input.DigitalEncoder.DRIVE_LEFT.reset(); stage();
                timeFlag = System.currentTimeMillis(); } break;

            case 10:
                if (drive.back(stageEightDriveDistance, stageEightDriveSpeed) || System.currentTimeMillis() - timeFlag > 4000) {
                    Input.DigitalEncoder.DRIVE_LEFT.reset(); stage(); timeFlag = System.currentTimeMillis();}

            case 11:

                Output.PneumaticSolenoid.RAKE.set(true); stage(); break;

            case 12: if (System.currentTimeMillis() - timeFlag > stageNineDelay) { stage(); } break;

            case 13: if (drive.forward(stageTenDriveDistance, stageTenDriveSpeed)) { Input.AnalogGyro.DRIVE.reset(); stage(); } break;

            case 14: Output.PneumaticSolenoid.RAKE.set(false); stage(); break;

            default: drive.arcadeDrive(0, 0);
        }

        System.out.println(state);
    }
}
