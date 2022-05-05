package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Carousel;
import org.firstinspires.ftc.teamcode.hardware.CarouselSolo;
import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Controller;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.Robotv2;

import java.util.Optional;

@TeleOp (name = "PRTeleOpDistricts", group = "Teleop")
public class slimeTeleOp5 extends LinearOpMode {

    Robotv2 zoom = new Robotv2();
    Controller c;

    boolean rTToggle = false;
    boolean lbToggle = false;

    ElapsedTime matchTime = new ElapsedTime();

    double rightSlide;
    double leftSlide;
    double leftMove;
    double rightMove;

    @Override
    public void runOpMode() throws InterruptedException {
        zoom.init(hardwareMap, telemetry);
        zoom.setMode(Constants.Status.FAST);

        c = new Controller(gamepad1, gamepad2);

        telemetry.setMsTransmissionInterval(100);
        telemetry.addData("Desc", "");
        telemetry.addData("How to Use", "GP1 | Left Stick: Moves Left Side of Drivetrain\n" +
                "GP2 | Right Stick: Moves Ride Side of Drivetrain\n" +
                "GP2 | Right Trigger: Powers Intake\n" +
                "GP1 | Right Bumper: Right Carousel Spins\n" +
                "GP1 | Left Bumper: Left Carousel Spins\n" +
                "GP2 | X: Moves Hopper Back\n" +
                "GP2 | Y: Moves Hopper to Neutral\n" +
                "GP2 | B: Moves Hopper Forward\n" +
                "GP2 | DPad Up: Moves Lift Up\n" +
                "GP2 | DPad Down: Moves Lift Down");
        telemetry.update();

        zoom.resetTimers();
        matchTime.reset();

        waitForStart();

        while (opModeIsActive()) {

            c.updateInputs();

            // Movement

            rightSlide = -gamepad1.right_stick_x;
            leftSlide = gamepad1.left_stick_x;
            rightMove = -gamepad1.right_stick_y;
            leftMove = -gamepad1.left_stick_y;

            zoom.drivetrain.setBase(-leftSlide + leftMove, rightSlide + rightMove, leftSlide + leftMove, -rightSlide + rightMove);

            // Intake and Optake

            if (gamepad2.right_trigger > 0.1) {
                zoom.intake.spinBackward(1);
                zoom.optake.spinForward(1);
                //zoom.outtake.backPosition();
            } else if (gamepad2.left_trigger > 0.1) {
                zoom.intake.spinForward(1);
                zoom.optake.spinBackward(1);
                //zoom.outtake.forwardPosition();
            } else {
                zoom.intake.stopIt();
                zoom.optake.stopIt();
            }

            // Lift and Outtake


            switch (zoom.lift.getState()) {
                case START:
                    if (!zoom.lift.getLift().isBusy()) {
                        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.lift.stopLift();
                        zoom.outtake.forwardPosition();
                    }
                    if (c.dpad_up_2.isPressed()) {
                        zoom.intake.setState(Intake.IntakeState.IDLE);
                        zoom.lift.getLift().setTargetPosition(Constants.LEVEL_FOUR);
                        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        zoom.lift.up(1);

                        zoom.outtake.neutralPosition();

                        zoom.lift.setState(Lift.LiftState.EXTEND);
                    }
                    if (c.dpad_right_2.isPressed()) {
                        zoom.intake.setState(Intake.IntakeState.IDLE);
                        zoom.lift.getLift().setTargetPosition(Constants.LEVEL_FOUR);
                        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        zoom.lift.up(1);

                        zoom.outtake.neutralPosition();

                        zoom.lift.setState(Lift.LiftState.EXTEND);
                    }
                    break;
                case EXTEND:
                    if (!zoom.lift.getLift().isBusy()) {

                        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.lift.stopLift();

                        if (gamepad2.dpad_down) {
                            zoom.outtake.backPosition();
                            zoom.lift.getTimer().reset();

                            zoom.lift.setState(Lift.LiftState.DUMP);
                        }
                    }
                    break;
                case DUMP:
                    if (zoom.lift.getTimer().milliseconds() >= Constants.DUMP_TIME) {
                        zoom.outtake.neutralPosition();
                        zoom.lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
                        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        zoom.lift.down(1);

                        zoom.lift.getLiftTimeout().reset();
                        zoom.lift.setState(Lift.LiftState.RETRACT);
                    }
                    break;
                case RETRACT:
                    if (!zoom.lift.getLift().isBusy() || zoom.lift.getLiftTimeout().milliseconds() >= Constants.RETRACT_TIMEOUT) {
                        //offset += 10;
                        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.lift.stopLift();

                        zoom.outtake.forwardPosition();

                        zoom.lift.setState(Lift.LiftState.START);
                    }
//                    if (zoom.lift.getLiftTimeout().milliseconds() >= 2000) {
//                        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                        zoom.lift.stopLift();
//
//                        zoom.outtake.forwardPosition();
//                        zoom.lift.setState(Lift.LiftState.START);
//                    }
                    break;
                default:
                    zoom.lift.setState(Lift.LiftState.START);
            }

            if (c.dpad_down_2.isPressed() && zoom.lift.getState() != Lift.LiftState.START) {
                zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                zoom.outtake.neutralPosition();
                zoom.lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
                zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                zoom.lift.down(1);
                zoom.lift.setState(Lift.LiftState.START);
            }

            if (c.dpad_right_2.isPressed() && zoom.lift.getState() != Lift.LiftState.START) {
                zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                zoom.outtake.backPosition();

                zoom.lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
                zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                zoom.lift.down(1);
                zoom.lift.setState(Lift.LiftState.START);
            }


            if (gamepad2.dpad_up ) {
                zoom.lift.up(1);
            } else if (gamepad2.dpad_down) {
                zoom.lift.down(1);
            } else {
                zoom.lift.stopLift();
            }

            // Carousel

            switch (zoom.carousel.getState()) {
                case IDLE:
                    // If gamepad1.left_bumper pressed, start SPIN_INCREASE and timer
                    if (c.left_bumper.isPressed() || c.right_bumper.isPressed()) {
                        // Which motor to turn on
                        zoom.carousel.setSide(Constants.Status.LEFT);
                        zoom.carousel.getTimer().reset();
                        // Reset spin power to starting spin power
                        zoom.carousel.resetSpin();
                        zoom.carousel.setState(CarouselSolo.CarouselState.SPIN_INCREASE);
                    }
                    break;
                case SPIN_INCREASE:
                    // Carousel motor setPower based on internal power variable
                    zoom.carousel.powerSpin();
                    // Increase internal power variable by multiplier at rate in Constants
                    zoom.carousel.increaseSpin();
                    // When max power is reached, carousel is at SPIN_MAX
                    if (Math.abs(zoom.carousel.getPowerVar()) == 1) {
                        zoom.carousel.powerSpin();
                        zoom.carousel.setState(CarouselSolo.CarouselState.SPIN_MAX);
                    }
                    break;
                case SPIN_MAX:
                    // When SPIN_TIME has elapsed, carousel becomes IDLE
                    if (zoom.carousel.getTimer().milliseconds() >= Constants.SPIN_TIME) {
                        zoom.carousel.stopSpin();
                        zoom.carousel.setState(CarouselSolo.CarouselState.IDLE);
                    }
                    break;
                default:
                    zoom.carousel.setState(CarouselSolo.CarouselState.IDLE);
            }

            // If gamepad1.left_bumper, interrupt and reset to IDLE
            if (c.x.isPressed() && zoom.carousel.getState() != CarouselSolo.CarouselState.IDLE) {
                zoom.carousel.stopSpin();
                zoom.carousel.setState(CarouselSolo.CarouselState.IDLE);
            }

            // Hopper

            if (gamepad2.x) {
                zoom.outtake.backPosition();
            }

            if (gamepad2.y) {
                zoom.outtake.neutralPosition();
            }

            if (gamepad2.b) {
                zoom.outtake.forwardPosition();
            }

            telemetry.addData("carousel power", zoom.carousel.getPower());
            telemetry.addData("lift enc", zoom.lift.getLift().getCurrentPosition());
            telemetry.addData("Intake State", zoom.intake.getState().toString());
            telemetry.addData("Lift State", zoom.lift.getState().toString());
            telemetry.addData("Carousel State", zoom.carousel.getState().toString());
            telemetry.addData("Drivetrain State", zoom.drivetrain.getState().toString());
            //telemetry.addData("gp1 right stick y", gamepad1.right_stick_y);
            //telemetry.addData("gp1 left stick y", gamepad1.left_stick_y);
            telemetry.update();
        }
    }
}
