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
        checkQueuedLevels();
        System.out.println("Queued Levels: " + queuedLevels);
        System.out.println("Current Level: " + currentLevel);
        System.out.println("encoder.get(): " + Input.DigitalEncoder.ELEVATOR.get());
    }

    private void checkQueuedLevels() {
        if (!Input.Digital.ELEVATOR_TOP.get() && queuedLevels > 0 && currentLevel < 4)
            elevatorUp();
        else if (!Input.Digital.ELEVATOR_GROUND.get() && queuedLevels < 0 && currentLevel > 0)
            elevatorDown();
        else if (Input.Digital.ELEVATOR_GROUND.get()) {
            queuedLevels = 0;
            currentLevel = 0;
            Input.DigitalEncoder.ELEVATOR.reset();
        }
    }

    public void elevatorUp() {
        Output.Motor.ELEVATOR.set(1.0);
        if (Input.DigitalEncoder.ELEVATOR.get() >= currentLevel + 1)
            currentLevel++;
    }

    public void elevatorDown() {
        Output.Motor.ELEVATOR.set(-1.0);
        if (Input.DigitalEncoder.ELEVATOR.get() <= currentLevel - 1)
            currentLevel--;
    }

}
