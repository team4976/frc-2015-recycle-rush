
package ca.team4976.sub;

import ca.team4976.in.Controller;
import ca.team4976.out.Output;

public class Rake {

    /**
     * Initializes the rake subsystem, called in robotInit();
     */
    public Rake() {
        isExtended = false;
    }

    //Determines if the solenoid is extended
    public boolean isExtended;

    /**
     * Called periodically during teleopPeriodic();
     */
    public void update() {
        //If the Start button is down
        if (Controller.Button.START.isDown())
            isExtended = false;

            //If the Y button is down after it has been released (de-bouncing)
        else if (Controller.Button.Y.isDownOnce())
            isExtended = !isExtended;

        //Extend the solenoids based on stored variable
        Output.PneumaticSolenoid.RAKE_LEFT.set(isExtended);
        Output.PneumaticSolenoid.RAKE_RIGHT.set(isExtended);
    }

}