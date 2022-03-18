package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Button;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Outtake;

@TeleOp(name = "TestLiftEncTuner", group = "PRTest")
public class TestLift4 extends LinearOpMode {

    Lift lift;
    Outtake outtake;

    boolean lbState, lbCurr, lbPrev, rbState, rbCurr, rbPrev;

    Button a;

    int liftEnc = 0;

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
        a = new Button();
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

            a.previous();
            a.setState(gamepad2.a);

            if (!lbPrev && lbCurr) {
                liftEnc -= 50;
            }
            if (!rbPrev && rbCurr) {
                liftEnc += 50;
            }

            if (liftEnc < 0) {
                liftEnc = 0;
            } else if (liftEnc > 2000) {
                liftEnc = 2000;
            }

//            if (a.isPressed()) {
//                lift.move(liftEnc);
//            }

            if (gamepad1.a) lift.move(liftEnc);

            if (gamepad2.x) {
                outtake.backPosition();
            }

            if (gamepad2.y) {
                outtake.neutralPosition();
            }

          //  if (gamepad2.b) {
            //    outtake.forwardPosition();
            //}

            //lift.updateLevel();
            telemetry.addData("liftEnc", liftEnc);
            telemetry.addData("lift encoder pos", lift.getLift().getCurrentPosition());
            telemetry.addData("level", lift.getCurrentLevel());
            telemetry.addData("tick", lift.getCurrentTick());
            telemetry.update();
        }
    }
}
