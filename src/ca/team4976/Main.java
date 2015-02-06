package ca.team4976;

import ca.team4976.in.DigitalInputs;
import ca.team4976.in.Encoders;
import ca.team4976.in.Gyros;
import ca.team4976.in.Controller;
import ca.team4976.out.Motors;
import ca.team4976.sub.DriveTrain;
import ca.team4976.sub.Elevator;
import ca.team4976.sub.Gripper;
import ca.team4976.sub.Rake;
import edu.wpi.first.wpilibj.*;

public class Main extends IterativeRobot {

    DriveTrain drive;

    Elevator elevator;
    Gripper gripper;
    Rake rake;

    public void robotInit() {
        Gyros.gyros[1] = new Gyro(1);

        //Encoders.encoders[0] = new Encoder(0, 1, 2);
        //Encoders.encoders[1] = new Encoder(3, 4, 5);

        Motors.setDefaultMotors();

        DigitalInputs.setDefaultInputs();

        Controller.setInputPort(0);

        elevator = new Elevator();
        rake = new Rake(11, 0, 1);
        gripper = new Gripper(11, 2, 3, Motors.GRIPPER_MOTOR_LEFT, Motors.GRIPPER_MOTOR_RIGHT);
    }

    public void disabledInit() {

    }

    public void teleopInit() {
        Gyros.gyros[1].reset();
        //Encoders.encoders[0].reset();
    }

    public void autonomousInit() {
    }

    public void testInit() {

    }

    public void disabledPeriodic() {
    }

    public void teleopPeriodic() {
        rake.update();
        gripper.update();
        elevator.update();
    }

    public void autonomousPeriodic() {

    }

    public void testPeriodic() {

    }

}
