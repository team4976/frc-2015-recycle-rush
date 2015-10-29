package ca.team4976;

import ca.team4976.io.*;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import ca.team4976.sub.NetworkVariables.Autonomous;

public class Main extends IterativeRobot {

    public Rake rake;
    public Elevator elevator;
    public Claw claw;
    public DriveTrain drive;

    private int currentStage = 0;
    private long timoutFlag = 0;

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

        new Thread(new NetworkVariables()).start();
        drive.thread.start();
    }

    public void disabledInit() {

        if (!Input.AnalogGyro.DRIVE.isInitalized()) Input.AnalogGyro.DRIVE.gyroInit();

        drive.disable();
    }

    public void autonomousInit() {

        drive.autonomousInit();

        currentStage = 0;
        timoutFlag = System.currentTimeMillis();

        Input.DigitalEncoder.DRIVE_LEFT.reset();
        Input.AnalogGyro.DRIVE.reset();
    }

    public void teleopInit() {

        Input.DigitalEncoder.ELEVATOR.reset();
        drive.arcadeDrive(0, 0);
        drive.useDeadBand = true;
        drive.teleopInit();
    }

    public void teleopPeriodic() {

        rake.update();
        elevator.update();
        claw.update();
    }

    public void autonomousPeriodic() {

        switch (currentStage) {

            case 0:
                drive.setMoveCount(Autonomous.stageDistance[0], Autonomous.stageSpeed[0]);
                timoutFlag = System.currentTimeMillis();
                stage(); break;

            case 1:
                if (drive.isMoveComplete() ||
        }
    }

    public void stage() { if (currentStage < Autonomous.finalStage - 1) currentStage++;}
}
