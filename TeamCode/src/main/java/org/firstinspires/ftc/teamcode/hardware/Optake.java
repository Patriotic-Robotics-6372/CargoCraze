package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Date: 10/20/21
 * Author: Na Zyia Nelson
 * Subsystem : intake
 */

public class Optake {

    private DcMotor optake;
    private double speed = 1;

    public enum OptakeState {
        IDLE, FORWARD, BACKWARD
    }

    private OptakeState optakeState = OptakeState.IDLE;

    public Optake(DcMotor i) {
        this.optake = i;
    }

    //spin forward
    public void spinForward(double power) {
        optake.setPower(power * speed);
    }
    //spin backward
    public void spinBackward(double power ) {
        optake. setPower(-power * speed);
    }
    //stop
    public void stopIt() {
        optake.setPower(0);
    }

    public void setState(OptakeState state) {
        optakeState = state;
    }

    public DcMotor getIntake() {
        return optake;
    }

    public OptakeState getState() {
        return optakeState;
    }

}
