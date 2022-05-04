package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Controller;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Outtake;

/*
    Author: Jacob Marinas
    Date: 1/17/22
    Desc: Test Lift class using states
 */
@TeleOp (name = "TestLiftStates", group = "PRTest")
public class TestLiftStates extends LinearOpMode {

    Lift lift;
    Outtake outtake;
    Drivetrain drivetrain;

    Controller c;

    @Override
    public void runOpMode() throws InterruptedException {
        lift = new Lift(hardwareMap.dcMotor.get("lift"));
        outtake = new Outtake(hardwareMap.servo.get("outtake"));
        drivetrain = new Drivetrain(hardwareMap.dcMotor.get("frontLeft"),
                hardwareMap.dcMotor.get("frontRight"),
                hardwareMap.dcMotor.get("backLeft"),
                hardwareMap.dcMotor.get("backRight"));

        // Gives additional information about inputs
        c = new Controller(gamepad1, gamepad2);

        telemetry.setMsTransmissionInterval(20);

        lift.init();
        lift.useEncoders(true);
        lift.useBrake(true);
        lift.setMaxPower(.3);

        // Timer for extension
        lift.setTimer(new ElapsedTime());

        waitForStart();

        lift.getTimer().reset();

        while (opModeIsActive()) {
            // Refresh gamepad inputs and update information
            c.updateInputs();
            switch (lift.getState()) {
                case START:
                    // If lift pos = LEVEL_ZERO, use START state
                    if (!lift.getLift().isBusy()) {
                        lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        lift.stopLift();
                        outtake.forwardPosition();
                    }
                    // If gamepad2.dpad_up pressed, start EXTEND state
                    if (c.dpad_up_2.isPressed()) {
                        lift.getLift().setTargetPosition(Constants.LEVEL_FOUR);
                        lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift.up(.9);

                        outtake.neutralPosition();

                        lift.setState(Lift.LiftState.EXTEND);
                    }
                    break;
                case EXTEND:
                    // If lift is done extending, DUMP
                    if (!lift.getLift().isBusy()) {
                        lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        lift.stopLift();

                        outtake.backPosition();

                        lift.getTimer().reset();

                        lift.setState(Lift.LiftState.DUMP);
                    }
                    break;
                case DUMP:
                    // After certain amount of time, finish dump and RETRACT
                    if (lift.getTimer().milliseconds() >= Constants.DUMP_TIME) {
                        outtake.neutralPosition();
                        lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
                        lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift.down(.6);

                        lift.setState(Lift.LiftState.RETRACT);
                    }
                    break;
                case RETRACT:
                    // If lift is done retracting, reset back to START state
                    if (!lift.getLift().isBusy()) {
                        lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        lift.stopLift();

                        outtake.forwardPosition();

                        lift.setState(Lift.LiftState.START);
                    }
                    break;
                default:
                    lift.setState(Lift.LiftState.START);
            }

            if (c.dpad_down_2.isPressed() && lift.getState() != Lift.LiftState.START) {
                // If gamepad2.dpad_down pressed, interrupt and reset to START state (neutral hopper)
                lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                outtake.neutralPosition();
                lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
                lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.down(.3);
                lift.setState(Lift.LiftState.START);
            }

            if (c.dpad_right_2.isPressed() && lift.getState() != Lift.LiftState.START) {
                // If gamepad2.dpad_right pressed, interrupt and reset to START state (backward hopper)
                lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                outtake.backPosition();
                lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
                lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.down(.3);
                lift.setState(Lift.LiftState.START);
            }

            // Test to see if drivetrain works while lift is running
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

            telemetry.addData("Lift State", lift.getState());
            telemetry.addData("Outtake Timer", lift.getTimer().milliseconds());
            telemetry.update();
        }
    }
}
