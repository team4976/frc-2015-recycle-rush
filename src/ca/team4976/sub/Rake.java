package ca.team4976.sub;
import ca.team4976.io.Controller;
import ca.team4976.io.Output;

public class Rake {

    public boolean isExtended = false;

    public void teleopPeriodic() {

        if (Controller.Primary.Button.RIGHT_STICK.isDownOnce())  isExtended = !isExtended;
        Output.PneumaticSolenoid.RAKE.set(isExtended);
    }

    public void reset() {
        isExtended = false;
    }
}