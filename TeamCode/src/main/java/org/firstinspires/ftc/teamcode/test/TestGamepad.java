package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "TestGamepad", group = "Test")
public class TestGamepad extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Gamepad 1", gamepad1.toString());
            telemetry.addData("Gamepad 2", gamepad2.toString());
            telemetry.update();
        }
    }
}
