
package ca.team4976.sub;

import ca.team4976.in.Controller;
import ca.team4976.out.Motors;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class Gripper {

    //Determines if the solenoid is extended
    public boolean isExtended;

    //Determines if the container is ready for alignment (sucked in) and is aligmned (fully rotated)
    public boolean isSuckedIn, isAligned;

    //Minimum delay before current is tested
    public long startTime;

    //The 2 solenoids for the gripper
    public Solenoid leftSolenoid;
    public Solenoid rightSolenoid;

    //The 2 motors for the gripper
    public CANTalon leftMotor;
    public CANTalon rightMotor;

    /**
     * Initializes the gripper subsystem, called in robotInit();
     *
     * @param nodeID    The Node ID for the PCM on the CAN setup
     * @param leftPort  The Port ID for the first solenoid on the PCM
     * @param rightPort The Port ID for the second solenoid on the PCM
     */
    public Gripper(int nodeID, int leftPort, int rightPort, int leftCAN, int rightCAN) {
        isExtended = false;
        isSuckedIn = false;
        isAligned = false;

        leftSolenoid = new Solenoid(nodeID, leftPort);
        rightSolenoid = new Solenoid(nodeID, rightPort);

        leftMotor = Motors.canMotors[leftCAN];
        rightMotor = Motors.canMotors[rightCAN];
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
        extendSolenoids(isExtended);

        //If the gripper is extended
        if (isExtended) {

            //And the container is not fully sucked in
            if (!isSuckedIn) {

                //Spin motors in opposite directions to suck in container
                driveMotors(-1.0, 1.0);

                //If motors current gets too high (container is sucked in)
                if (motorsStressed()) {
                    isSuckedIn = true;
                    startTime = System.currentTimeMillis();
                }

                //Then, if the container is not yet fully aligned
            } else if (!isAligned) {

                //Spin motors in same direction to rotate container
                driveMotors(1.0, 1.0);

                //If motor current gets too high (container is aligned)
                if (motorsStressed())
                    isAligned = true;

                //If container is sucked in and aligned
            } else {

                //Stop motors
                driveMotors(0.0, 0.0);
            }

            //If the gripper is not down, reset the state and stop motors
        } else {
            driveMotors(0.0, 0.0);
            isSuckedIn = false;
            isAligned = false;
        }

    }

    /**
     * Extends the gripper solenoids
     *
     * @param extend Determines whether or not the solenoids extend
     */
    public void extendSolenoids(boolean extend) {
        leftSolenoid.set(extend);
        rightSolenoid.set(extend);
    }

    /**
     * Determines if the container is oriented
     *
     * @return if the container is oriented
     */
    public boolean motorsStressed() {
        if (System.currentTimeMillis() - startTime > 1000)
            if (leftMotor.getOutputCurrent() > 0.5)
                return true;
        return false;
    }

    public void driveMotors(double leftSpeed, double rightSpeed) {
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }


}
