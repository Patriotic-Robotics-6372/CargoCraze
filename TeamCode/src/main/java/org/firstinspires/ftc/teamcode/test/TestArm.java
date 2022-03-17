package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Controller;

@TeleOp (name = "TestArm", group = "Test")
public class TestArm extends LinearOpMode {

    Arm a;
    Controller c;
    boolean toggle = false;

    @Override
    public void runOpMode() throws InterruptedException {
        a = new Arm(hardwareMap.servo.get("capper"));
        c = new Controller(gamepad1, gamepad2);

        telemetry.setMsTransmissionInterval(20);
        waitForStart();

        while (opModeIsActive()) {

            c.updateInputs();

            if (c.y.isPressed()) {
                a.increase();
            }

            if (c.b.isPressed()) {
                a.decrease();
            }

            if (c.a.isPressed()) {
                toggle = !toggle;
            }

            if (c.x.isPressed()) {
                a.extend();
                sleep(800);
                a.start();
            }

            if (toggle) {
                a.updatePos();
            }

            telemetry.addData("arm pos", a.getArm().getPosition());
            telemetry.addData("pos var", a.getPos());
            telemetry.addData("toggle", toggle);
            telemetry.update();
        }
    }
}
