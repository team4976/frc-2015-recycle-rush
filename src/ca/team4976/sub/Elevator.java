package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Input;
import ca.team4976.io.Output;

public class Elevator {

    public int queuedLevels, currentLevel;
    public boolean grounding;

    public Elevator() {
        queuedLevels = 0;
        currentLevel = 0;
        grounding = false;
    }

    public int[] update() {
        if (Controller.Primary.Button.RIGHT_BUMPER.isDownOnce())
            queuedLevels++;
        else if (Controller.Primary.Button.LEFT_BUMPER.isDownOnce())
            queuedLevels--;
        else if (Controller.Primary.Button.START.isDownOnce())
            grounding = !grounding;
        if (!Input.Digital.ELEVATOR_TOP.get() && Controller.Secondary.Stick.RIGHT.vertical(-0.2, 0.2) < 0)
            Output.Motor.ELEVATOR.set(0.5);
        else if (!Input.Digital.ELEVATOR_GROUND.get() && Controller.Secondary.Stick.RIGHT.vertical(-0.2, 0.2) > 0)
            Output.Motor.ELEVATOR.set(-0.5);
        else
            checkQueuedLevels();
        System.out.println("Queued Levels: " + queuedLevels);
        System.out.println("Current Level: " + currentLevel);
        System.out.println("encoder.get(): " + Input.DigitalEncoder.ELEVATOR.getDistance());
        return new int[] { queuedLevels, currentLevel };
    }

    private void checkQueuedLevels() {
        if (grounding) {
            Output.Motor.ELEVATOR.set(-1.0);
            queuedLevels = 0;
        }
        if (!Input.Digital.ELEVATOR_TOP.get() && queuedLevels > 0) {
            if (currentLevel < 4)
                elevatorUp();
            else
                queuedLevels = 0;
        } else if (!Input.Digital.ELEVATOR_GROUND.get() && queuedLevels < 0) {
            if (currentLevel > 0)
                elevatorDown();
            else
                queuedLevels = 0;
        } else if (Input.Digital.ELEVATOR_GROUND.get()) {
            queuedLevels = 0;
            currentLevel = 0;
            //Input.DigitalEncoder.ELEVATOR.reset();
            grounding = false;
            Output.Motor.ELEVATOR.set(0.0);
        } else if (Input.Digital.ELEVATOR_TOP.get()) {
            queuedLevels = 0;
            currentLevel = 4;
            grounding = false;
            Output.Motor.ELEVATOR.set(0.0);
        } else if (!grounding) {
            Output.Motor.ELEVATOR.set(0.0);
        }
    }

    public void elevatorUp() {
        Output.Motor.ELEVATOR.set(1.0);
        if (Input.DigitalEncoder.ELEVATOR.getDistance() >= (currentLevel + 1) * 1000) {
            currentLevel++;
            queuedLevels--;
        }
    }

    public void elevatorDown() {
        Output.Motor.ELEVATOR.set(-1.0);
        if (Input.DigitalEncoder.ELEVATOR.getDistance() <= (currentLevel - 1) * 1000) {
            currentLevel--;
            queuedLevels++;
        }
    }

}
