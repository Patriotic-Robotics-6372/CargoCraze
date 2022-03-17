package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.SensorColor;

@TeleOp (name = "TestColorSensor", group = "Test")
public class TestColorSensor extends LinearOpMode {

    SensorColor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = new SensorColor(hardwareMap.colorSensor.get("colorSensor"));

        colorSensor.turnOffLED();

        waitForStart();

        colorSensor.turnOnLED();

        while (opModeIsActive()) {
            telemetry.addData("Red", colorSensor.getRed())
                    .addData("Green", colorSensor.getGreen())
                    .addData("Blue", colorSensor.getBlue())
                    .addData("Alpha", colorSensor.getAlpha())
                    .addData("Hue", colorSensor.getHue());
            telemetry.update();
        }
    }
}
