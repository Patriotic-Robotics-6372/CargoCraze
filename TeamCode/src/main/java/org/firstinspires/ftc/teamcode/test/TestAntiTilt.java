package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp (name = "TestAntiTilt", group = "Test")
public class TestAntiTilt extends LinearOpMode {
    Robot prbot = new Robot();
    double mult = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        prbot.init(hardwareMap, telemetry);
        prbot.setMode(Constants.Status.AUTO);

        telemetry.setMsTransmissionInterval(20);

        waitForStart();

        while (opModeIsActive()) {
            if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
                prbot.drivetrain.setBase(gamepad1.right_stick_y - gamepad1.left_trigger, gamepad1.left_stick_y - gamepad1.left_trigger, gamepad1.right_stick_y - gamepad1.left_trigger, gamepad1.left_stick_y - gamepad1.left_trigger);
            } else if (gamepad1.dpad_up) {
                prbot.drivetrain.setBase(-1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger);
            } else if (gamepad1.dpad_down) {
                prbot.drivetrain.setBase(1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger);
            } else {
                prbot.drivetrain.stop();
            }
            if (Math.abs(prbot.imu.getSecondAngleNum()) > 5) {

            }
            telemetry.addData("Roll", prbot.imu.getSecondAngle());
        }
    }
}
