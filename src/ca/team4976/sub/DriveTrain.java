package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Input;

public class DriveTrain extends CustomRobotDrive implements Runnable {

    Thread thread = new Thread(this);

    private boolean shouldHault = false;
    private boolean teleopEnabled = false;
    private boolean isEnabled = false;
    private int defaultTickTiming = 1000 / 50;
    private int currentTickTiming = 0;
    private long waitTime = 100;

    private int turnCount = 0;

    public DriveTrain() { thread.start(); }

    public void teleopInit() { teleopEnabled = true; isEnabled = true; }

    public void disable() { teleopEnabled = false; isEnabled = false; }

    public void keepRunning(boolean shouldStop) { shouldHault = shouldStop; }

    @Override
    public void run() {

        long lastTick = System.currentTimeMillis();

        while (!shouldHault) {

            if (lastTick - System.currentTimeMillis() >= currentTickTiming) {

                if (teleopEnabled) {


                } else if (isEnabled) {


                }
            }
        }
    }

    private void userControl() {

        Controller.Primary.Stick stick = Controller.Primary.Stick.LEFT;
        Controller.Primary.Trigger left = Controller.Primary.Trigger.LEFT;
        Controller.Primary.Trigger right = Controller.Primary.Trigger.RIGHT;

        if (Controller.Primary.DPad.WEST.isDownOnce()) addTurn(-90);

        if (Controller.Primary.DPad.EAST.isDownOnce()) addTurn(90);

        arcadeDrive(stick.horizontal(), right.value() - left.value());
    }

    private void checkTurn(boolean shouldForce) {

        if (turnCount < 0) {

            if (turnLeft(-turnCount)) turnCount = 0;

        } else if (turnCount > 0) {

            if (turnRight(turnCount)) turnCount = 0;
        }
    }

    private boolean turnLeft(int angle) {

        currentTickTiming = 1000 / 200;

        if (Input.AnalogGyro.DRIVE.getAngle() <= -angle) {

            directDrive(0.2, -0.2);
            waitTime--;

        } else {

            directDrive(-1, 1);
            waitTime = 100;
        }

        if (waitTime == 0) {

            directDrive(0, 0);
            currentTickTiming = defaultTickTiming;
            return true;
        }

        return true;
    }

    private boolean turnRight(int angle) {

        currentTickTiming = 1000 / 200;

        if (Input.AnalogGyro.DRIVE.getAngle() >= angle) {

            directDrive(0.2, -0.2);
            waitTime--;

        } else {

            directDrive(-1, 1);
            waitTime = 100;
        }

        if (waitTime == 0) {

            directDrive(0, 0);
            currentTickTiming = defaultTickTiming;
            return true;
        }

        return true;
    }

    public void addTurn(int count) { turnCount += count; }
}
