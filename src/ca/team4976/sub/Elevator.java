package ca.team4976.sub;

import ca.team4976.in.Controller;

public class Elevator {

    private int queuedLevels, currentLevel;

    public Elevator() {
        queuedLevels = 0;
        currentLevel = 0;
    }

    public void checkController() {
        if (Controller.Button._360_RIGHT_BUMPER.isDownOnce()) {
            queuedLevels++;
        } else if (Controller.Button._360_LEFT_BUMPER.isDownOnce()) {
            queuedLevels--;
        } else if (Controller.Button._360_A.isDownOnce()) {
            releaseElevator();
        } else if (Controller.Button._360_START.isDownOnce()) {
            groundElevator();
        }
        checkQueuedLevels();
    }

    private void checkQueuedLevels() {
        if (queuedLevels > 0) {
            elevatorUp();
            currentLevel--;
        } else if (queuedLevels < 0) {
            elevatorDown();
            currentLevel++;
        }
    }

    public void releaseElevator() {
        elevatorDown();
        currentLevel = 0;
    }

    public void groundElevator() {
        while (currentLevel > 0) {
            elevatorDown();
        }
        currentLevel = 0;
    }

    public void elevatorUp() {
        if (currentLevel < 4) {
            currentLevel++;
        }
    }

    public void elevatorDown() {
        if (currentLevel > 0) {
            currentLevel--;
        }
    }
    
}
