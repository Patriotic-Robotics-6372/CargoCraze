package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class NaZyiaTeleOp {
    @TeleOp(name = "nazyiaTeleOp")
    @Disabled
    public class nazyiaTeleOp extends OpMode {

        //drivetrain
        DcMotor frontLeft, frontRight, backLeft, backRight;

        //intake
        DcMotor intake;

        //outake
        CRServo outake;
        //Servo

        //lift
        public DcMotor lift;

        @Override
        public void init() {

            //motors
            frontLeft = hardwareMap.dcMotor.get("frontLeft");
            frontRight = hardwareMap.dcMotor.get("frontRight");
            backLeft = hardwareMap.dcMotor.get("backLeft");
            backRight = hardwareMap.dcMotor.get("backRight");
            intake = hardwareMap.dcMotor.get ("intake");

            // servos
            outake = hardwareMap.crservo.get("outake");



        }

        @Override
        public void loop() {

            //code for base
            //left side
            if (Math.abs(gamepad1.left_stick_y ) > 0.1 ) {
                frontLeft.setPower(gamepad1.right_stick_y);
                backLeft.setPower(gamepad1.left_stick_y);
            } else {
                frontLeft.setPower(0);
                backLeft.setPower(0);
            }
            // right side
            if (Math.abs(gamepad1.right_stick_y) > 0.1 ) {
                frontRight.setPower(-gamepad1.right_stick_y);
                backRight.setPower(-gamepad1.right_stick_y);
            } else {
                frontRight. setPower(0);
                backRight.setPower(0);

            }
                //intake
            if (gamepad1.dpad_up) {
                intake.setPower(1);
            } else if (gamepad1.dpad_down) {
                intake.setPower( -1);
            } else {
                intake.setPower(0);
            }
            //outake
            //positioner
            /*
            if (gamepad1.b) {
                setPosition(1);

            }
             */
        }
    }
}
