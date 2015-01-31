
package ca.team4976.sub;

import ca.team4976.in.Controller;
import edu.wpi.first.wpilibj.Solenoid;

public class Rake {

    public Rake() {
        extendCylinders = false;
        rakeSolenoid = new Solenoid(11, 1);
    }

    public boolean extendCylinders;
    public Solenoid rakeSolenoid;

    public void update() {
        if (Controller.Button._360_Y.isDownOnce()) {
            extendCylinders = !extendCylinders;
            rakeSolenoid.set(extendCylinders);
        }
    }

}