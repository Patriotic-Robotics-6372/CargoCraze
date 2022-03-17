package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Controller;

@TeleOp (name = "TestArmState", group = "Test")
public class TestArmState extends LinearOpMode {

    Arm a;
    Controller c;
    boolean toggle = false;

    @Override
    public void runOpMode() throws InterruptedException {
        a = new Arm(hardwareMap.servo.get("capper"));
        c = new Controller(gamepad1, gamepad2);

        telemetry.setMsTransmissionInterval(20);
        a.setExtendTime(new ElapsedTime());

        ElapsedTime matchTime = new ElapsedTime();

        waitForStart();

        a.getExtendTime().reset();
        matchTime.reset();

        while (opModeIsActive()) {

            //a.getExtendTime().reset();
            c.updateInputs();

            switch (a.getArmstate()) {
                case START:
                    a.start();
                    if (c.x.isPressed()/* && matchTime.seconds() > 115*/) {
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
                    if (c.y.isPressed()) {
                        a.increase();
                    }

                    if (c.b.isPressed()) {
                        a.decrease();
                    }
                    a.updatePos();
                    break;
            }

            telemetry.addData("arm pos", a.getArm().getPosition());
            telemetry.addData("pos var", a.getPos());
            telemetry.addData("toggle", toggle);
            telemetry.addData("time", a.getExtendTime().seconds());
            telemetry.update();
        }
    }
}
