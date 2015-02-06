package ca.team4976.sub;


import ca.team4976.in.Controller;
import ca.team4976.in.Encoders;
import ca.team4976.in.Gyros;

public class DriveTrain extends CustomRobotDrive {

    Controller.Stick steeringAxis = Controller.Stick.LEFT;
    Controller.Trigger leftTrigger = Controller.Trigger.LEFT;
    Controller.Trigger RightTrigger = Controller.Trigger.RIGHT;

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

            double[] drive = DeadBand.evaluteDeadBand(steeringAxis.horizontal(), Controller.Trigger.totalValue(RightTrigger, leftTrigger));

            arcadeDrive(drive[0] * throttle, drive[1] * throttle);

        } else arcadeDrive(steeringAxis.horizontal() * throttle, Controller.Trigger.totalValue(RightTrigger, leftTrigger) * throttle);
    }

    public void updateGear() {

        if (Controller.DPad.NORTH.isDownOnce() && gear < 3) gear++;
        if (Controller.DPad.SOUTH.isDownOnce() && gear > 1) gear--;

        updateThrottle();
    }

    public void updateThrottle() {

        switch (gear) {

            case 1: throttle = 0.4;
            case 2: throttle = 0.7;
            case 3: throttle = 1.0;
            default: throttle = 0;
        }
    }

    public void updateAutoTurn() {

        if (Controller.DPad.EAST.isDownOnce() && gear < 3) autoTurnFlag++;
        if (Controller.DPad.WEST.isDownOnce() && gear > 1) autoTurnFlag--;

        if (autoTurnFlag < 0) {

            if (turnLeft(90, 0.3)) { Gyros.gyros[0].reset(); autoTurnFlag++; }

        } else if (autoTurnFlag > 0) {

            if (turnRight(90, 0.3)) { Gyros.gyros[0].reset(); autoTurnFlag--; }
        }
    }

    public boolean turnRight(double angle, double speed) {

        if (Gyros.gyros[0].getAngle() > angle) {

            return true;

        } else {

            speed = ramp(speed, angle - Gyros.gyros[0].getAngle());

            arcadeDrive(speed, 0);

            return false;
        }
    }

    public boolean turnLeft(double angle, double speed) {

        if (Gyros.gyros[0].getAngle() < -angle) {

            arcadeDrive(0, 0);
            return true;

        } else {

            speed = ramp(speed, angle + Gyros.gyros[0].getAngle());

            arcadeDrive(-speed, 0);

            return false;
        }
    }

    public boolean forward(double distance, double speed) {

        if (Encoders.encoders[0].getDistance() > distance) {

            arcadeDrive(0, 0);
            return true;

        } else {

            speed = ramp(speed, distance - Encoders.encoders[0].getDistance());

            arcadeDrive(0, speed);
            return false;
        }
    }

    public boolean back(double distance, double speed) {

        if (Encoders.encoders[0].getDistance() < -distance) {

            arcadeDrive(0, 0);
            return true;

        } else {

            speed = ramp(speed, distance + Encoders.encoders[0].getDistance());

            arcadeDrive(0, speed);
            return false;
        }
    }
}
