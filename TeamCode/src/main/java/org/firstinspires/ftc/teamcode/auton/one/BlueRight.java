package org.firstinspires.ftc.teamcode.auton.one;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Constants;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.IMU;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Robotv2;

@Autonomous(name = "BlueRight", group = "District")
//@Disabled
public class BlueRight extends LinearOpMode {

    Robotv2 bot = new Robotv2();
    IMU imu;

    int wait = 500;

    @Override
    public void runOpMode() throws InterruptedException {
        bot.init(hardwareMap, telemetry);
        bot.setMode(Constants.Status.AUTO);

        waitForStart();

        bot.resetTimers();
        //matchTime.reset();

        while (opModeIsActive()) {

            bot.drivetrain.forward(35);
            sleep(wait);

            bot.optake.spinBackward(.8);
            bot.intake.spinBackward(.8);
            bot.drivetrain.forward(10);
            //sleep(2000);

            bot.drivetrain.backward(5);
            sleep(wait);
            bot.intake.stopIt();

            bot.drivetrain.PIDTurn(90);
            sleep(wait);

            bot.drivetrain.forward(25);
            sleep(wait);
            bot.optake.stopIt();

            bot.drivetrain.PIDTurn(-90);
            sleep(wait);

            bot.drivetrain.forward(33);
            sleep(wait);

            bot.drivetrain.PIDTurn(-90);
            sleep(wait);

            bot.lift.move(2600);
            sleep(wait);

            bot.drivetrain.backward(8.5);
            sleep(wait);

            bot.outtake.backPosition();
            sleep(3000);

            bot.outtake.neutralPosition();
            bot.lift.move(0);
            sleep(wait);

            bot.optake.spinBackward(.8);
            bot.intake.spinBackward(.8);
            bot.drivetrain.forward(15);
            sleep(3000);
            bot.optake.stopIt();
            bot.intake.stopIt();

            //bot.outtake.forwardPosition();
            break;
        }
    }
}
