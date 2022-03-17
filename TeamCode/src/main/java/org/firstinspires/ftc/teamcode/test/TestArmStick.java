package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Controller;

@TeleOp (name = "TestArmStick", group = "Test")
public class TestArmStick extends LinearOpMode {
    Arm a;
    Controller c;
    double radians, angle, servoAngle;
    boolean toggle = false;
    @Override
    public void runOpMode() throws InterruptedException {
        a = new Arm(hardwareMap.servo.get("capper"));

        c = new Controller(gamepad1, gamepad2);

        a.setExtendTime(new ElapsedTime());

        ElapsedTime matchTime = new ElapsedTime();

        waitForStart();

        a.getExtendTime().reset();
        matchTime.reset();

        while (opModeIsActive()) {
            c.updateInputs();
            radians = Math.atan2(-gamepad2.left_stick_y, -gamepad2.left_stick_x);
            angle = (radians / Math.PI * 180) + 180.0;
            servoAngle = Math.max(Math.min(angle, 300), 170);

            switch (a.getArmstate()) {
                case START:
                    a.start();
                    if (c.left_stick_button_2.isPressed()/* && matchTime.seconds() > 115*/) {
                        a.extend();
                        a.getExtendTime().reset();
                        a.setArmstate(Arm.ArmState.EXTEND);
                    }
                    break;
                case EXTEND:
                    if (a.getExtendTime().milliseconds() > 800) {
                        a.start();
                        a.setArmstate(Arm.ArmState.END);
                    }
                    break;
                case END:
                    if (a.getExtendTime().milliseconds() > 1600) {
                        a.setArmstate(Arm.ArmState.MANUAL);
                    }
                case MANUAL:
                    if (Math.abs(gamepad2.left_stick_y) > .1 && Math.abs(gamepad2.left_stick_x) > .1) {
                        a.getArm().setPosition(servoAngle / 300.0);
                    }
                    break;
            }

            telemetry.addData("gamepad1.left_stick_x", gamepad1.left_stick_x)
                    .addData("gamepad1.left_stick_y", gamepad1.left_stick_y)
                    .addData("Radians", radians)
                    .addData("Angle", angle)
                    .addData("servoAngle", servoAngle)
                    .addData("toggle", toggle);
            telemetry.update();
        }
    }
}
