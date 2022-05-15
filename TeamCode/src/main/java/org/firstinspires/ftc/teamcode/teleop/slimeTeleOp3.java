package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Barcode;
import org.firstinspires.ftc.teamcode.hardware.Carousel;
import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Controller;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Disabled
@TeleOp (name = "PRTELEOPv2", group = "PRTeleOp")
public class slimeTeleOp3 extends LinearOpMode {

    Robot zoom = new Robot();
    Controller c;

    OpenCvCamera webcam;

    Barcode pipeline = new Barcode(telemetry, Constants.StartPos.REDLEFT);

    ElapsedTime matchTime = new ElapsedTime();

    boolean rTToggle = false;
    boolean lbToggle = false;
    boolean armToggle = false;
    boolean lStickButtonToggle = true;

    double dtSpeed = 1;

    boolean isRed = false;
    //boolean holdingFreight = false;
    //int offset = 10;
    double radians, angle, servoAngle;

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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipeline);

        waitForStart();

        FtcDashboard.getInstance().startCameraStream(webcam, 0);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                /*
                 * Tell the webcam to start streaming images to us! Note that you must make sure
                 * the resolution you specify is supported by the camera. If it is not, an exception
                 * will be thrown.
                 *
                 * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                 * supports streaming from the webcam in the uncompressed YUV image format. This means
                 * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                 * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                 *
                 * Also, we specify the rotation that the webcam is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */
                //320px x 340px
                webcam.startStreaming(320 * 2, 240 * 2 , OpenCvCameraRotation.UPRIGHT);

                /*
                 * Specify the image processing pipeline we wish to invoke upon receipt
                 * of a frame from the camera. Note that switching pipelines on-the-fly
                 * (while a streaming session is in flight) *IS* supported.
                 */

                webcam.setPipeline(pipeline);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("errorCode", errorCode);
            }
        });

        zoom.resetTimers();
        matchTime.reset();

        while (opModeIsActive()) {

            //holdingFreight = zoom.colorSensor.getHue() > 0.3;

            c.updateInputs();

//          Movement

            if (c.right_trigger.isPressed()) rTToggle = !rTToggle;
            if (c.left_stick_button.isPressed()) lStickButtonToggle = !lStickButtonToggle;
            dtSpeed = lStickButtonToggle ? 1 : 0.3;
            if (zoom.drivetrain.getState() == Drivetrain.DrivetrainState.NEUTRAL) {
                if (!rTToggle) {
                    if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
                        zoom.drivetrain.setBase((gamepad1.right_stick_y - gamepad1.left_trigger) * dtSpeed, (gamepad1.left_stick_y - gamepad1.left_trigger) * dtSpeed, (gamepad1.right_stick_y - gamepad1.left_trigger) * dtSpeed, (gamepad1.left_stick_y - gamepad1.left_trigger) * dtSpeed);
                    } else if (gamepad1.dpad_up) {
                        zoom.drivetrain.setBase(-1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger);
                    } else if (gamepad1.dpad_down) {
                        zoom.drivetrain.setBase(1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger);
                    } else {
                        zoom.drivetrain.stop();
                    }
                } else {
                    if (Math.abs(gamepad1.right_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1) {
                        zoom.drivetrain.setBase((-gamepad1.right_stick_y + gamepad1.left_trigger) * dtSpeed, (-gamepad1.left_stick_y + gamepad1.left_trigger) * dtSpeed, (-gamepad1.right_stick_y + gamepad1.left_trigger) * dtSpeed, (-gamepad1.left_stick_y + gamepad1.left_trigger) * dtSpeed);
                    } else if (gamepad1.dpad_down) {
                        zoom.drivetrain.setBase(-1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger, -1 + gamepad1.left_trigger);
                    } else if (gamepad1.dpad_up) {
                        zoom.drivetrain.setBase(1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger, 1 - gamepad1.left_trigger);
                    } else {
                        zoom.drivetrain.stop();
                    }
                }
            }

            /*
            switch (zoom.drivetrain.getState()) {
                case NEUTRAL:
                    // shouldn't do anything
                    if (c.y.isPressed()) {
                        // Goes forward, out of the barrier
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        zoom.drivetrain.setTargetPos(Constants.BARRIER_FORWARD, Constants.BARRIER_FORWARD, Constants.BARRIER_FORWARD, Constants.BARRIER_FORWARD);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
                        zoom.drivetrain.setBase(.5);
                        zoom.drivetrain.setState(Drivetrain.DrivetrainState.START_HUB);
                        //toggle = !toggle;
                    }
                    break;
                case START_HUB:
                    // The first turn, away from the hub
                    if (!zoom.drivetrain.allBusy()) {
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        //prbot.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.drivetrain.stop();
                        zoom.drivetrain.setTargetPos(Constants.TURN_ONE, Constants.TURN_ONE, Constants.TURN_ONE, Constants.TURN_ONE);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
                        if (isRed) {
                            zoom.drivetrain.setRightSide(1, 1);
                        } else {
                            zoom.drivetrain.setLeftSide(1, 1);
                        }
                        zoom.drivetrain.setState(Drivetrain.DrivetrainState.TURN_ONE);

                    }
                    break;
                case TURN_ONE:
                    // The second turn, towards the hub
                    if (!zoom.drivetrain.allBusy()) {
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        zoom.drivetrain.stop();
                        zoom.drivetrain.setTargetPos(Constants.TURN_TWO, Constants.TURN_TWO, Constants.TURN_TWO, Constants.TURN_TWO);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
                        if (isRed) {
                            zoom.drivetrain.setLeftSide(1, 1);
                        } else {
                            zoom.drivetrain.setRightSide(1, 1);
                        }
                        zoom.drivetrain.setState(Drivetrain.DrivetrainState.TURN_TWO);
                    }
                    break;
                case TURN_TWO:
                    // Needs to go forward a little bit to reach the hub
                    if (!zoom.drivetrain.allBusy()) {
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        zoom.drivetrain.stop();
                        zoom.drivetrain.setTargetPos(Constants.HUB_FORWARD, Constants.HUB_FORWARD, Constants.HUB_FORWARD, Constants.HUB_FORWARD);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
                        zoom.drivetrain.setBase(-.5);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.drivetrain.setState(Drivetrain.DrivetrainState.END_HUB);
                    }
                    break;
                case END_HUB:
                    if (!zoom.drivetrain.allBusy()) {
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                        zoom.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        zoom.drivetrain.stop();
                        zoom.drivetrain.setState(Drivetrain.DrivetrainState.NEUTRAL);
                    }
                    break;
                default:
                    zoom.drivetrain.setState(Drivetrain.DrivetrainState.NEUTRAL);
            }
             */

            if (c.b.isPressed() && zoom.drivetrain.getState() != Drivetrain.DrivetrainState.NEUTRAL) {
                zoom.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
                zoom.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                zoom.drivetrain.stop();
                zoom.drivetrain.setState(Drivetrain.DrivetrainState.NEUTRAL);
            }

//        Intake

            //if (!holdingFreight) {
                if (gamepad2.left_trigger > 0.1) {
                    zoom.intake.spinForward(1);
                    //zoom.outtake.backPosition();
                } else if (gamepad2.right_trigger > 0.1) {
                    zoom.intake.spinBackward(1);
                    //zoom.outtake.forwardPosition();
                } else {
                    zoom.intake.stopIt();
                }
            //}

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

                        if (!gamepad2.dpad_left) {
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
                            zoom.lift.down(.5);

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

            if (armToggle) {
                radians = Math.atan2(gamepad2.left_stick_y, gamepad2.left_stick_x);
            } else {
                radians = Math.atan2(-gamepad2.left_stick_y, -gamepad2.left_stick_x);
            }
            //angle = ((radians / Math.PI * 180) + 180.0) / 360 * 130;
            angle = ((radians / Math.PI * 180) + 180.0);
            servoAngle = Math.max(Math.min(angle, 300), 170);
            //servoAngle = angle + 170;

            switch (zoom.arm.getArmstate()) {
                case START:
                    zoom.arm.start();
                    if (c.left_stick_button_2.isPressed()/* && matchTime.seconds() > 115*/) {
                        zoom.arm.extend();
                        zoom.arm.getExtendTime().reset();
                        zoom.arm.setArmstate(Arm.ArmState.EXTEND);
                    }
                    break;
                case EXTEND:
                    if (zoom.arm.getExtendTime().milliseconds() > 800) {
                        zoom.arm.start();
                        zoom.arm.setArmstate(Arm.ArmState.END);
                    }
                    break;
                case END:
                    if (zoom.arm.getExtendTime().milliseconds() > 1600) {
                        zoom.arm.setArmstate(Arm.ArmState.MANUAL);
                    }
                case MANUAL:
                    if (c.left_stick_button_2.isPressed()) armToggle = !armToggle;
                    if (Math.abs(gamepad2.left_stick_y) > .1 || Math.abs(gamepad2.left_stick_x) > .1) {
                        zoom.arm.setPos(servoAngle / 300.0);
                    } else if (armToggle){
                        zoom.arm.start();
                    } else {
                        zoom.arm.rest();
                    }
                    zoom.arm.updatePos();
                    break;
            }

            if (c.left_bumper.isPressed()) lbToggle = !lbToggle;

            //telemetry.addData("dpad_left 2 held", c.dpad_down_2.isHeld());
            telemetry.addData("carousel power", zoom.carousel.getPower());
            telemetry.addData("lift enc", zoom.lift.getLift().getCurrentPosition());
            telemetry.addData("Intake State", zoom.intake.getState().toString());
            telemetry.addData("Lift State", zoom.lift.getState().toString());
            telemetry.addData("Carousel State", zoom.carousel.getState().toString());
            telemetry.addData("Drivetrain State", zoom.drivetrain.getState().toString());
            telemetry.addData("Arm State", zoom.arm.getArmstate().toString());
            //telemetry.addData("gp1 right stick y", gamepad1.right_stick_y);
            //telemetry.addData("gp1 left stick y", gamepad1.left_stick_y);
            telemetry.update();
        }
    }
}
