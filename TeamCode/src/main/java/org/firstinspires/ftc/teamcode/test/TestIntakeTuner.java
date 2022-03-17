package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Intake;

@TeleOp(name = "TestIntakerTuner", group = "Tuner")
@Disabled
public class TestIntakeTuner extends LinearOpMode {

    Intake intake;
    double speed = 0;
    boolean lbState, lbCurr, lbPrev, rbState, rbCurr, rbPrev;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap.dcMotor.get("intake"));
        telemetry.setMsTransmissionInterval(50);
        telemetry.addData("Desc", "");
        telemetry.addData("How to Use", "Left/Right trigger to increase/decrease speed");
        telemetry.update();
        waitForStart();

        while (opModeIsActive())
        {
            lbState = gamepad2.left_bumper;
            lbPrev = lbCurr;
            lbCurr = lbState;

            rbState = gamepad2.right_bumper;
            rbPrev = rbCurr;
            rbCurr = rbState;

            if (!lbPrev && lbCurr) {
                speed += 0.1;
            }
            if (!rbPrev && rbCurr) {
                speed -= 0.1;
            }
            checkSpeed();
            intake.spinForward(speed);
            telemetry.addData("speed var", speed);
            telemetry.addData("intake power", intake.getIntake().getPower());
            telemetry.addData("lbState", lbState);
            telemetry.addData("lbPrev", lbPrev);
            telemetry.addData("lbCurr", lbCurr);
            telemetry.addData("rbState", rbState);
            telemetry.addData("rbPrev", rbPrev);
            telemetry.addData("rbCurr", rbCurr);
            telemetry.update();
        }
    }

    public void checkSpeed() {
        if (speed > 1) {
            speed = 1;
        } else if (speed < -1) {
            speed = -1;
        }
    }
}
