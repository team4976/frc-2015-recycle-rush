
package ca.team4976.sub;

import ca.team4976.in.Controller;
import ca.team4976.out.Output;


public class Rake {

    private boolean isManualMode = false;
    public boolean isExtended;
    public boolean isRightExtended;
    public boolean isLeftExtended;

    /**
     * Initializes the rake subsystem, called in robotInit();
     */
    public Rake() {
        isExtended = false;
    }

    //Determines if the solenoid is extended

    /**
     * Called periodically during teleopPeriodic();
     */
    public void update() {

        if (!isManualMode) {
            //If the Start button is down
            if (Controller.Primary.Button.START.isDown())
                isExtended = false;

                //If the Y button is down after it has been released (de-bouncing)
            else if (Controller.Primary.Button.Y.isDownOnce())
                isExtended = !isExtended;

        } else {
            //If the Start button is down
            if (Controller.Primary.Button.START.isDown())
                isExtended = false;

                //If the A button is down after it has been releases (de-bouncing)
            else if (Controller.Primary.Button.A.isDownOnce())
                isRightExtended = !isRightExtended;

                //If the B button is down after it has been releases (de-bouncing)
            else if (Controller.Primary.Button.B.isDownOnce())
                isLeftExtended = !isLeftExtended;
        }

        //**************************************************
        //Output to solenoids
        //**************************************************

        if (isManualMode) {
            //Extend the solenoids based on stored variable
            Output.PneumaticSolenoid.RAKE_LEFT.set(isLeftExtended);
            Output.PneumaticSolenoid.RAKE_RIGHT.set(isRightExtended);
        }
        else {
            Output.PneumaticSolenoid.RAKE_LEFT.set(isExtended);
            Output.PneumaticSolenoid.RAKE_RIGHT.set(isExtended);
        }

    }
}