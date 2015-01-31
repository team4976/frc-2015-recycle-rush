package ca.team4976.sub;

import ca.team4976.in.Controller;

public class Elevator {

    private int queuedLevels, currentLevel;

    public Elevator() {
        queuedLevels = 0;
        currentLevel = 0;
    }

    public void teleOpLoop() {
        if (Controller.Button._360_A.isDownOnce()) {
            queuedLevels++;
        } else if (Controller.Button._360_B.isDownOnce()) {
            queuedLevels--;
        } else if (Controller.Button._360_X.isDownOnce()) {
            releaseElevator();
        } else if (Controller.Button._360_Y.isDownOnce()) {
            groundElevator();
        }
        checkQueuedLevels();
    }

    public void autoLoop() {

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

    private void releaseElevator() {
        elevatorDown();
        currentLevel = 0;
    }

    private void groundElevator() {
        while (currentLevel > 0) {
            elevatorDown();
        }
        currentLevel = 0;
    }

    private void elevatorUp() {
        if (currentLevel < 4) {
            currentLevel++;
        }
    }

    private void elevatorDown() {
        if (currentLevel > 0) {
            currentLevel--;
        }
    }
    
}
