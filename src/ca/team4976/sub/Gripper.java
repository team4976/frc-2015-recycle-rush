
package ca.team4976.sub;

import ca.team4976.in.Controller;
import ca.team4976.out.Motors;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class Gripper {

    //Determines if the solenoid is extended
    public boolean isExtended;
    public boolean containerAligned;

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
        containerAligned = false;

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
            isExtended = false;

            //If the X button is down after it has been released (de-bouncing)
        else if (Controller.Button.X.isDownOnce()) {
            isExtended = !isExtended;
            if (isExtended)
                startTime = System.currentTimeMillis();
        }

        extendSolenoids(isExtended);

        //If container is not oriented and the gripper is down
        /*if (!containerOriented() && isExtended) {
            driveMotors(1.0, 1.0);
            //If container is oriented and the gripper is down
            //We rewrote your code. How does it feel?
            //Suck it.
        } else if (containerOriented() && isExtended) {
            if (!container) {
                container = true;
            }
            else {
                driveMotors(1.0, -1.0);
            }

        } else {
            driveMotors(0.0, 0.0);
        }
        */

        // If the gripper is down.
        if (isExtended) {
            // If the container is ready for rotation.
            if (containerAligned) {
                // If the motors are being stressed.
                if (motorsStressed()) {
                    // TEMP: Stop the gripper
                    isExtended = false; // TEMP
                }
                // If the motors are not being stressed, rotate container.
                else {
                    driveMotors(1.0,1.0);
                }
            }
            // If the container is not ready for rotation.
            else {
                // If the motors are being stressed, the container is ready for rotation.
                if (motorsStressed()) {
                    containerAligned = true;
                }
                // If the motors are not being stressed, the container (if there is one) is not ready for rotation.
                else {
                    driveMotors(1.0,-1.0);
                }
            }
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
        if (System.currentTimeMillis() - startTime > 210)
            if (leftMotor.getOutputCurrent() > 0.5)
                return true;
        return false;
    }

    public void driveMotors(double leftSpeed, double rightSpeed) {
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }



}
