package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous (name = "TestMotor", group = "Test")
public class TestMotor extends LinearOpMode {

    DcMotor motor;
    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.dcMotor.get("backRight");

        waitForStart();

        while (opModeIsActive()) {

            motor.setPower(gamepad1.left_stick_y);
        }
    }
}
