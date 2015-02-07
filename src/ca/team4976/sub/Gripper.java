
package ca.team4976.sub;

import ca.team4976.in.Controller;
import ca.team4976.out.Output;

public class Gripper {

    //Determines if the solenoid is extended
    public boolean isExtended;

    //Determines if the container is ready for alignment (sucked in) and is aligned (fully rotated)
    public boolean isSuckedIn, isAligned;

    //Minimum delay before current is tested
    public long startTime;

    /**
     * Initializes the gripper subsystem, called in robotInit();
     */
    public Gripper() {
        isExtended = false;
        isSuckedIn = false;
        isAligned = false;
    }

    /**
     * Called periodically during teleopPeriodic();
     */
    public void update() {

        //If the Start button is down reset the gripper
        if (Controller.Button.START.isDown())
            //Spin motors in opposite directions to push out contatiner
            driveMotors(1.0,-1.0);
            isExtended = false;
        
            //If the X button is down after it has been released (de-bouncing)
        else if (Controller.Button.X.isDownOnce()) {
            isExtended = !isExtended;
            if (isExtended)
                startTime = System.currentTimeMillis();
        }

        //Extend the solenoids based on stored variable
        Output.PneumaticSolenoid.GRIPPER_LEFT.set(isExtended);
        Output.PneumaticSolenoid.GRIPPER_RIGHT.set(isExtended);

        //If the gripper is extended
        if (isExtended) {

            //And the container is not fully sucked in
            if (!isSuckedIn) {

                //Spin motors in opposite directions to suck in container
                Output.Motor.GRIPPER_LEFT.set(-1.0);
                Output.Motor.GRIPPER_RIGHT.set(1.0);

                //If motors current gets too high (container is sucked in)
                if (motorsStressed()) {
                    isSuckedIn = true;
                    startTime = System.currentTimeMillis();
                }

                //Then, if the container is not yet fully aligned
            } else if (!isAligned) {

                //Spin motors in same direction to rotate container
                Output.Motor.GRIPPER_LEFT.set(1.0);
                Output.Motor.GRIPPER_RIGHT.set(1.0);

                //If motor current gets too high (container is aligned)
                if (motorsStressed())
                    isAligned = true;

                //If container is sucked in and aligned
            } else {

                //Stop motors
                Output.Motor.GRIPPER_LEFT.set(0);
                Output.Motor.GRIPPER_RIGHT.set(0);
            }

            //If the gripper is not down, reset the state and stop motors
        } else {
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
        return (Output.Motor.GRIPPER_LEFT.getCurrent() > 0.5 && Output.Motor.GRIPPER_RIGHT.getCurrent() > 0.5) && (System.currentTimeMillis() - startTime > 1000);
    }


}
