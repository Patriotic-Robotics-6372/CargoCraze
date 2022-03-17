package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Date: 10/20/21
 * Author: Na Zyia Nelson
 * Subsystem : intake
 */

public class Intake {

    private DcMotor intake;

    public enum IntakeState {
        IDLE, FORWARD, BACKWARD
    }

    private IntakeState intakeState = IntakeState.IDLE;

    public Intake(DcMotor i) {
        this.intake = i;
    }

    //spin forward
    public void spinForward(double power) {
        intake.setPower(power);
    }
    //spin backward
    public void spinBackward(double power ) {
        intake. setPower(-power);
    }
    //stop
    public void stopIt() {
        intake. setPower(0);
    }

    public void setState(IntakeState state) {
        intakeState = state;
    }

    public DcMotor getIntake() {
        return intake;
    }

    public IntakeState getState() {
        return intakeState;
    }

}
