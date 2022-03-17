package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TestOuttakeTuner")
@Disabled
public class TestOuttakeTeleop extends LinearOpMode {

    Servo outtake;
    double position = 0;
    boolean yPrev, yCurr, yState, yToggle, aPrev, aCurr, aState, aToggle;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.setMsTransmissionInterval(50);
        telemetry.addData("Desc", "");
        telemetry.addData("How to Use", "");
        telemetry.update();
        outtake = hardwareMap.servo.get("outtake");

        waitForStart();

        while (opModeIsActive()) {
            yState = gamepad1.y;
            yPrev = yCurr;
            yCurr = yState;

            aState = gamepad1.a;
            aPrev = aCurr;
            aCurr = aState;

            if (!yPrev && yCurr) {
                position += 5 / 300.0;
                yToggle = !yToggle;
            }

            if (!aPrev && aCurr) {
                position -= 5 / 300.0;
                aToggle = !aToggle;
            }
            checkPos();
            outtake.setPosition(position);
            telemetry.addData("position var", position);
            telemetry.addData("servo pos", outtake.getPosition());
            telemetry.addData("yToggle", yToggle);
            telemetry.addData("aToggle", aToggle);
            telemetry.update();
        }
    }

    public void checkPos() {
        if (position > 1) {
            position = 1;
        } else if (position < 0) {
            position = 0;
        }
    }
}
