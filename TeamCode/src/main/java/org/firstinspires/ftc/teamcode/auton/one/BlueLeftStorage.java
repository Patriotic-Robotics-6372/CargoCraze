package org.firstinspires.ftc.teamcode.auton.one;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.IMU;

@Autonomous(name = "BlueLeftStorage", group = "District")
//@Disabled
public class BlueLeftStorage extends LinearOpMode {

    Drivetrain move;
    IMU imu;

    int wait = 500;

    @Override
    public void runOpMode() throws InterruptedException {
        move = new Drivetrain(hardwareMap.dcMotor.get("frontLeft"),
                hardwareMap.dcMotor.get("frontRight"),
                hardwareMap.dcMotor.get("backLeft"),
                hardwareMap.dcMotor.get("backRight"));
        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu 1"));

        move.setTelemetry(telemetry);
        move.setIMU(imu);
        move.setPower(.4);
        move.useBrake(true);
        waitForStart();

        while (opModeIsActive()) {
            move.forward(5);
            sleep(wait);

            move.PIDTurn(90);
            sleep(wait);

            move.forward(87);
            sleep(wait);

            move.PIDTurn(-90);
            sleep(wait);

            move.forward(55);
            sleep(wait);

            move.PIDTurn(-90);
            sleep(wait);

            move.forward(2);
            sleep(wait);

            break;
        }
    }
}
