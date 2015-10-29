package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Input;


public class DriveTrain extends CustomRobotDrive implements Runnable {

    public Thread thread = new Thread(this);

    private boolean shouldHalt = false;
    private boolean teleopEnabled = false;
    private boolean isEnabled = false;
    private int defaultTickTiming = 1000 / 50;
    private int currentTickTiming = 0;
    private long waitTime = 20;

    private static double[] turnCount = new double[2];
    private static double[] moveCount = new double[2];

    private final int gearDefault = 1;
    private int gear = gearDefault;

    private double[] gears = new double[] {0.3, 0.5, 1};

    private void userControl() {

        Controller.Primary.Stick stick = Controller.Primary.Stick.LEFT;
        Controller.Primary.Trigger left = Controller.Primary.Trigger.LEFT;
        Controller.Primary.Trigger right = Controller.Primary.Trigger.RIGHT;

        if (Controller.Primary.DPad.WEST.isDownOnce()) {

            turnCount[0] += -90;
            turnCount[1] = gears[gear];

        } else if (Controller.Primary.DPad.EAST.isDownOnce()) {

            turnCount[0] += 90;
            turnCount[1] = gears[gear];

        } else if (Controller.Primary.DPad.NORTH.isDownOnce()) incrementGear(1);

        else if (Controller.Primary.DPad.WEST.isDownOnce()) incrementGear(-1);

        arcadeDrive(stick.horizontal() * gears[gear],
                (right.value() - left.value() * gears[gear]));
    }

    private void checkTurn() {

        if (turnCount[0] != 0 && moveCount[0] != 0)

            System.out.println("ERROR: DRIVE_443");

        else {

            if (turnCount[0] < 0) {

                if (turnLeft(-turnCount[0], turnCount[1])) turnCount[0] = 0;

            } else if (turnCount[0] > 0) {

                if (turnRight(turnCount[0], turnCount[1])) turnCount[0] = 0;

            } else Input.AnalogGyro.DRIVE.reset();
        }
    }

    public void checkMove() {

        if (turnCount[0] != 0 && moveCount[0] != 0)

            System.out.println("ERROR: DRIVE_443");

        else {

            if (moveCount[0] < 0) {

                if (moveBackwards(-moveCount[0], moveCount[1])) moveCount[0] = 0;

            } else if (moveCount[0] > 0) {

                if (moveForward(moveCount[0], moveCount[1])) moveCount[0] = 0;

            } else Input.DigitalEncoder.DRIVE_LEFT.reset();
        }
    }

    private boolean moveForward(double distance, double speed) {

        currentTickTiming = 1000 / 200;

        if (Input.DigitalEncoder.DRIVE_LEFT.getDistance() >= distance) {

            directDrive(-0.2, -0.2);
            waitTime--;

        } else {

            directDrive(speed, speed);
            waitTime = 20;
        }

        if (waitTime == 0) {

            directDrive(0, 0);
            currentTickTiming = defaultTickTiming;
            return true;
        }

        return true;
    }

    private boolean moveBackwards(double distance, double speed) {

        currentTickTiming = 1000 / 200;

        if (Input.DigitalEncoder.DRIVE_LEFT.getDistance() <= -distance) {

            directDrive(0.2, 0.2);
            waitTime--;

        } else {

            directDrive(-speed, -speed);
            waitTime = 20;
        }

        if (waitTime == 0) {

            directDrive(0, 0);
            currentTickTiming = defaultTickTiming;
            return true;
        }

        return true;
    }

    private boolean turnLeft(double angle, double speed) {

        currentTickTiming = 1000 / 200;

        if (Input.AnalogGyro.DRIVE.getAngle() <= -angle) {

            directDrive(0.2, -0.2);
            waitTime--;

        } else {

            directDrive(-speed, speed);
            waitTime = 20;
        }

        if (waitTime == 0) {

            directDrive(0, 0);
            currentTickTiming = defaultTickTiming;
            return true;
        }

        return true;
    }

    private boolean turnRight(double angle, double speed) {

        currentTickTiming = 1000 / 200;

        if (Input.AnalogGyro.DRIVE.getAngle() >= angle) {

            directDrive(0.2, -0.2);
            waitTime--;

        } else {

            directDrive(-speed, speed);
            waitTime = 100;
        }

        if (waitTime == 0) {

            directDrive(0, 0);
            currentTickTiming = defaultTickTiming;
            return true;
        }

        return true;
    }

    @Override
    public void run() {

        long lastTick = System.currentTimeMillis();

        while (!shouldHalt) {

            if (lastTick - System.currentTimeMillis() >= currentTickTiming) {

                if (teleopEnabled) {

                    if (turnCount[0] == 0) userControl();

                    else checkTurn();

                } else if (isEnabled) {

                    checkTurn();
                    checkMove();
                }
            }
        }
    }

    public void incrementGear(int direction) {

        if (gear < 2 && direction > 0) gear++;

        else if (gear > 0 && direction < 0) gear--;

        else gear = gearDefault;
    }

    public void teleopInit() { teleopEnabled = true; isEnabled = true; }

    public void autonomousInit() { teleopEnabled = false; isEnabled = true; }

    public void disable() { teleopEnabled = false; isEnabled = false; }

    public void keepRunning(boolean shouldStop) { shouldHalt = shouldStop; }

    public static void setTurnCount(int count, double speed) {

        if (isTurnComplete()) turnCount = new double[] {count, speed};

        else System.out.println("ERROR: DRIVE_444");
    }

    public static void setMoveCount(int count, double speed) {

        if (isMoveComplete()) moveCount = new double[] {count, speed};

        else System.out.println("ERROR: DRIVE_444");
    }

    public static boolean isTurnComplete() { return turnCount[0] == 0; }

    public static boolean isMoveComplete() { return moveCount[0] == 0; }
}
