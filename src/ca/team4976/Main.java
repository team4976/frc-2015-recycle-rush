package ca.team4976;

import ca.team4976.io.*;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;
import ca.team4976.io.NetworkVariables.Autonomous;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.io.BufferedWriter;
import java.io.*;

public class Main extends IterativeRobot {

    public Rake rake = new Rake();
    public Elevator elevator = new Elevator();
    public Claw claw = new Claw();
    public DriveTrain drive = new DriveTrain();

    private int currentStage = 0;
    private long timeoutFlag = 0;

    public void robotInit() {

        table.putNumber("kp", 0);
        table.putNumber("ki", 0);
        table.putNumber("kd", 0);

        NetworkVariables.robotInit();
        drive.robotInit();
    }

    public void disabledInit() {

        if (!Input.AnalogGyro.DRIVE.isInitalized()) {

            System.out.println("Running Gyro Init. Expect System hang");
            Input.AnalogGyro.DRIVE.gyroInit();
            System.out.println("Gyro has been initialized");
        }

        drive.disableInit();
    }

    public void autonomousInit() {

        NetworkVariables.autonomousInit();
        drive.autonomousInit();

        currentStage = 0;
        timeoutFlag = System.currentTimeMillis();

        Input.DigitalEncoder.DRIVE_LEFT.reset();
        Input.AnalogGyro.DRIVE.reset();
    }

    public void teleopInit() {

        Input.DigitalEncoder.ELEVATOR.reset();
        drive.teleopInit();
    }

    public void disabledPeriodic() { NetworkVariables.disabledPeriodic(); }

    public void teleopPeriodic() {

        rake.teleopPeriodic();
        elevator.teleopPeriodic();
        claw.teleopPeriodic();
    }

    int id;
    boolean done = false;

    NetworkTable table = NetworkTable.getTable("auto");

    public void autonomousPeriodic() {
        
        if (!done) { drive.addMoveCount(1, 0.1); done = true; }
    }

    public void stage() { if (currentStage < Autonomous.finalStage - 1) currentStage++;}

    public void stage(int stage) { currentStage = stage; }
}
