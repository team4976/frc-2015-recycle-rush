
package ca.team4976.sub;

import ca.team4976.in.Controller;
import edu.wpi.first.wpilibj.Solenoid;

public class Rake {

    public Rake() {
        extendCylinders = false;
        rakeSolenoid1 = new Solenoid(11, 1);
        rakeSolenoid2 = new Solenoid(11, 0);

    }

    public boolean extendCylinders;
    public Solenoid rakeSolenoid1;
    public Solenoid rakeSolenoid2;

    public void update() {
        if (Controller.Button.START.isDown())
            extendCylinders = false;
        else if (Controller.Button.Y.isDownOnce())
            extendCylinders = !extendCylinders;

        rakeSolenoid1.set(extendCylinders);
        rakeSolenoid2.set(extendCylinders);
    }

}