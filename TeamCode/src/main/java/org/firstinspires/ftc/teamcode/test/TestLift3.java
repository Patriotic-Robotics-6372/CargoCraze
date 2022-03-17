package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Outtake;

@TeleOp(name = "TestLiftEnc", group = "PRTest")
public class TestLift3 extends LinearOpMode {

    Lift lift;
    Outtake outtake;

    boolean lbState, lbCurr, lbPrev, rbState, rbCurr, rbPrev;

    @Override
    public void runOpMode() throws InterruptedException {
        lift = new Lift(hardwareMap.dcMotor.get("lift"));
        outtake = new Outtake(hardwareMap.servo.get("outtake"));
        lift.init();
        lift.setTelemetry(telemetry);
        lift.useEncoders(true);
        lift.useBrake(true);
        lift.setMaxPower(.3);
        outtake.neutralPosition();
        telemetry.setMsTransmissionInterval(50);
        telemetry.addData("Desc", "");
        telemetry.addData("How to Use", "Left/Right trigger to increase/decrease level");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            lbState = gamepad2.left_bumper;
            lbPrev = lbCurr;
            lbCurr = lbState;

            rbState = gamepad2.right_bumper;
            rbPrev = rbCurr;
            rbCurr = rbState;

            if (!lbPrev && lbCurr) {
                lift.decreaseLevel();
            }
            if (!rbPrev && rbCurr) {
                lift.increaseLevel();
            }

            if (gamepad2.x) {
                outtake.backPosition();
            }

            if (gamepad2.y) {
                outtake.neutralPosition();
            }

            if (gamepad2.b) {
                outtake.forwardPosition();
            }

            lift.updateLevel();
            telemetry.addData("level", lift.getCurrentLevel());
            telemetry.addData("tick", lift.getCurrentTick());
            telemetry.update();
        }
    }
}
