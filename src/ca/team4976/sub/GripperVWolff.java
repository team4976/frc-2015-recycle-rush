package ca.team4976.sub;

import ca.team4976.io.Controller;
import ca.team4976.io.Output;

public class GripperVWolff {

    // aMode and xMode keep track of what mode the gripper and kicker are in.
    boolean aMode, xMode;
    // wheelsRotOut and wheelsRotIn keep track of what direction the wheels on the gripper are rotating in.
    boolean wheelsRotOut, wheelsRotIn;
    // kickerExtended and gripperExtended keep track of the state of the pneumatics that control the gripper and kicker.
    boolean kickerExtended, gripperExtended;
    // transition just keeps track of when the gripper, kicker, and elevator are in mid-transition.
    boolean transition;
    // secondary keeps track of if the second controller is being used.
    boolean secondary;

    // These keep track of the triggers and bumpers on the secondary controller.
    double leftTrigger, rightTrigger;
    boolean leftBumper, rightBumper;
    
    public GripperVWolff() {

        // Set all the booleans above to false.
        aMode = false;
        xMode = false;
        kickerExtended = false;
        gripperExtended = false;
        wheelsRotOut = false;
        wheelsRotIn = false;
        transition = false;

        leftTrigger = 0.0;
        rightTrigger = 0.0;
        leftBumper = false;
        rightBumper = false;
    }

    /**
     *  update method (recieves elevator object)
     */
    public void update(Elevator elevator) {

        //Update triggers and bumpers for the second controller
        leftTrigger = Controller.Secondary.Trigger.LEFT.value();
        rightTrigger = Controller.Secondary.Trigger.RIGHT.value();
        leftBumper = Controller.Secondary.Button.LEFT_BUMPER.isDown();
        rightBumper = Controller.Secondary.Button.RIGHT_BUMPER.isDown();
        
        if (Controller.Primary.Button.START.isDownOnce() || Controller.Secondary.Button.START.isDownOnce()) {
            // Set the transition to true only if the elevator is at level 0.
            if (elevator.withinThreshold(0)) {
                transition = true;
            }
            // Set the wheels and modes to false.
            wheelsRotOut = false;
            wheelsRotIn = false;
            aMode = false;
            xMode = false;
        }
        
        // Primary
        
        if (Controller.Primary.Button.X.isDownOnce()) {
            secondary = false;
            // Set the transition to true only if the elevator is at level 0.
            if (elevator.withinThreshold(0)) {
                transition = true;
            }
            // Set aMode to false.
            aMode = false;
            // If xMode was true before, set it to false and stop the wheels from rotating.
            if (xMode) {
                wheelsRotOut = false;
                wheelsRotIn = false;
                xMode = false;
            }
            // if xMode was false before, set it to true and rotate the wheels in to the robot.
            else {
                wheelsRotOut = false;
                wheelsRotIn = true;
                xMode = true;
            }
        }
        else if (Controller.Primary.Button.A.isDownOnce()) {
            secondary = false;
            // Set the transition to true only if the elevator is at level 0.
            if (elevator.withinThreshold(0)) {
                transition = true;
            }
            // Set xMode to false.
            xMode = false;
            // If AMode was true before, set it to false and stop the wheels from rotating.
            if (aMode) {
                wheelsRotOut = false;
                wheelsRotIn = false;
                aMode = false;
            }
            // if aMode was false before, set it to true and rotate the wheels in to the robot.
            else {
                wheelsRotOut = false;
                wheelsRotIn = true;
                aMode = true;
            }
        }
        if (Controller.Primary.Button.Y.isDownOnce()) {
            secondary = false;
            // Stop the wheels from rotating in.
            wheelsRotIn = false;
            // If the wheels were rotating out before, stop them.
            if (wheelsRotOut) {
                wheelsRotOut = false;
            }
            // If the wheels were not rotating out before, rotate them out.
            else {
                wheelsRotOut = true;
            }
        }
        else if (Controller.Primary.Button.B.isDownOnce()) {
            secondary = false;
            // Stop the wheels from rotating out.
            wheelsRotOut = false;
            // If the wheels were rotating in before, stop them.
            if (wheelsRotIn) {
                wheelsRotIn = false;
            }
            // If the wheels were not rotating in before, rotate them in.
            else {
                wheelsRotIn = true;
            }
        }
        
        // Secondary

        if (leftTrigger > 0){
            secondary = true;
            Output.Motor.GRIPPER_LEFT.set(leftTrigger * -1);
        }
        else if (leftBumper) {
            secondary = true;
            Output.Motor.GRIPPER_LEFT.set(1.0);
        }
        else if (secondary) {
            Output.Motor.GRIPPER_LEFT.set(0);
        }

        if (rightTrigger > 0){
            secondary = true;
            Output.Motor.GRIPPER_RIGHT.set(rightTrigger);
        }
        else if (rightBumper) {
            secondary = true;
            Output.Motor.GRIPPER_RIGHT.set(-1.0);
        }
        else if (secondary) {
            Output.Motor.GRIPPER_RIGHT.set(0);
        }

        if (Controller.Secondary.Button.X.isDownOnce()) {
            secondary = true;
            gripperExtended = !gripperExtended;
        }
        else if (Controller.Secondary.Button.A.isDownOnce()) {
            secondary = true;
            kickerExtended = !kickerExtended;
        }
        
        // Processing
        
        if (!secondary) {

            // If xMode is true
            if (xMode) {
                // Set the gripper to be extended since it is independent from the elevator.
                gripperExtended = true;
                // If the kicker is extended OR there is a transition
                if (kickerExtended || transition) {
                    // If the kicker is extended AND the elevator is at position 100 or higher, set the kicker to not be extended.
                    if (kickerExtended && elevator.pastThreshold(1)) {
                        kickerExtended = false;
                    }
                    // If the kicker is extended and the elevator's level is 0, request the elevator to move to level 1.
                    else if (kickerExtended && elevator.withinThreshold(0)) {
                        elevator.elevatorToLevel(1);
                    }
                    // If the kicker is not extended AND the elevator is at position 100 or higher, AND there is a transition, 
                    // request the elevator to move back down to level 0, and end the transition.
                    else if (!kickerExtended && elevator.pastThreshold(1) && transition) {
                        elevator.elevatorToLevel(0);
                        transition = false;
                    }
                }
            }
            // If aMode is true
            else if (aMode) {
                // Set the gripper to be extended since it is independent from the elevator.
                gripperExtended = true;
                // If the kicker is not extended OR there is a transition
                if (!kickerExtended || transition) {
                    // If the kicker is not extended and the elevator is at position 100 or higher, extend the kicker.
                    if (!kickerExtended && elevator.pastThreshold(1)) {
                        kickerExtended = true;
                    }
                    // If the kicker is not extended and the elevator is at level 0, request the elevator to move to level 1.
                    else if (!kickerExtended && elevator.withinThreshold(0)) {
                        elevator.elevatorToLevel(1);
                    }
                    // If the kicker is extended and the elevator is at position 100 or higher, AND there is a transition,
                    // request the elevator to move back down to level 0, and end the transition.  
                    else if (kickerExtended && elevator.pastThreshold(1) && transition) {
                        elevator.elevatorToLevel(0);
                        transition = false;
                    }
                }
            }
            // If neither xMode or aMode are true
            else {
                // Set the gripper to not be extended since it is independent from the elevator.
                gripperExtended = false;
                // Stop the wheels from rotating since the gripper will not be extended.
                wheelsRotOut = false;
                wheelsRotIn = false;
                // If the kicker is extended OR there is a transition
                if (kickerExtended || transition) {
                    // If the kicker is extended AND the elevator is at position 100 or higher, set the kicker to not be extended.
                    if (kickerExtended && elevator.pastThreshold(1)) {
                        kickerExtended = false;
                    }
                    // If the kicker is extended and the elevator's level is 0, request the elevator to move to level 1.
                    else if (kickerExtended && elevator.withinThreshold(0)) {
                        elevator.elevatorToLevel(1);
                    }
                    // If the kicker is not extended AND the elevator is at position 100 or higher, AND there is a transition, 
                    // request the elevator to move back down to level 0, and end the transition.
                    else if (!kickerExtended && elevator.pastThreshold(1) && transition) {
                        elevator.elevatorToLevel(0);
                        transition = false;
                    }
                }
            }
            Output.PneumaticSolenoid.GRIPPER_PNEUMATIC.set(gripperExtended);
            Output.PneumaticSolenoid.GRIPPER_KICKER.set(kickerExtended);

            if (wheelsRotIn) {
                Output.Motor.GRIPPER_LEFT.set(1.0);
                Output.Motor.GRIPPER_RIGHT.set(-1.0);
            }
            else if (wheelsRotIn) {
                Output.Motor.GRIPPER_LEFT.set(-1.0);
                Output.Motor.GRIPPER_RIGHT.set(1.0);
            }
            else {
                Output.Motor.GRIPPER_LEFT.set(0.0);
                Output.Motor.GRIPPER_RIGHT.set(0.0);
            }
        }
        else {
            Output.PneumaticSolenoid.GRIPPER_PNEUMATIC.set(gripperExtended);
            Output.PneumaticSolenoid.GRIPPER_KICKER.set(kickerExtended);
        }

    }
    
}
