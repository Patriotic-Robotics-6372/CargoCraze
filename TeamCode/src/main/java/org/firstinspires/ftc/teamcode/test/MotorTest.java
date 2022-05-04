package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Button;

@Autonomous (name = "MotorTest", group = "Test")
public class MotorTest extends LinearOpMode {

    private DcMotor motor;
    public Button dpad_up, dpad_down;
    public double speed = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.dcMotor.get("motor");
        dpad_down = new Button();
        dpad_up = new Button();
        telemetry.addData("Desc", "This programs tests for a DcMotor")
                .addData("How to Use", "Values should update when motor is powered");
        telemetry.update();
        telemetry.setMsTransmissionInterval(20);
        waitForStart();
        while (opModeIsActive()) {
            dpad_down.previous();
            dpad_down.setState(gamepad1.dpad_down);
            dpad_up.previous();
            dpad_up.setState(gamepad1.dpad_up);
            if (dpad_down.isPressed()) {
                speed += .05;
            } else if (dpad_up.isPressed()) {
                speed -= .05;
            }
            motor.setPower(speed);
            telemetry.addData("motorPower", motor.getPower());
            telemetry.addData("motorEnc", motor.getCurrentPosition());
            telemetry.update();
        }
    }
}