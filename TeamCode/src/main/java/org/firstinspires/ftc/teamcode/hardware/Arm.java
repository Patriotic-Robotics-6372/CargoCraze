package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Arm implements Constants {

    private Servo arm;
    private double pos;

    public enum ArmState {
        START, EXTEND, MANUAL, END
    }

    ElapsedTime extendTime;

    private ArmState armstate = ArmState.START;


    public Arm(Servo a) {
        arm = a;

        arm.setDirection(Servo.Direction.FORWARD);
        arm.setPosition(armStartPos);
    }

    public void start() {
        pos = armStartPos;
        arm.setPosition(pos);
    }

    public void extend() {
        pos = armExtendPos;
        arm.setPosition(pos);
    }

    public void rest() {
        pos = armRestPos;
        arm.setPosition(pos);
    }

    public void increase() {
        pos += 5 / 300.0;
        checkPos();
    }

    public void decrease() {
        pos -= 5 / 300.0;
        checkPos();
    }

    public void fullExtend() {
        extend();
        start();
    }

    public void setPos(double pos) {
        this.pos = pos;
        checkPos();
    }

    public void updatePos() {
        arm.setPosition(pos);
    }

    public void checkPos() {
        if (pos > 1) pos = 1;
        if (pos < 0) pos = 0;
    }

    public double getPos() {
        return pos;
    }

    public Servo getArm() {
        return arm;
    }

    public ArmState getArmstate() {
        return armstate;
    }

    public void setArmstate(ArmState state) {
        armstate =  state;
    }

    public ElapsedTime getExtendTime() {
        return extendTime;
    }

    public void setExtendTime(ElapsedTime time) {
        extendTime = time;
    }
}
