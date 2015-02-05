
package ca.team4976.sub;

import ca.team4976.in.Controller;
import edu.wpi.first.wpilibj.Solenoid;

public class Rake {

    //Initialized in robotInit();
    public Rake(int nodeID, int port1, int port2) {
        isExtended = false;
        rakeSolenoid1 = new Solenoid(nodeID, port1);
        rakeSolenoid2 = new Solenoid(nodeID, port2);
    }

    //Determines if the solenoid is extended
    public boolean isExtended;

    //The 2 solenoids for the rake
    public Solenoid rakeSolenoid1;
    public Solenoid rakeSolenoid2;

    //Called in teleopPeriodic();
    public void update() {
        //If the Start button is down
        if (Controller.Button.START.isDown())
            isExtended = false;

        //If the Y button is down after it has been released (debouncing)
        else if (Controller.Button.Y.isDownOnce())
            isExtended = !isExtended;

        //Extend the solenoids based on stored variable
        extendSolenoids(isExtended);
    }

    public void extendSolenoids(boolean extend) {
        rakeSolenoid1.set(extend);
        rakeSolenoid2.set(extend);
    }

}