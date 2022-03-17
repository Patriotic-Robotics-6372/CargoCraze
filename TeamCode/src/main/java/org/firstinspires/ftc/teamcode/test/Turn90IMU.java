package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Robot;

@Autonomous (name = "Turn90IMU", group = "PRTest")
public class Turn90IMU extends LinearOpMode {

    Robot prbot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        prbot.init(hardwareMap, telemetry);
        prbot.setMode(Constants.Status.AUTO);
        telemetry.setMsTransmissionInterval(50);

        waitForStart();
        prbot.imu.resetAngle();
        while (opModeIsActive()) {
//            prbot.drivetrain.turnIMU(45);
//            sleep(4000);
//            telemetry.addData("angle", prbot.imu.getAngle());
//            telemetry.update();
            prbot.drivetrain.turnIMU(0);
            sleep(4000);
            telemetry.addData("angle", prbot.imu.getAngle());
            telemetry.addData("start angle", prbot.imu.getStartAngle());
            telemetry.update();


            prbot.drivetrain.turnIMU(90);
            sleep(4000);
            telemetry.addData("angle", prbot.imu.getAngle());
            telemetry.addData("start angle", prbot.imu.getStartAngle());
            telemetry.update();


            prbot.drivetrain.turnIMU(0);
            sleep(4000);
            telemetry.addData("angle", prbot.imu.getAngle());
            telemetry.addData("start angle", prbot.imu.getStartAngle());
            telemetry.update();


            prbot.drivetrain.turnIMU(180);
            sleep(4000);
            telemetry.addData("angle", prbot.imu.getAngle());
            telemetry.addData("start angle", prbot.imu.getStartAngle());
            telemetry.update();


            prbot.drivetrain.turnIMU(0);
            sleep(4000);
            telemetry.addData("angle", prbot.imu.getAngle());
            telemetry.addData("start angle", prbot.imu.getStartAngle());
            telemetry.update();

           
            prbot.drivetrain.turnIMU(0);
            sleep(4000);
            telemetry.addData("angle", prbot.imu.getAngle());
            telemetry.addData("start angle", prbot.imu.getStartAngle());
            telemetry.update();

            /*
            sleep(3000);
            prbot.drivetrain.turnToIMU(-90);
            sleep(250);
            telemetry.addData("angle", prbot.imu.getFirstAngleNum());
            telemetry.update();
            sleep(2000);
             */
            break;
        }
    }
}
