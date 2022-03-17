package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Intake;

@Autonomous(name = "Intake Test")
@Disabled
public class TestIntake extends LinearOpMode {
    Intake intake;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap.dcMotor.get("intake"));

        waitForStart();

        intake.spinForward(0.5);
        sleep(5000);
        intake.spinBackward(0.5);
        sleep(5000);
        //intake.stop();
        sleep(5000);
    }
}
