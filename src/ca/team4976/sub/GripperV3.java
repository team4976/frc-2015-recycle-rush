package ca.team4976.sub;
import ca.team4976.io.Controller;
import ca.team4976.io.Input;
import ca.team4976.io.Output;

public class GripperV3 {
    boolean isSuckedIn;

    boolean xState, aState;

    boolean leftBumper, rightBumper;
    double  leftTrigger, rightTrigger;

    boolean gripperState, kickerState;

    public GripperV3(){
        xState = false;
        aState = false;

        boolean gripperState = false;
        boolean kickerState = false;

        isSuckedIn = false;

        leftBumper = Controller.Secondary.Button.LEFT_BUMPER.isDown();
        rightBumper = Controller.Secondary.Button.RIGHT_BUMPER.isDown();
        leftTrigger = Controller.Secondary.Trigger.LEFT.value();
        rightTrigger = Controller.Secondary.Trigger.RIGHT.value();
    }

    public void update(Elevator elevator){
        //Primary Controls

        if(Controller.Primary.Button.X.isDownOnce()){
            //pickup an upright container
            xState = !xState;
            gripperState = !gripperState;
            kickerState = !kickerState;
            pickupContainer(elevator, xState);
        }
        else if(Controller.Primary.Button.A.isDownOnce()){
            //pick up a fallen over container
            aState = !aState;
            gripperState = !gripperState;
            kickerState = !kickerState;
            pickupContainer(elevator, aState);
        }
        //Secondary Controls
        if(leftTrigger != 0){
            gripperLeftMotors(leftTrigger * - 1);
        }
        else if (leftBumper){
            gripperLeftMotors(1.0);
        }
        else {
            gripperLeftMotors(0);
        }

        if(rightTrigger != 0){
            gripperRightMotors(rightTrigger);
        }
        else if(rightBumper){
            gripperRightMotors(-1.0);
        }
        else{
            gripperLeftMotors(0.0);
        }
    }
    //reset function called in main when start button is pressed
    public void reset(Elevator elevator){
        gripperMotors(0.0, 0.0);
        toggleGripper(false);
        if(elevator.getCurrentLevel() >= 1){
            toggleKicker(false);
        }
        else{
            elevator.elevatorToLevel(1);
            if(elevator.getCurrentLevel() == 1){
                toggleKicker(false);
                elevator.elevatorToLevel(0);
            }
        }
    }
    //simplifies output to the gripper pneumatic
    void toggleGripper(boolean state){
        Output.PneumaticSolenoid.GRIPPER_PNEUMATIC.set(state);
    }
    //simplifies output to the kicker pneumatic
    void toggleKicker(boolean state){
        Output.PneumaticSolenoid.GRIPPER_KICKER.set(state);
    }
    //Simplifies output to the left Gripper Motor
    void gripperLeftMotors(double speed){
        Output.Motor.GRIPPER_LEFT.set(speed);
    }
    //Simplifies output to the Right Gripper motor
    void gripperRightMotors(double speed){
        Output.Motor.GRIPPER_RIGHT.set(speed);
    }
    //Sets speed value to both motors simultaneously
    void gripperMotors(double speedLeft,double speedRight){
        Output.Motor.GRIPPER_LEFT.set(speedLeft);
        Output.Motor.GRIPPER_RIGHT.set(speedRight);
    }

    //Picks up a container
    void pickupContainer(Elevator elevator, boolean state) {
        if (state) {
            toggleGripper(gripperState);
            gripperMotors(1.0, -1.0);
            if (elevator.getCurrentLevel() >= 1) {
                toggleKicker(kickerState);
                if(Input.Digital.GRIPPER_LASER.get()){
                    gripperMotors(0.0,0.0);
                }
            } else {
                elevator.elevatorToLevel(1);
                if (elevator.getCurrentLevel() == 1) {
                    toggleKicker(kickerState);
                    elevator.elevatorToLevel(0);
                    if(Input.Digital.GRIPPER_LASER.get()){
                        gripperMotors(0.0,0.0);
                    }
                }
            }
        } else {
            reset(elevator);
        }
    }
}
