package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Controller;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Intake;

/*
    Author: Jacob Marinas
    Date: 1/17/22
    Desc: Test Intake class using states
 */
@TeleOp (name = "TestIntakeStates", group = "PRTest")
public class TestIntakeStates extends LinearOpMode {

    Intake intake;
    Drivetrain drivetrain;

    Controller c;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap.dcMotor.get("intake"));
        drivetrain = new Drivetrain(hardwareMap.dcMotor.get("frontLeft"),
                hardwareMap.dcMotor.get("frontRight"),
                hardwareMap.dcMotor.get("backLeft"),
                hardwareMap.dcMotor.get("backRight"));

        // Gives additional information about inputs
        c = new Controller(gamepad1, gamepad2);

        waitForStart();

        while (opModeIsActive()) {
            // Refresh gamepad inputs and update information
            c.updateInputs();
            switch (intake.getState()) {
                case IDLE:
                    // instead of true, check if liftState = START
                    if (c.left_trigger_2.isPressed() && true) {
                        intake.spinForward(1);
                        intake.setState(Intake.IntakeState.FORWARD);
                    } else if (c.right_trigger_2.isPressed()) {
                        intake.spinBackward(1);
                        intake.setState(Intake.IntakeState.BACKWARD);
                    }
                    break;
                case FORWARD:
                    // Press gamepad1.left_trigger once to turn on, twice to turn off
                    if (c.left_trigger_2.isPressed()) {
                        intake.stopIt();
                        intake.setState(Intake.IntakeState.IDLE);
                    }
                    if (c.right_trigger_2.isPressed()) {
                        intake.spinBackward(1);
                        intake.setState(Intake.IntakeState.BACKWARD);
                    }
                    break;
                case BACKWARD:
                    // Press gamepad1.right_trigger once to turn on, twice to turn off
                    if (c.right_trigger_2.isPressed()) {
                        intake.stopIt();
                        intake.setState(Intake.IntakeState.IDLE);
                    }
                    if (c.left_trigger_2.isPressed() && true) {
                        intake.spinForward(1);
                        intake.setState(Intake.IntakeState.FORWARD);
                    }
                    break;
                default:
                    intake.setState(Intake.IntakeState.IDLE);
            }

            // Test to see if drivetrain works while intake is on
            if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
                drivetrain.setBase(gamepad1.right_stick_y, gamepad1.left_stick_y, gamepad1.right_stick_y, gamepad1.left_stick_y);
            } else if (gamepad1.dpad_up) {
                drivetrain.setBase(-1, -1, -1, -1);
            } else if (gamepad1.dpad_down) {
                drivetrain.setBase(1, 1, 1, 1);
            }
            else {
                drivetrain.stop();
            }

            telemetry.addData("Intake State", intake.getState());
            telemetry.update();
        }
    }
}
