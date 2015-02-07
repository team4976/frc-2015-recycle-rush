
package ca.team4976.sub;

import ca.team4976.in.Controller;
import ca.team4976.out.Output;

public class Gripper {

    //Determines if the solenoid is extended
    public boolean gripperExtended, kickerExtended;

    //Determines if the container is ready for alignment (sucked in) and is aligned (fully rotated)
    public boolean isSuckedIn, isAligned;

    //Minimum delay before current is tested
    public long startTime;

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

        //If the Start button is down reset the gripper
        if (Controller.Button.START.isDown()) {
            gripperExtended = false;
            kickerExtended = false;
        }
            //If the X button is down after it has been released (de-bouncing)
        else if (Controller.Button.X.isDownOnce()) {
            gripperExtended = !gripperExtended;
            if (gripperExtended)
                startTime = System.currentTimeMillis();
        }
        else if (Controller.Button.A.isDownOnce()) {
            kickerExtended = !kickerExtended;
        }

        //Extend the solenoids based on stored variable
        Output.PneumaticSolenoid.GRIPPER_LEFT.set(gripperExtended);
        Output.PneumaticSolenoid.GRIPPER_RIGHT.set(gripperExtended);


        //If the gripper is extended
        if (gripperExtended) {

            // Only extend the kicker based on user input if the gripper is extended.
            Output.PneumaticSolenoid.GRIPPER_KICKER.set(kickerExtended);
            System.out.println("Gripper extended");

            //And the container is not fully sucked in
            if (!isSuckedIn) {
                System.out.println("Bin not sucked in");
                //Spin motors in opposite directions to suck in container
                Output.Motor.GRIPPER_LEFT.set(-1.0);
                Output.Motor.GRIPPER_RIGHT.set(1.0);

                //If motors current gets too high (container is sucked in)
                if (motorsStressed()) {
                    isSuckedIn = true;
                    startTime = System.currentTimeMillis();
                    System.out.println("Motor stressed in sucked in");
                }

                //Then, if the container is not yet fully aligned
            } else if (!isAligned) {
                System.out.println("Bin not aligned");
                //Spin motors in same direction to rotate container
                Output.Motor.GRIPPER_LEFT.set(1.0);
                Output.Motor.GRIPPER_RIGHT.set(1.0);

                //If motor current gets too high (container is aligned)
                if (motorsStressed()) {
                    isAligned = true;
                    System.out.println("Motor stressed in aligned");
                }
                //If container is sucked in and aligned
            } else {
                System.out.println("Aligned and sucked in");
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
        System.out.println("Left Current: " + Output.Motor.GRIPPER_LEFT.getCurrent());
        System.out.println("Right Current: " + Output.Motor.GRIPPER_RIGHT.getCurrent());
        return (Output.Motor.GRIPPER_LEFT.getCurrent() > 0.75 && Output.Motor.GRIPPER_RIGHT.getCurrent() > 0.75) && (System.currentTimeMillis() - startTime > 1000);
    }


}
