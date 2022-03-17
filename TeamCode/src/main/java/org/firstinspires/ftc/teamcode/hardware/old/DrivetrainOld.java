package org.firstinspires.ftc.teamcode.hardware.old;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Date: 10/5/2021
 * Author: Amare Askerneese
 * Subsystem: Drivetrain :o
 */

public class DrivetrainOld {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    //cableTies

    public DrivetrainOld(DcMotor fL, DcMotor fR, DcMotor bL, DcMotor bR) {

        this.frontLeft = fL;
        this.frontRight = fR;
        this.backLeft = bL;
        this.backRight = bR;
    }


    public void forward(double speed) {
        move(speed, -speed, speed, -speed);
    }

    public void backward(double speed) {
        move(-speed, speed, -speed, speed);
    }

    public void turnRight(double speed) {
        move(speed, 0, speed, 0);
    }

    public void turnLeft(double speed) {
        move(0, speed, 0, speed);
    }

    public void stopMotors() {
        move(0, 0, 0, 0);
    }

//Will eventually add code for exact movements? Still need to add encoders :p


    public void move (double fL, double fR, double bL, double bR) {
        frontLeft.setPower(fL);
        frontRight.setPower(fR);
        backLeft.setPower(bL);
        backRight.setPower(bR);
    }
}
