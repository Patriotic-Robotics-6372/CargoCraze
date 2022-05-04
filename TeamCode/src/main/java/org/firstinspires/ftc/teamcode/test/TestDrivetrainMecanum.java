package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name = "MechanumTest", group = "Test")
public class TestDrivetrainMecanum extends LinearOpMode {

    Drivetrain prbot;
    DcMotor intake;

    double rightSlide;
    double leftSlide;
    double leftMove;
    double rightMove;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = hardwareMap.dcMotor.get("intake");
        prbot = new Drivetrain(hardwareMap.dcMotor.get("frontLeft"), hardwareMap.dcMotor.get("frontRight"), hardwareMap.dcMotor.get("backLeft"), hardwareMap.dcMotor.get("backRight"));

        waitForStart();
        while (opModeIsActive()) {
            rightSlide = -gamepad1.right_stick_x;
            leftSlide = gamepad1.left_stick_x;
            rightMove = gamepad1.right_stick_y;
            leftMove = gamepad1.left_stick_y;
            //prbot.getDrivetrain().setRightSide(rightSlide + rightMove, -rightSlide + rightMove);
            //prbot.getDrivetrain().setLeftSide(-leftSlide + leftMove, leftSlide + leftMove);
            prbot.setBase(-leftSlide + leftMove, rightSlide + rightMove, leftSlide + leftMove, -rightSlide + rightMove);
            if (gamepad1.right_trigger > .1) {
                intake.setPower(-1);
            } else {
                intake.setPower(0);
            }
        }
    }
}
