package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Carousel;
import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Controller;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Disabled
@TeleOp (name = "PRTELEOPv3", group = "PRTeleOp")
public class slimeTeleOp4 extends OpMode {

    Robot zoom = new Robot();
    Controller c;

    boolean rTToggle = false;
    boolean lbToggle = false;

    @Override
    public void init() {
        zoom.init(hardwareMap, telemetry);
        zoom.setMode(Constants.Status.FAST);

        c = new Controller(gamepad1, gamepad2);

        telemetry.setMsTransmissionInterval(20);
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
    }

    @Override
    public void init_loop() {
        zoom.resetTimers();
    }


    @Override
    public void loop() {
        c.updateInputs();

//          Movement

        if (c.right_trigger.isPressed()) rTToggle = !rTToggle;

        if (!rTToggle) {
        if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
        zoom.drivetrain.setBase(gamepad1.right_stick_y - gamepad1.left_trigger, gamepad1.left_stick_y - gamepad1.left_trigger, gamepad1.right_stick_y - gamepad1.left_trigger, gamepad1.left_stick_y - gamepad1.left_trigger);
        } else if (gamepad1.dpad_up) {
        zoom.drivetrain.setBase(-1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger);
        } else if (gamepad1.dpad_down) {
        zoom.drivetrain.setBase(1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger);
        } else {
        zoom.drivetrain.stop();
        }
        } else {
        if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
        zoom.drivetrain.setBase(-gamepad1.right_stick_y + gamepad1.left_trigger, -gamepad1.left_stick_y + gamepad1.left_trigger, -gamepad1.right_stick_y + gamepad1.left_trigger, -gamepad1.left_stick_y + gamepad1.left_trigger);
        } else if (gamepad1.dpad_down) {
        zoom.drivetrain.setBase(-1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger);
        } else if (gamepad1.dpad_up) {
        zoom.drivetrain.setBase(1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger);
        } else {
        zoom.drivetrain.stop();
        }
        }

        //        Carousel

        switch (zoom.carousel.getState()) {
            case IDLE:
                // If gamepad1.left_bumper pressed, start SPIN_INCREASE and timer
                if (c.left_bumper.isPressed() || c.right_bumper.isPressed()) {
                    // Which motor to turn on
                    zoom.carousel.setSide(c.left_bumper.isPressed() ? Constants.Status.LEFT : Constants.Status.RIGHT);
                    zoom.carousel.getTimer().reset();
                    // Reset spin power to starting spin power
                    zoom.carousel.resetSpin();
                    zoom.carousel.setState(Carousel.CarouselState.SPIN_INCREASE);
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
                    zoom.carousel.setState(Carousel.CarouselState.SPIN_MAX);
                }
                break;
            case SPIN_MAX:
                // When SPIN_TIME has elapsed, carousel becomes IDLE
                if (zoom.carousel.getTimer().milliseconds() >= Constants.SPIN_TIME) {
                    zoom.carousel.stopSpin();
                    zoom.carousel.setState(Carousel.CarouselState.IDLE);
                }
                break;
            default:
                zoom.carousel.setState(Carousel.CarouselState.IDLE);
        }

        // If gamepad1.left_bumper, interrupt and reset to IDLE
        if (c.x.isPressed() && zoom.carousel.getState() != Carousel.CarouselState.IDLE) {
            zoom.carousel.stopSpin();
            zoom.carousel.setState(Carousel.CarouselState.IDLE);
        }

//        Intake

        switch (zoom.intake.getState()) {
        case IDLE:
        if (c.left_trigger_2.isPressed() && zoom.lift.getState() == Lift.LiftState.START) {
        zoom.intake.spinForward(1);
        zoom.intake.setState(Intake.IntakeState.FORWARD);
        } else if (c.right_trigger_2.isPressed()) {
        zoom.intake.spinBackward(1);
        zoom.intake.setState(Intake.IntakeState.BACKWARD);
        }
        break;
        case FORWARD:
        if (c.left_trigger_2.isPressed()) {
        zoom.intake.stopIt();
        zoom.intake.setState(Intake.IntakeState.IDLE);
        }
        if (c.right_trigger_2.isPressed()) {
        zoom.intake.spinBackward(1);
        zoom.intake.setState(Intake.IntakeState.BACKWARD);
        }
        break;
        case BACKWARD:
        if (c.right_trigger_2.isPressed()) {
        zoom.intake.stopIt();
        zoom.intake.setState(Intake.IntakeState.IDLE);
        }
        if (c.left_trigger_2.isPressed() && zoom.lift.getState() == Lift.LiftState.START) {
        zoom.intake.spinForward(1);
        zoom.intake.setState(Intake.IntakeState.FORWARD);
        }
        break;
default:
        zoom.intake.setState(Intake.IntakeState.IDLE);
        }

        //         Lift and Outtake

        switch (zoom.lift.getState()) {
        case START:
        if (!zoom.lift.getLift().isBusy()) {
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        zoom.lift.stopLift();
        zoom.outtake.forwardPosition();
        }
        if (c.dpad_up_2.isPressed()) {
        zoom.intake.setState(Intake.IntakeState.IDLE);
        zoom.lift.getLift().setTargetPosition(Constants.LEVEL_TWO);
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        zoom.lift.up(.8);

        zoom.outtake.neutralPosition();

        zoom.lift.setState(Lift.LiftState.EXTEND);
        }
        break;
        case EXTEND:
        if (!zoom.lift.getLift().isBusy()) {
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        zoom.lift.stopLift();

        zoom.outtake.backPosition();

        zoom.lift.getTimer().reset();

        zoom.lift.setState(Lift.LiftState.DUMP);
        }
        break;
        case DUMP:
        if (zoom.lift.getTimer().milliseconds() >= Constants.DUMP_TIME) {
        zoom.outtake.neutralPosition();
        zoom.lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        zoom.lift.down(.5);

        zoom.lift.setState(Lift.LiftState.RETRACT);
        }
        break;
        case RETRACT:
        if (!zoom.lift.getLift().isBusy()) {
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        zoom.lift.stopLift();

        zoom.outtake.forwardPosition();

        zoom.lift.setState(Lift.LiftState.START);
        }
        break;
default:
        zoom.lift.setState(Lift.LiftState.START);
        }

        if (c.dpad_down_2.isPressed() && zoom.lift.getState() != Lift.LiftState.START) {
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        zoom.outtake.neutralPosition();
        zoom.lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        zoom.lift.down(.5);
        zoom.lift.setState(Lift.LiftState.START);
        }

        if (c.dpad_right_2.isPressed() && zoom.lift.getState() != Lift.LiftState.START) {
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        zoom.outtake.backPosition();
        zoom.lift.getLift().setTargetPosition(Constants.LEVEL_ZERO);
        zoom.lift.getLift().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        zoom.lift.down(.3);
        zoom.lift.setState(Lift.LiftState.START);
        }

        if (c.left_bumper.isPressed()) lbToggle = !lbToggle;

        //telemetry.addData("carousel power", zoom.carousel.getPower());
//        telemetry.addData("Intake State", zoom.intake.getState().toString());
//        telemetry.addData("Lift State", zoom.lift.getState().toString());
//        telemetry.addData("Carousel State", zoom.carousel.getState().toString());
//        telemetry.update();
        }
    }

