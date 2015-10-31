package ca.team4976;

import ca.team4976.io.*;
import ca.team4976.sub.*;
import edu.wpi.first.wpilibj.*;
import ca.team4976.io.NetworkVariables.Autonomous;

public class Main extends IterativeRobot {

    public Rake rake = new Rake();
    public Elevator elevator = new Elevator();
    public Claw claw = new Claw();
    public DriveTrain drive = new DriveTrain();

    private int currentStage = 0;
    private long timeoutFlag = 0;

    public void robotInit() { NetworkVariables.robotInit(); }

    public void disabledInit() {

        if (!Input.AnalogGyro.DRIVE.isInitalized()) Input.AnalogGyro.DRIVE.gyroInit();

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

    public void autonomousPeriodic() {

        switch (currentStage) {

            
        }
    }

    public void stage() { if (currentStage < Autonomous.finalStage - 1) currentStage++;}

    public void stage(int stage) { currentStage = stage; }
}
