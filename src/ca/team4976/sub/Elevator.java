package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Input;
import ca.team4976.io.Output;

public class Elevator {

    private int currentLevel, desiredLevel;

    public Elevator() {
        currentLevel = 0;
        desiredLevel = 0;
    }

    public void update() {
        checkPrimaryController();
        if (checkSecondaryController())
            goToDesiredLevel();
        checkHomes();
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
        if (currentLevel != desiredLevel) {
            if (desiredLevel == 0) {
                Output.Motor.ELEVATOR.set(-1.0);
            } else if (desiredLevel == 4) {
                Output.Motor.ELEVATOR.set(1.0);
            } else if (desiredLevel < currentLevel) {
                Output.Motor.ELEVATOR.set(-1.0);
                currentLevel = (int) (Input.DigitalEncoder.ELEVATOR.getDistance());
            } else if (desiredLevel > currentLevel) {
                Output.Motor.ELEVATOR.set(1.0);
                currentLevel = (int) (Input.DigitalEncoder.ELEVATOR.getDistance());
            }
        } else {
            Output.Motor.ELEVATOR.set(0.0);
        }
    }
    
    private void checkHomes() {
        if (Input.Digital.ELEVATOR_GROUND.get()) {
            currentLevel = 0;
            desiredLevel = 0;
            Input.DigitalEncoder.ELEVATOR.reset();
        } else if (Input.Digital.ELEVATOR_TOP.get()) {
            currentLevel = 4;
            desiredLevel = 4;
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
    
    public int getCurrentLevel() {
        return currentLevel;        
    }
    
    public int getDesiredLevel() {
        return desiredLevel;
    }

}
