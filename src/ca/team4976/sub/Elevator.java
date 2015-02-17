package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Input;
import ca.team4976.io.Output;

public class Elevator {

    private double currentLevel;
    private int desiredLevel, displayLevel;
    private double percentError;

    public Elevator() {
        currentLevel = 0;
        desiredLevel = 0;
        displayLevel = 0;
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
        else if (Controller.Primary.Button.BACK.isDownOnce())
            elevatorToLevel(0);
    }

    private boolean checkSecondaryController() {
        if (!Input.Digital.ELEVATOR_TOP.get() && Controller.Secondary.Stick.RIGHT.vertical(-0.2, 0.2) < 0) {
            Output.Motor.ELEVATOR.set(0.5);
            currentLevel = Input.DigitalEncoder.ELEVATOR.getDistance();
            checkTop();
            return false;
        } else if (!Input.Digital.ELEVATOR_GROUND.get() && Controller.Secondary.Stick.RIGHT.vertical(-0.2, 0.2) > 0) {
            Output.Motor.ELEVATOR.set(-0.5);
            currentLevel = Input.DigitalEncoder.ELEVATOR.getDistance();
            checkGround();
            return false;
        }
        return true;
    }

    private void goToDesiredLevel() {
        if (!withinThreshold(desiredLevel)) {
            if (desiredLevel == 0) {
                Output.Motor.ELEVATOR.set(-1.0);
                checkGround();
            } else if (desiredLevel == 4) {
                Output.Motor.ELEVATOR.set(1.0);
                checkTop();
            } else {
                currentLevel = Input.DigitalEncoder.ELEVATOR.getDistance();
                if (desiredLevel < currentLevel) {
                    Output.Motor.ELEVATOR.set(-1.0);
                    checkGround();
                } else if (desiredLevel > currentLevel) {
                    Output.Motor.ELEVATOR.set(1.0);
                    checkTop();
                }
            }
        } else {
            Output.Motor.ELEVATOR.set(0);
        }
    }

    private void checkGround() {
        if (Input.Digital.ELEVATOR_GROUND.get() && (desiredLevel == 0 || desiredLevel < currentLevel)) {
            desiredLevel = 0;
            currentLevel = 0;
            Input.DigitalEncoder.ELEVATOR.reset();
        }
    }

    private void checkTop() {
        if (Input.Digital.ELEVATOR_TOP.get() && (desiredLevel == 4 || desiredLevel > currentLevel)) {
            desiredLevel = 4;
            currentLevel = 4;
            Input.DigitalEncoder.ELEVATOR.set(4);
        }
    }

    public void elevatorUp() {
        desiredLevel++;
    }

    public void elevatorDown() {
        desiredLevel--;
    }

    public void elevatorToLevel(int level) {
        if (level >= 0 && level <= 4)
            desiredLevel = level;
    }

    public double getCurrentLevel() {
        return currentLevel;
    }

    public int getDesiredLevel() {
        return desiredLevel;
    }

    public boolean pastThreshold(int desiredLevel) {
        return (currentLevel + percentError) >= desiredLevel;
    }
    
    public boolean withinThreshold(int desiredLevel) {
        return (currentLevel >= desiredLevel - percentError && currentLevel <= desiredLevel + percentError);
    }

}
