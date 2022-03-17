package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.old.DrivetrainOld;

@Autonomous(name = "Drivetrain Test", group = "PRTest")
//@Disabled
public class TestDrivetrain extends LinearOpMode {

    DrivetrainOld move;

    @Override
    public void runOpMode() throws InterruptedException {
        move = new DrivetrainOld(hardwareMap.dcMotor.get("frontLeft"),
                hardwareMap.dcMotor.get("frontRight"),
                hardwareMap.dcMotor.get("backLeft"),
                hardwareMap.dcMotor.get("backRight"));

        waitForStart();

        while (opModeIsActive()) {

        }
        //move.forward(.3);
        sleep(1000);

        //move.backward(.3);
        sleep(1000);

    }
}
