package org.firstinspires.ftc.teamcode.test;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.old.OuttakeOld;

@Autonomous(name= "Outtake test")
@Disabled
public class TestOuttake  extends LinearOpMode {
    OuttakeOld outtake;
    @Override
    public void runOpMode () throws InterruptedException {
        outtake = new OuttakeOld(hardwareMap.crservo.get("outtake"));
        waitForStart();

        outtake.moveForward( 0.5);
        sleep( 5000);
        outtake.moveBack( 0.5 );
        sleep(5000);
        outtake.stop();
        sleep(5000);
    }
}
