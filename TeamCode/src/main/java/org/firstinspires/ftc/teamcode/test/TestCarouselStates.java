package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Carousel;
import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Controller;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;

/*
    Author: Jacob Marinas
    Date: 1/17/22
    Desc: Test Carousel class using states
 */
@Disabled
@TeleOp (name = "TestCarouselStates", group = "PRTest")
public class TestCarouselStates extends LinearOpMode {

    Carousel carousel;
    Drivetrain drivetrain;

    Controller c;

    @Override
    public void runOpMode() throws InterruptedException {
        carousel = new Carousel(hardwareMap.dcMotor.get("leftCarousel"), hardwareMap.dcMotor.get("rightCarousel"));
        drivetrain = new Drivetrain(hardwareMap.dcMotor.get("frontLeft"),
                hardwareMap.dcMotor.get("frontRight"),
                hardwareMap.dcMotor.get("backLeft"),
                hardwareMap.dcMotor.get("backRight"));

        // Gives additional information about inputs
        c = new Controller(gamepad1, gamepad2);

        telemetry.setMsTransmissionInterval(20);

        carousel.setTimer(new ElapsedTime());

        waitForStart();

        carousel.getTimer().reset();

        while (opModeIsActive()) {
            // Refresh gamepad inputs and update information
            c.updateInputs();
            switch (carousel.getState()) {
                case IDLE:
                    // If gamepad1.left_bumper pressed, start SPIN_INCREASE and timer
                    if (c.left_bumper.isPressed() || c.right_bumper.isPressed()) {
                        // Which motor to turn on
                        carousel.setSide(c.left_bumper.isPressed() ? Constants.Status.LEFT : Constants.Status.RIGHT);
                        carousel.getTimer().reset();
                        // Reset spin power to starting spin power
                        carousel.resetSpin();
                        carousel.setState(Carousel.CarouselState.SPIN_INCREASE);
                    }
                    break;
                case SPIN_INCREASE:
                    // Carousel motor setPower based on internal power variable
                    carousel.powerSpin();
                    // Increase internal power variable by multiplier at rate in Constants
                    carousel.increaseSpin();
                    // When max power is reached, carousel is at SPIN_MAX
                    if (Math.abs(carousel.getPowerVar()) == 1) {
                        carousel.powerSpin();
                        carousel.setState(Carousel.CarouselState.SPIN_MAX);
                    }
                    break;
                case SPIN_MAX:
                    // When SPIN_TIME has elapsed, carousel becomes IDLE
                    if (carousel.getTimer().milliseconds() >= Constants.SPIN_TIME) {
                        carousel.stopSpin();
                        carousel.setState(Carousel.CarouselState.IDLE);
                    }
                    break;
                default:
                    carousel.setState(Carousel.CarouselState.IDLE);
            }

            // If gamepad1.left_bumper, interrupt and reset to IDLE
            if (c.x.isPressed() && carousel.getState() != Carousel.CarouselState.IDLE) {
                carousel.stopSpin();
                carousel.setState(Carousel.CarouselState.IDLE);
            }

            // Test to see if drivetrain works while carousel is on
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

            telemetry.addData("Carousel power var", carousel.getPower());
            telemetry.addData("Carousel State", carousel.getState());
            telemetry.addData("Carousel Timer", carousel.getTimer().milliseconds());
            telemetry.update();
        }
    }
}
