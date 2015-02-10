
package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Output;


public class Rake {

    public boolean isLeftExtended, isRightExtended;

    /**
     * Initializes the rake subsystem, called in robotInit();
     */
    public Rake() {
        isLeftExtended = false;
        isRightExtended = false;
    }

    //Determines if the solenoid is extended

    /**
     * Called periodically during teleopPeriodic();
     */
    public void update() {

        //If the Start button is down
        if (Controller.Primary.Button.START.isDown()) {
            isLeftExtended = false;
            isRightExtended = false;
        }

        //If the Y button is down after it has been released (de-bouncing)
        else if (Controller.Primary.Button.Y.isDownOnce()) {
            if (isLeftExtended != isRightExtended) {
                isLeftExtended = false;
                isRightExtended = false;
            } else {
                isLeftExtended = !isLeftExtended;
                isRightExtended = !isRightExtended;
            }
        }

        //If the A button is down after it has been releases (de-bouncing)
        if (Controller.Secondary.Button.Y.isDownOnce())
            isRightExtended = !isRightExtended;

            //If the B button is down after it has been releases (de-bouncing)
        else if (Controller.Secondary.Button.B.isDownOnce())
            isLeftExtended = !isLeftExtended;

        //Extend the solenoids based on stored variable
        Output.PneumaticSolenoid.RAKE_LEFT.set(isLeftExtended);
        Output.PneumaticSolenoid.RAKE_RIGHT.set(isRightExtended);

    }
}