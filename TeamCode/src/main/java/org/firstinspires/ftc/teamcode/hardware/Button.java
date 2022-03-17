package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Date: 2/15/20
 * Author: Jacob Marinas
 * Lets buttons have greater functionality. Use this class to tell if the button has been pressed, held, or released.
 */
//@Disabled
public class Button {

    private boolean currentState, previousState;
    private int states = 2;
    private boolean toggle = false;

    public Button() {

    }

    public Button(int states) {
        this.states = states;
    }

    /**
     * Sets currentState to true
     */
    public void pressed() {
        currentState = true;
    }

    /**
     * Sets previousState to currentState
     */
    public void previous() {
        previousState = currentState;
    }

    /**
     * Sets currentState to false
     */
    public void released() {
        currentState = false;
    }

    /**
     * @param state of currentState
     */
    public void setState(boolean state) {
        currentState = state;
    }

    /**
     * When method is true, it inverts the toggle
     * @return true if button has been pressed on the first tick, false otherwise
     */
    public boolean isPressed() {
        if (currentState == true && previousState == false) {
            toggle = !toggle;
        }
        return currentState == true && previousState == false;
    }

    /**
     * @return true if button has been pressed for more than one tick, false otherwise
     */
    public boolean isHeld() {
        return currentState == true && previousState == true;
    }

    /**
     * @return true if button has been released for one tick, false otherwise
     */
    public boolean isReleased() {
        return currentState == false && previousState == true;
    }

    /**
     * @return currentState
     */
    public boolean getCurrentState() {
        return currentState;
    }

    /**
     * @return previousState
     */
    public boolean getPreviousState() {
        return previousState;
    }

    /**
     * @return toggle; on or off
     */
    public boolean getToggle() {
        return toggle;
    }
}
