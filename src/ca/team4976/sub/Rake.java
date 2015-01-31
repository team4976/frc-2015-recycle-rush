
package ca.team4976.sub;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Rake {
	
    public void robotInit() {
    	buttonWasReleased = true;
    	extendCylinders = false;
    	rakeSolenoid = new Solenoid(11, 1);
    	controller = new Joystick(1);
    	beginTime = System.currentTimeMillis();
    }

   
    public void autonomousPeriodic() {
    	
    }
    
    public boolean buttonWasReleased, extendCylinders;
    public Solenoid rakeSolenoid;
    public Joystick controller;
	public long beginTime;
    
	public void teleopPeriodic() {
       if (checkControllerOnce(1) & hasItBeen(1000))
 		  extendCylinders = !extendCylinders;
 	  
 	  rakeSolenoid.set(extendCylinders);
    }

	public boolean hasItBeen(long millis) {
		long timeSince = System.currentTimeMillis() - beginTime;
		
		if (timeSince >= millis) {
			beginTime = System.currentTimeMillis();
			return true;
		}
		
		return false;
	}

	public boolean checkControllerOnce(int i) {
    	boolean buttonDown = controller.getRawButton(i);
    	
    	if (buttonWasReleased & buttonDown) {
    		buttonWasReleased = false;
    		return true;
    		
    	}
    	
    	else if (!buttonWasReleased & !buttonDown) {
    		buttonWasReleased = true;
    	}
    	
    	return false;
	}
    
}