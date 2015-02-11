package ca.team4976.sub;
import ca.team4976.io.Controller;
import ca.team4976.io.Output;

public class Gripper {
    //Determines if the solenoids are extended
    public boolean gripperExtended, kickerExtended;

    //Determines if the container is ready for alignment(sucked in) and is aligned (fully rotated)
    public boolean isSuckedIn, isAligned;

    //Minimum delay before current is tested
    public long startTime;

    public double currentThreshold = 0.5;

    double leftTrigger, rightTrigger;
    boolean leftBumper, rightBumper;

    boolean secondaryControllerActive;

    /**
     * Initializes the gripper subsystem, called in robotInit();
     */
    public Gripper() {
        gripperExtended = false;
        kickerExtended = false;
        isSuckedIn = false;
        isAligned = false;

        leftTrigger = 0.0;
        rightTrigger = 0.0;
        leftBumper = false;
        rightBumper = false;
    }
    /**
     * Called periodically during teleopPeriodic();
     */
    public void update() {
        //Update triggers and bumpers
        leftTrigger = Controller.Secondary.Trigger.LEFT.value();
        rightTrigger = Controller.Secondary.Trigger.RIGHT.value();
        leftBumper = Controller.Secondary.Button.LEFT_BUMPER.isDown();
        rightBumper = Controller.Secondary.Button.RIGHT_BUMPER.isDown();

        //If the Start button is down reset the gripper
        if (Controller.Primary.Button.START.isDown()) {
            resetGripper();
        }
        //If the X button is down change gripper state
        else if (Controller.Primary.Button.X.isDownOnce()) {
            secondaryControllerActive = false;
            gripperExtended = !gripperExtended;
            if (gripperExtended)
                startTime = System.currentTimeMillis();
        }//If the A button is pressed change the Kicker state
        else if (Controller.Primary.Button.A.isDownOnce()) {
            secondaryControllerActive = false;
            if(gripperExtended) 
                kickerExtended = !kickerExtended;
        }

        //Secondary controls
        if (leftTrigger > 0){
            secondaryControllerActive = true;
            Output.Motor.GRIPPER_LEFT.set(leftTrigger * -1);
        }
        else if (leftBumper) {
            secondaryControllerActive = true;
            Output.Motor.GRIPPER_LEFT.set(1.0);
        }

        if (rightTrigger > 0){
            secondaryControllerActive = true;
            Output.Motor.GRIPPER_RIGHT.set(rightTrigger);
        }
        else if (rightBumper) {
            secondaryControllerActive = true;
            Output.Motor.GRIPPER_LEFT.set(-1.0);
        }

        if (Controller.Secondary.Button.X.isDownOnce()) {
            secondaryControllerActive = true;
            gripperExtended = !gripperExtended;
        }
        else if (Controller.Secondary.Button.A.isDownOnce()) {
            secondaryControllerActive = true;
            kickerExtended = !kickerExtended;
        }

        // If the secondary controller is not active (primary is active)
        if (!secondaryControllerActive) {
            //If the gripper is extended
            if (gripperExtended) {
                //And the container is not fully sucked in
                if (!isSuckedIn) {
                    // Only extend the kicker based on user input if the gripper is extended and their is no container.
                    Output.PneumaticSolenoid.GRIPPER_KICKER.set(kickerExtended);

                    //Spin motors in opposite directions to suck in container
                    Output.Motor.GRIPPER_LEFT.set(-1.0);
                    Output.Motor.GRIPPER_RIGHT.set(1.0);

                    if (motorsStressed()) {
                        isSuckedIn = true;
                        startTime = System.currentTimeMillis();
                    }
                } else {
                    //Stop motors
                    Output.Motor.GRIPPER_LEFT.set(0);
                    Output.Motor.GRIPPER_RIGHT.set(0);
                    // If the elevator is in the process of lifting the container
                    // out of the gripper, reset the gripper
                    System.out.println("Current level: " + Elevator.currentLevel + " and queued level: " + Elevator.queuedLevels);
                    if (Elevator.currentLevel == 0 && Elevator.queuedLevels >= 1)
                        resetGripper();
                }
                //If the gripper is not down, reset the state and stop motors
            } else {
                // Pull the kicker in if the gripper is up.
                isSuckedIn = false;
                isAligned = false;

                Output.PneumaticSolenoid.GRIPPER_KICKER.set(false);
                Output.Motor.GRIPPER_LEFT.set(0);
                Output.Motor.GRIPPER_RIGHT.set(0);
            }

            //Override the kicker solenoid with the kickerExtended variable if the second controller was used.
        } else {
            Output.PneumaticSolenoid.GRIPPER_KICKER.set(kickerExtended);
        }
        
        //Extend the solenoids based on stored variable
        Output.PneumaticSolenoid.GRIPPER_PNEUMATIC.set(gripperExtended);
    }
    /**
     * Determines if the container is oriented
     *
     * @return if the container is oriented
     */
    public boolean motorsStressed() {
        return (Output.Motor.GRIPPER_LEFT.getCurrent() > currentThreshold && Output.Motor.GRIPPER_RIGHT.getCurrent() > currentThreshold) && (System.currentTimeMillis() - startTime > 1000);
    }

    public void resetGripper() {
        gripperExtended = false;
        kickerExtended = false;
        secondaryControllerActive = false;
    }

}
