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

    public void teleopPeriodic() {

        rake.teleopPeriodic();
        elevator.teleopPeriodic();
        claw.teleopPeriodic();
    }

    int id;

    public void autonomousPeriodic() {

        switch (currentStage) {

            case 0:
                Output.PneumaticSolenoid.RAKE.set(true);
                id = drive.addMoveCount(Autonomous.stageCount[0], Autonomous.stageSpeed[0]);
                timeoutFlag = System.currentTimeMillis();
                stage(); break;

            case 1:
                if (drive.isLastMoveComplete())
                    stage();

                else if (System.currentTimeMillis() - timeoutFlag > Autonomous.stageTimeout[0] * 1000) {

                    drive.safety.stopMove(id);
                    stage();
                }

                break;

            case 2:
                timeoutFlag = System.currentTimeMillis();
                stage(); break;

            case 3:
                if (System.currentTimeMillis() - timeoutFlag > Autonomous.stageTimeout[1])
                    stage();

                break;

            case 4:
                drive.setContinuous(false);
                drive.addTurnCount(-Autonomous.stageCount[2], Autonomous.stageSpeed[2]);
                timeoutFlag = System.currentTimeMillis();
                stage(); break;

            case 5:
                if (drive.isLastMoveComplete()) stage();

                else if (System.currentTimeMillis() - timeoutFlag > Autonomous.stageTimeout[2] * 1000) stage(8);

                break;

            case 6:

                //TODO Finish Autonomous COMMANDS
        }
    }

    public void stage() { if (currentStage < Autonomous.finalStage - 1) currentStage++;}

    public void stage(int stage) { currentStage = stage; }
}
