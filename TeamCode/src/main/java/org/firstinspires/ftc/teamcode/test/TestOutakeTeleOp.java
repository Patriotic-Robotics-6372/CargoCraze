package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
public class TestOutakeTeleOp extends OpMode {

    //outake
    Servo outake;
    double position = 0;

    @Override
    public void init() {
        // servos
        outake = hardwareMap.servo.get("outake");

    }

    @Override
    public void loop() {
        if (gamepad1.y) {
            position = position + 1;
        }

        if (gamepad1.a) {
            position = position - 1;
        }
        {
            outake.setPosition(position);

            telemetry.addData("position of outake", outake.getPosition());
            telemetry.update();
            // Servo

        }
    }
}



