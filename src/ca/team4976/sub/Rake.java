
package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Output;


public class Rake {

    public boolean isExtended;

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

        //If the Right Stick button is down after it has been released (de-bouncing)
        if (Controller.Primary.Button.RIGHT_STICK.isDownOnce())
            isExtended = !isExtended;

        //Extend the solenoids based on stored variable
        Output.PneumaticSolenoid.RAKE.set(isExtended);
    }
    //reset function called in main when start button is pressed
    public void reset(){
        isExtended = false;
    }
}