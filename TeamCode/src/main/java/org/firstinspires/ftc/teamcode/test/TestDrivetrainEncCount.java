package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain;

@Disabled
@TeleOp(name = "TestDrivetrainEnc")
public class TestDrivetrainEncCount extends LinearOpMode {
    Drivetrain drive;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drivetrain(hardwareMap.dcMotor.get("frontLeft"),
                hardwareMap.dcMotor.get("frontRight"),
                hardwareMap.dcMotor.get("backLeft"),
                hardwareMap.dcMotor.get("backRight"));
        waitForStart();
        while (opModeIsActive()) {
            if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
                drive.setBase(gamepad1.right_stick_y, -gamepad1.left_stick_y, gamepad1.right_stick_y, -gamepad1.left_stick_y);
            } else if (gamepad1.dpad_down) {
                drive.forward(1);
            } else if (gamepad1.dpad_up) {
                drive.backward(1);
            }
            else {
                drive.stop();
            }
            telemetry.addData("fL", drive.getFrontLeft().getCurrentPosition());
            telemetry.addData("fR", drive.getFrontRight().getCurrentPosition());
            telemetry.addData("bL", drive.getBackLeft().getCurrentPosition());
            telemetry.addData("bR", drive.getBackRight().getCurrentPosition());
            telemetry.update();
        }
    }
}
