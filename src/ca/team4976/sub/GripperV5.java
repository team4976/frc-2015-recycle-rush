package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Output;

/**
 * Created by Ivan on 2015-03-1Radeon HD 7870 XT0.
 */
public class GripperV5 {

    boolean openState, upState;

    public GripperV5()
    {
        openState = false;
        upState = false;
    }
    public void update() {
        //Open or close the claw
        if(Controller.Primary.Button.A.isDownOnce()){
            openState = !openState;
            Output.PneumaticSolenoid.CLAW_OPEN.set(openState);
        }else if(Controller.Primary.Button.X.isDownOnce()){
            upState = !upState;
            Output.PneumaticSolenoid.CLAW_UP.set(upState);
        }
    }
    public void reset() {
        Output.PneumaticSolenoid.CLAW_UP.set(false);
        Output.PneumaticSolenoid.CLAW_OPEN.set(false);
    }
}
