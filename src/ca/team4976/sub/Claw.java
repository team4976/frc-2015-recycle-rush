package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Output;

/**
 * Created by Ivan on 2015-03-1.
 * @version 1.0
 */
public class Claw {

    boolean openState = false; boolean upState = false;

    public void teleopPeriodic() {

        if (Controller.Primary.Button.A.isDownOnce()) openState = !openState;


        if (Controller.Primary.Button.X.isDownOnce()) upState = !upState;

        Output.PneumaticSolenoid.CLAW_OPEN.set(openState);
        Output.PneumaticSolenoid.CLAW_UP.set(upState);
    }

    public void reset() {

        openState = false; upState = false;
        Output.PneumaticSolenoid.CLAW_UP.set(false);
        Output.PneumaticSolenoid.CLAW_OPEN.set(false);
    }
}
