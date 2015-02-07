package ca.team4976.sub;


import ca.team4976.io.Controller;
import ca.team4976.io.Input;

public class DriveTrain extends CustomRobotDrive {

    Controller.Primary.Stick steeringAxis = Controller.Primary.Stick.LEFT;
    Controller.Primary.Trigger leftTrigger = Controller.Primary.Trigger.LEFT;
    Controller.Primary.Trigger RightTrigger = Controller.Primary.Trigger.RIGHT;

    private double throttle = 0.5;
    private int gear = 2;
    private int autoTurnFlag = 0;

    public DriveTrain() {

        useDeadBand = true;
        DeadBand.setDeadBandType(DeadBand.LINEAR);
        DeadBand.setDeadBandZone(0.15);
    }

    public void teleopArcadeDrive() {

        if (useDeadBand) {

            double[] drive = DeadBand.evaluteDeadBand(steeringAxis.horizontal(), Controller.Primary.Trigger.totalValue(RightTrigger, leftTrigger));

            arcadeDrive(drive[0] * throttle, drive[1] * throttle);

        } else
            arcadeDrive(steeringAxis.horizontal() * throttle, Controller.Primary.Trigger.totalValue(RightTrigger, leftTrigger) * throttle);
    }

    public void updateGear() {

        if (Controller.Primary.DPad.NORTH.isDownOnce() && gear < 3) gear++;
        if (Controller.Primary.DPad.SOUTH.isDownOnce() && gear > 1) gear--;

        updateThrottle();
    }

    public void updateThrottle() {

        switch (gear) {

            case 1:
                throttle = 0.4;
            case 2:
                throttle = 0.7;
            case 3:
                throttle = 1.0;
            default:
                throttle = 0;
        }
    }

    public void updateAutoTurn() {

        if (Controller.Primary.DPad.EAST.isDownOnce() && gear < 3) autoTurnFlag++;
        if (Controller.Primary.DPad.WEST.isDownOnce() && gear > 1) autoTurnFlag--;

        if (autoTurnFlag < 0) {

            if (turnLeft(90, 0.3)) {
                Input.AnalogGyro.DRIVE.reset();
                autoTurnFlag++;
            }

        } else if (autoTurnFlag > 0) {

            if (turnRight(90, 0.3)) {
                Input.AnalogGyro.DRIVE.reset();
                autoTurnFlag--;
            }
        }
    }

    public boolean turnRight(double angle, double speed) {

        if (Input.AnalogGyro.DRIVE.getAngle() > angle) {

            return true;

        } else {

            speed = ramp(speed, angle - Input.AnalogGyro.DRIVE.getAngle());

            arcadeDrive(speed, 0);

            return false;
        }
    }

    public boolean turnLeft(double angle, double speed) {

        if (Input.AnalogGyro.DRIVE.getAngle() < -angle) {

            arcadeDrive(0, 0);
            return true;

        } else {

            speed = ramp(speed, angle + Input.AnalogGyro.DRIVE.getAngle());

            arcadeDrive(-speed, 0);

            return false;
        }
    }

    public boolean forward(double distance, double speed) {

        if (Input.DigitalEncoder.DRIVE_LEFT.getDistance() > distance) {

            arcadeDrive(0, 0);
            return true;

        } else {

            speed = ramp(speed, distance - Input.DigitalEncoder.DRIVE_LEFT.getDistance());

            arcadeDrive(0, speed);
            return false;
        }
    }

    public boolean back(double distance, double speed) {

        if (Input.DigitalEncoder.DRIVE_LEFT.getDistance() < -distance) {

            arcadeDrive(0, 0);
            return true;

        } else {

            speed = ramp(speed, distance + Input.DigitalEncoder.DRIVE_LEFT.getDistance());

            arcadeDrive(0, speed);
            return false;
        }
    }
}
