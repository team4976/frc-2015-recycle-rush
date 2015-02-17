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

        state = 0;
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
        if(Controller.Primary.Button.START.isDownOnce()){
            gripper.reset(elevator);
            rake.reset();
        }

       // System.out.print("Angle: " + Input.AnalogGyro.DRIVE.getAngle());
       // System.out.print(" Output: " + Output.Motor.DRIVE_LEFT_1.getSpeed());
      //  System.out.println(" Rate: " + Input.AnalogGyro.DRIVE.getRate());
        //System.out.println("ground: " + Input.Digital.ELEVATOR_GROUND.get() + " | top: " + Input.Digital.ELEVATOR_TOP.get());
        //System.out.println("current level: " + elevator.getCurrentLevel() + " | desired level: " + elevator.getDesiredLevel());
    }

    int state = 0;

    public void autonomousPeriodic() {

        System.out.println("Encoder: " + Input.DigitalEncoder.DRIVE_LEFT.getDistance());

        switch (state) {

            case 0:

                Output.PneumaticSolenoid.GRIPPER_PNEUMATIC.set(DoubleSolenoid.Value.kForward);
                if (drive.back(100, 0.15)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            case 1:

                Output.PneumaticSolenoid.GRIPPER_PNEUMATIC.set(DoubleSolenoid.Value.kReverse);
                if (drive.forward(300, 0.15)) { Input.AnalogGyro.DRIVE.reset(); Input.DigitalEncoder.DRIVE_LEFT.reset(); state++; } break;

            default: drive.arcadeDrive(0, 0);
        }
    }

    public void testPeriodic() {

        if (Controller.Primary.Button.A.isDown()) Input.DigitalEncoder.DRIVE_LEFT.reset();

        System.out.println("Encoder: " + Input.DigitalEncoder.DRIVE_LEFT.getDistance());
    }

}
