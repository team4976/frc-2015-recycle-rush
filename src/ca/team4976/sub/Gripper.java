
package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Input;
import ca.team4976.io.Output;

public class Gripper {

    //Determines if the solenoid is extended
    public boolean gripperExtended, kickerExtended;

    //Determines if the container is ready for alignment (sucked in) and is aligned (fully rotated)
    public boolean isSuckedIn, isAligned;

    //Minimum delay before current is tested
    public long startTime;

    //Laser
    public boolean laserDetector;

    public float currentThreshold = 1;

    /**
     * Initializes the gripper subsystem, called in robotInit();
     */
    public Gripper() {
        gripperExtended = false;
        kickerExtended = false;
        isSuckedIn = false;
        isAligned = false;
    }

    /**
     * Called periodically during teleopPeriodic();
     */
    public void update() {
        laserDetector = Input.Digital.CONTAINER_POSITION_LASER.get();
        System.out.println(laserDetector);
        //If the Start button is down reset the gripper
        if (Controller.Primary.Button.START.isDown()) {
            gripperExtended = false;
            kickerExtended = false;
        }
        //If the X button is down after it has been released (de-bouncing)
        else if (Controller.Primary.Button.X.isDownOnce()) {
            gripperExtended = !gripperExtended;
            if (gripperExtended)
                startTime = System.currentTimeMillis();
        } else if (Controller.Primary.Button.A.isDownOnce()) {
            kickerExtended = !kickerExtended;
        }
        double leftTrigger = Controller.Secondary.Trigger.LEFT.value();
        double rightTrigger = Controller.Secondary.Trigger.RIGHT.value();
        boolean leftBumper = Controller.Secondary.Button.LEFT_BUMPER.isDown();
        boolean rightBumper = Controller.Secondary.Button.RIGHT_BUMPER.isDown();

        if (leftTrigger > 0){
            Output.Motor.GRIPPER_LEFT.set(leftTrigger * -1);

        }
        else if (leftBumper) {
            Output.Motor.GRIPPER_LEFT.set(1.0);
        }

        if (rightTrigger > 0){
            Output.Motor.GRIPPER_RIGHT.set(rightTrigger);
        }
        else if (rightBumper) {
            Output.Motor.GRIPPER_LEFT.set(-1.0);
        }

        if (Controller.Secondary.Button.X.isDownOnce()) {
            gripperExtended = !gripperExtended;
        }
        if (Controller.Secondary.Button.Y.isDownOnce()) {
            kickerExtended = !kickerExtended;

        }

        //Extend the solenoids based on stored variable
        Output.PneumaticSolenoid.GRIPPER_PNEUMATIC.set(gripperExtended);
        System.out.println("The gripper state is " + gripperExtended);


        //If the gripper is extended
        if (gripperExtended) {

            //And the container is not fully sucked in
            if (!isSuckedIn) {

                // Only extend the kicker based on user input if the gripper is extended and their is no container.
                System.out.println("The kicker state is " + kickerExtended);
                Output.PneumaticSolenoid.GRIPPER_KICKER.set(kickerExtended);

                //Spin motors in opposite directions to suck in container
                Output.Motor.GRIPPER_LEFT.set(-1.0);

                //Then, if the container is not yet fully aligned
            } else if (!isAligned) {

                //Spin motors in same direction to rotate container
                Output.Motor.GRIPPER_LEFT.set(1.0);
                Output.Motor.GRIPPER_RIGHT.set(1.0);

                //If motor current gets too high (container is aligned) or laser returns true
                if (motorsStressed() || laserDetector)
                    isAligned = true;

                //If container is sucked in and aligned
            } else {

                //Stop motors
                Output.Motor.GRIPPER_LEFT.set(0);
                Output.Motor.GRIPPER_RIGHT.set(0);
            }

            //If the gripper is not down, reset the state and stop motors
        } else {
            // Pull the kicker in if the gripper is up.
            Output.PneumaticSolenoid.GRIPPER_KICKER.set(false);
            Output.Motor.GRIPPER_LEFT.set(0);
            Output.Motor.GRIPPER_RIGHT.set(0);
            isSuckedIn = false;
            isAligned = false;
        }

    }

    /**
     * Determines if the container is oriented
     *
     * @return if the container is oriented
     */
    public boolean motorsStressed() {
        System.out.println("Left " + Output.Motor.GRIPPER_LEFT.getCurrent());
        System.out.println("Right " + Output.Motor.GRIPPER_RIGHT.getCurrent());
        return (Output.Motor.GRIPPER_LEFT.getCurrent() > currentThreshold && Output.Motor.GRIPPER_RIGHT.getCurrent() > currentThreshold) && (System.currentTimeMillis() - startTime > 1000);
    }


}
