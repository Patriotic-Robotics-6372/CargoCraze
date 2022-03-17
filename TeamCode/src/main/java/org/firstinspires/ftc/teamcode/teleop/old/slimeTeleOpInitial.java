package org.firstinspires.ftc.teamcode.teleop.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "slimeTeleOpInitial", group = "PRTest")
@Disabled
public class slimeTeleOpInitial extends OpMode {

    //Drivetrain
    DcMotor frontLeft, frontRight, backLeft, backRight;

    //Intake
    //DcMotor intake;

    //Outtake
    //CRServo outtake;

    //Lift
    //DcMotor lift;

   //Carousel
    DcMotor carouselLeft;
    // carouselRight

    @Override
    public void init() {

        //DT
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        /*
        //Intake
        intake = hardwareMap.dcMotor.get("intake");

        //Outtake
        outtake = hardwareMap.crservo.get("outtake");
        //Lift
        lift = hardwareMap.dcMotor.get("lift");
        //Carousel
        carouselRight = hardwareMap.dcMotor.get("rightCoursel");
         */
        carouselLeft = hardwareMap.dcMotor.get("leftCarousel");
    }

    @Override
    public void loop() {

//        Movement
//

        if (Math.abs(gamepad1.right_stick_y) > 0.1) {
                frontLeft.setPower(gamepad1.right_stick_y);
                backLeft.setPower(gamepad1.right_stick_y);
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }

        if (Math.abs(gamepad1.left_stick_y) > 0.1) {
                frontRight.setPower(-gamepad1.left_stick_y);
                backRight.setPower(-gamepad1.left_stick_y);
        } else {
            frontRight.setPower(0);
            backRight.setPower(0);
        }

//        Intake

//        if (gamepad1.left_trigger > 0.1) {
//            intake.setPower(0.2);
//        } else {
//            intake.setPower(0);
//        }

//        Carousel

        if (gamepad1.left_bumper) {
            carouselLeft.setPower(0.5);
        }   else {
            carouselLeft.setPower(0);
        }

    }




    }

