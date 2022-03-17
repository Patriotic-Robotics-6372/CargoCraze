package org.firstinspires.ftc.teamcode.auton.old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "slimeAutonInitial", group = "PRTest")
@Disabled
public class slimeAutonInitial extends LinearOpMode {

    //Drivetrain
    DcMotor frontLeft, frontRight, backLeft, backRight;

    //Intake
    DcMotor intake;

    //Outtake
    CRServo outtake;

    //Lift
    DcMotor lift;

    //Carousel
    DcMotor carousel;

    @Override
    public void runOpMode() throws InterruptedException {

        //Driver App Names

        //Drivetrain
        hardwareMap.dcMotor.get("frontLeft");
        hardwareMap.dcMotor.get("frontRight");
        hardwareMap.dcMotor.get("backLeft");
        hardwareMap.dcMotor.get("backRight");

        //Intake
        hardwareMap.crservo.get("intake");

        waitForStart();

        frontLeft.setPower(.2);
        frontRight.setPower(-.2);
        backLeft.setPower(.2);
        backRight.setPower(-.2);

        sleep(2500);

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        sleep(1000);

        intake.setPower(.1);

        sleep(1000);







    }
}
