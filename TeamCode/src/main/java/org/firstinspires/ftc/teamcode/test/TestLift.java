package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Lift;

@Autonomous(name = "Kofi", group = "PRTest")
@Disabled
public class TestLift extends LinearOpMode {

    Lift lift;

    @Override
    public void runOpMode() throws InterruptedException {
       lift = new Lift(hardwareMap.dcMotor.get("lift"));

       waitForStart();

       lift.up(.1);
       sleep(1000);

       lift.down(.1);
       sleep(3000);

       lift.up(.2);
       sleep(1000);

        lift.down(.2);
        sleep(3000);

        lift.up(.3);
        sleep(1000);

        lift.down(.3);
        sleep(1000);

        lift.stopLift();


    }
}
