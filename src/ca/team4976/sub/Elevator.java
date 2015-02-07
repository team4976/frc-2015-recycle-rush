package ca.team4976.sub;

import ca.team4976.in.Controller;
import ca.team4976.in.Input;
import ca.team4976.out.Output;

public class Elevator {

    public int queuedLevels, currentLevel;

    public Elevator() {
        queuedLevels = 0;
        currentLevel = 0;
    }

    public void update() {
        if (Controller.Button.RIGHT_BUMPER.isDownOnce()) {
            queuedLevels++;
        } else if (Controller.Button.LEFT_BUMPER.isDownOnce()) {
            queuedLevels--;
        }

        if (Input.Digital.ELEVATOR_A2B.get() && queuedLevels > 0)
            Output.Motor.ELEVATOR.set(0.0);
        else
            checkQueuedLevels();
    }

    private void checkQueuedLevels() {
        if (queuedLevels > 0)
            elevatorUp();
        else if (queuedLevels < 0)
            elevatorDown();
    }

    public void releaseElevator() {
        elevatorDown();
        currentLevel = 0;
    }

    public void groundElevator() {
        elevatorDown();
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
