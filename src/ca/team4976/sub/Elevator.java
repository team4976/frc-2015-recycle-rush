package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Input;
import ca.team4976.io.Output;

public class Elevator {

    private double currentLevel;
    private int desiredLevel;
    private double percentError;

    public Elevator() {
        currentLevel = 0;
        desiredLevel = 0;
        percentError = 0.1;
    }

    public void update() {
        checkPrimaryController();
        if (checkSecondaryController())
            goToDesiredLevel();
    }

    private void checkPrimaryController() {
        if (Controller.Primary.Button.RIGHT_BUMPER.isDownOnce() && desiredLevel < 4)
            elevatorUp();
        else if (Controller.Primary.Button.LEFT_BUMPER.isDownOnce() && desiredLevel > 0)
            elevatorDown();
        else if (Controller.Primary.Button.START.isDownOnce())
            elevatorToLevel(0);
    }

    private boolean checkSecondaryController() {
        if (!Input.Digital.ELEVATOR_TOP.get() && Controller.Secondary.Stick.RIGHT.vertical(-0.2, 0.2) < 0) {
            Output.Motor.ELEVATOR.set(0.5);
            return false;
        } else if (!Input.Digital.ELEVATOR_GROUND.get() && Controller.Secondary.Stick.RIGHT.vertical(-0.2, 0.2) > 0) {
            Output.Motor.ELEVATOR.set(-0.5);
            return false;
        }
        return true;
    }

    private void goToDesiredLevel() {
        if (desiredLevel == 0) {

        } else if (desiredLevel == 1) {

        } else if (desiredLevel == 2) {

        } else if (desiredLevel == 3) {

        } else if (desiredLevel == 4) {

        }
    }

    public void elevatorUp() {
        desiredLevel++;
    }

    public void elevatorDown() {
        desiredLevel--;
    }

    public void elevatorToLevel(int level) {
        desiredLevel = level;
    }

    public double getCurrentLevel() {
        return currentLevel;
    }

    public int getDesiredLevel() {
        return desiredLevel;
    }

    public boolean withinThreshold(int desiredLevel) {
        return (currentLevel >= desiredLevel - percentError && currentLevel <= desiredLevel + percentError);
    }

}
