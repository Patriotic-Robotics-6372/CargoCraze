package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

/*
    Author: Jacob Marinas
    Date: 1/17/22
    Desc: Controller class for additional gamepad functionality
 */
public class Controller implements Constants {

    private ArrayList<Button> inputs = new ArrayList<Button>();
    public Button left_bumper, right_bumper, right_trigger, dpad_up_2, dpad_down_2, dpad_right_2, dpad_left_2, left_trigger_2, right_trigger_2, x, a, y, b, left_stick_button_2, left_stick_button;
    private Gamepad gp1, gp2;

    /*
    inputs[0]:
     */
    public Controller(Gamepad gp1, Gamepad gp2) {
        /*
        for (int i = 0; i < 10; i++) {
            inputs.add(new Button());
        }
         */
        this.gp1 = gp1;
        this.gp2 = gp2;
        left_bumper = new Button();
        right_bumper = new Button();
        right_trigger = new Button();
        dpad_up_2 = new Button();
        dpad_down_2 = new Button();
        dpad_right_2 = new Button();
        dpad_left_2 = new Button();
        left_trigger_2 = new Button();
        right_trigger_2 = new Button();
        x = new Button();
        a = new Button();
        y = new Button();
        b = new Button();
        left_stick_button_2 = new Button();
        left_stick_button = new Button();
        inputs.add(left_bumper);
        inputs.add(right_bumper);
        inputs.add(right_trigger);
        inputs.add(dpad_up_2);
        inputs.add(dpad_down_2);
        inputs.add(dpad_right_2);
        inputs.add(dpad_left_2);
        inputs.add(left_trigger_2);
        inputs.add(right_trigger_2);
        inputs.add(x);
        inputs.add(a);
        inputs.add(y);
        inputs.add(b);
        inputs.add(left_stick_button_2);
        inputs.add(left_stick_button);
    }

    public void addInput(Button input) {
        inputs.add(input);
    }

    /*
    public Button getInput(String name) {
        switch (name) {

        }
    }

     */

    public void updateInputs() {
        for (Button in : inputs) {
            in.previous();
        }
        left_bumper.setState(gp1.left_bumper);
        right_bumper.setState(gp1.right_bumper);
        right_trigger.setState(gp1.right_trigger > .1);
        dpad_up_2.setState(gp2.dpad_up);
        dpad_down_2.setState(gp2.dpad_down);
        dpad_right_2.setState(gp2.dpad_right);
        dpad_left_2.setState(gp2.dpad_left);
        left_trigger_2.setState(gp2.left_trigger > .1);
        right_trigger_2.setState(gp2.right_trigger > .1);
        x.setState(gp1.x);
        a.setState(gp1.a);
        y.setState(gp1.y);
        b.setState(gp1.b);
        left_stick_button_2.setState(gp2.left_stick_button);
        left_stick_button.setState(gp1.left_stick_button);
    }
}
