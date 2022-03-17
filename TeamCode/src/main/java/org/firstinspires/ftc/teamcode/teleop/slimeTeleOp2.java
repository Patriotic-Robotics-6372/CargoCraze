package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Button;
import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.hardware.old.RobotOld;

@TeleOp(name = "PRTELEOP", group = "PRTeleop")
public class slimeTeleOp2 extends LinearOpMode {

    Robot zoom = new Robot();
    //Jacob is a sussy baka uwu

    Button x, b, right_trigger, left_bumper, left_bumper_2, right_bumper;

    boolean rTToggle, lBToggle;
    int liftEnc;
    double servoPos;

    int encError = 0;

    double startSpin;
    ElapsedTime time;
    double spinPower;

    @Override
    public void runOpMode() throws InterruptedException {

        //telemetry.setMsTransmissionInterval(50);
        telemetry.addData("version", "2");
        telemetry.setMsTransmissionInterval(250);
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

        zoom.init(hardwareMap, telemetry);
        zoom.setMode(Constants.Status.FAST);

        x = new Button();
        b = new Button();
        right_trigger = new Button();
        left_bumper = new Button();
        right_bumper = new Button();
        left_bumper_2 = new Button();

        time = new ElapsedTime();

        waitForStart();

        zoom.outtake.neutralPosition();
        time.reset();

        while (opModeIsActive()) {

//        Movement

            /*
                if (Math.abs(gamepad1.left_stick_y) > 0.1) {
                    zoom.drivetrain.forward(gamepad1.left_stick_y);
                } else {
                    zoom.drivetrain.stopMotors();
                }

                if (Math.abs(gamepad1.right_stick_y) > 0.1) {
                    zoom.drivetrain.forward(gamepad1.right_stick_y);
                } else {
                    zoom.drivetrain.stopMotors();
                }
             */

            right_trigger.previous();
            right_trigger.setState(gamepad1.right_trigger > .1);

            if (right_trigger.isPressed()) rTToggle = !rTToggle;

            if (!rTToggle) {
                if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
                    zoom.drivetrain.setBase(gamepad1.right_stick_y, gamepad1.left_stick_y, gamepad1.right_stick_y, gamepad1.left_stick_y);
                } else if (gamepad1.dpad_up) {
                    zoom.drivetrain.setBase(-1, -1, -1, -1);
                } else if (gamepad1.dpad_down) {
                    zoom.drivetrain.setBase(1, 1, 1, 1);
                }
                else {
                    zoom.drivetrain.stop();
                }
            } else {
                if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
                    zoom.drivetrain.setBase(-gamepad1.right_stick_y, gamepad1.left_stick_y, -gamepad1.right_stick_y, gamepad1.left_stick_y);
                } else if (gamepad1.dpad_up) {
                    zoom.drivetrain.setBase(-1, -1, -1, -1);
                } else if (gamepad1.dpad_down) {
                    zoom.drivetrain.setBase(1, 1, 1, 1);
                }
                else {
                    zoom.drivetrain.stop();
                }
            }

//        Intake

            if (gamepad2.left_trigger > 0.1) {
                zoom.intake.spinForward(1);
                //zoom.outtake.backPosition();
            } else if (gamepad2.right_trigger > 0.1) {
                zoom.intake.spinBackward(1);
                zoom.outtake.forwardPosition();
            } else {
                zoom.intake.stopIt();
            }

//        Carousel

            right_bumper.previous();
            right_bumper.setState(gamepad1.right_bumper);

            if (right_bumper.isPressed()) {
                spinPower = 0.4;
                startSpin = time.milliseconds();
                while (time.milliseconds() < startSpin + 1400) {    // 1400 is the offset; after 1400 ms, it exits the while loop
                    if (time.milliseconds() % 250 > 150) spinPower *= 1.04; // Increment by 4% every 250 ms
                    if (spinPower > 1) spinPower = 1.0;
                    zoom.carousel.rightSpin(spinPower);
                }
                zoom.carousel.rightSpin(0);
            }

            left_bumper.previous();
            left_bumper.setState(gamepad1.left_bumper);

            if (left_bumper.isPressed()) {
                spinPower = 0.4;
                startSpin = time.milliseconds();
                while (time.milliseconds() < startSpin + 1400) {
                    if (time.milliseconds() % 250 > 150) spinPower *= 1.04;
                    if (spinPower > 1) spinPower = 1.0;
                    zoom.carousel.leftSpin(spinPower);
                }
                zoom.carousel.leftSpin(0);
            }

//         Outtake

            left_bumper_2.previous();
            left_bumper_2.setState(gamepad2.left_bumper);

            if (left_bumper_2.isPressed()) lBToggle = !lBToggle;


            if (!lBToggle) {
                if (gamepad2.x) {
                    zoom.outtake.backPosition();
                }

                if (gamepad2.y) {
                    zoom.outtake.neutralPosition();
                }

                if (gamepad2.b) {
                    zoom.outtake.forwardPosition();
                }
            } else {

                x.previous();
                x.setState(gamepad2.x);

                b.previous();
                b.setState(gamepad2.b);

                if (x.isPressed()) {
                    servoPos -= 0.1;
                }
                if (b.isPressed()) {
                    servoPos += 0.1;
                }

                if (servoPos > 1)
                    servoPos = 1;
                if (servoPos < 0)
                    servoPos = 0;

                zoom.outtake.setPos(servoPos);
            }

//         Lift

            /*
            if (gamepad2.dpad_up) {
                zoom.lift.setLevel(2);
            } else if (gamepad2.dpad_down) {
                zoom.lift.setLevel(1);
            } else {
                zoom.lift.setLevel(0);
            }
             */

            liftEnc = zoom.lift.getLift().getCurrentPosition();
            if (gamepad2.dpad_up && liftEnc < 1300 + encError) {
                zoom.lift.up(.8);
            } else if (gamepad2.dpad_down && liftEnc > 0) {
                zoom.lift.down(.5);
            } else {
                zoom.lift.stopLift();
            }

            if (gamepad2.right_bumper) {
                encError = liftEnc;
            }

            //zoom.lift.updateLevel();

            //telemetry.addData("lift level", zoom.lift.getCurrentLevel());
            telemetry.addData("lift pow", zoom.lift.getLift().getPower());
            telemetry.addData("lift enc", zoom.lift.getLift().getCurrentPosition());

            telemetry.addData("gamepad1 left stick y", gamepad1.left_stick_y);
            telemetry.addData("gamepad1 right stick y", gamepad1.right_stick_y);


//          Telem
            //telemetry.addData("pos", zoom.outtake.getOuttake2().getPosition());
            //telemetry.update();
            telemetry.addData("outtake back", "x");
            telemetry.addData("outtake neutral", "y");
            telemetry.addData("outtake forward", "b");
            telemetry.addData("servo pos", servoPos);
            telemetry.addData("manual servo", lBToggle);
            //telemetry.addData("lift 2", "dpad_up");
            //telemetry.addData("lift 1", "dpad_down");
            //telemetry.addData("lift 0", "neutral");
            //telemetry.addData("intake in", "left_trigger");
            //telemetry.addData("intake out", "right_trigger");
            //telemetry.addData("lift level", zoom.lift.getCurrentLevel());
            telemetry.addData("lift tick", zoom.lift.getCurrentTick());
            telemetry.addData("left carousel", zoom.carousel.getLeftCarousel().getPower());
            telemetry.addData("right carousel", zoom.carousel.getRightCarousel().getPower());
            telemetry.addData("encError", encError);
            telemetry.update();
        }
    }
}
