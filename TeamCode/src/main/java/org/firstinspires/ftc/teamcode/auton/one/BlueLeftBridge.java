package org.firstinspires.ftc.teamcode.auton.one;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.IMU;

@Autonomous(name = "BlueLeftBridge", group = "District")
//@Disabled
public class BlueLeftBridge extends LinearOpMode {

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

            move.forward(90);
            sleep(wait);

            move.PIDTurn(-90);
            sleep(wait);

            move.forward(23);
            sleep(wait);

            move.PIDTurn(-90);
            sleep(wait);

            move.setPower(.8);
            move.forward(30);
            sleep(wait);

            break;
        }
    }
}
