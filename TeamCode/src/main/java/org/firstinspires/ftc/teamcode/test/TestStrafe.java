package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.old.DrivetrainOld;

@Autonomous(name = "TestStrafe", group = "PRTest")
//@Disabled
public class TestStrafe extends LinearOpMode {

    Drivetrain move;

    @Override
    public void runOpMode() throws InterruptedException {
        move = new Drivetrain(hardwareMap.dcMotor.get("frontLeft"),
                hardwareMap.dcMotor.get("frontRight"),
                hardwareMap.dcMotor.get("backLeft"),
                hardwareMap.dcMotor.get("backRight"));

        move.setTelemetry(telemetry);
        move.setPower(.4);
        move.useBrake(true);
        waitForStart();

        while (opModeIsActive()) {
            move.strafeLeft(10);
            sleep(1000);

            //move.backward(.3);
            move.strafeRight(7);
            sleep(1000);
        }
        //move.forward(.3);


    }
}
