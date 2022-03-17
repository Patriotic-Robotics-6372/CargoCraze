package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.LightBlinker;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.teamcode.hardware.Controller;

@Disabled
@TeleOp (name = "TestBlinker", group = "")
public class TestBlinker extends LinearOpMode {

    LED led;

    Controller c;

    boolean toggle = false;

    @Override
    public void runOpMode() throws InterruptedException {
        led = hardwareMap.led.get("led");

        c = new Controller(gamepad1, gamepad2);

        waitForStart();

        while (opModeIsActive()) {
            if (c.x.isPressed()) {
                toggle = !toggle;
            }
            led.enableLight(toggle);
        }
    }
}
