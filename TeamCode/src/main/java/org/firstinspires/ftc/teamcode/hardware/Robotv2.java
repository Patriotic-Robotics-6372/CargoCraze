package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robotv2 implements Constants {

    public Drivetrain drivetrain;
    public Intake intake;
    public Optake optake;
    public Outtake outtake;
    public Lift lift;
    public CarouselSolo carousel;
    public IMU imu;
    //public SensorColor colorSensor;

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        drivetrain = new Drivetrain(hwMap.dcMotor.get("frontLeft"), hwMap.dcMotor.get("frontRight"), hwMap.dcMotor.get("backLeft"), hwMap.dcMotor.get("backRight"));
        intake = new Intake(hwMap.dcMotor.get("intake"));
        optake = new Optake(hwMap.dcMotor.get("optake"));
        outtake = new Outtake(hwMap.servo.get("outtake"));
        lift = new Lift(hwMap.dcMotor.get("lift"));
        carousel = new CarouselSolo(hwMap.dcMotor.get("carousel"));
        imu = new IMU(hwMap.get(BNO055IMU.class, "imu 1"));
        //colorSensor = new SensorColor((hwMap.colorSensor.get("freightSensor")));
        //imu.getIMU();
        lift.setTelemetry(telemetry);
        drivetrain.setTelemetry(telemetry);
        drivetrain.setIMU(imu);
        lift.setTimer(new ElapsedTime());
        lift.setLiftTimeout(new ElapsedTime());
        carousel.setTimer(new ElapsedTime());
    }

    public void setMode(Status mode) {
        switch (mode) {
            case NORMAL:
                drivetrain.useBrake(true);
                outtake.neutralPosition();
                lift.init();
                lift.useEncoders(true);
                lift.useBrake(true);
                lift.setMaxPower(.5);
                break;
            case AUTO:
                drivetrain.setPower(.4);
                drivetrain.useBrake(true);
                outtake.neutralPosition();
                lift.init();
                lift.useEncoders(true);
                lift.useBrake(true);
                lift.setMaxPower(.6);
                break;
            case SLOW:
                drivetrain.useBrake(true);
                outtake.neutralPosition();
                lift.init();
                lift.useEncoders(true);
                lift.useBrake(true);
                lift.setMaxPower(.3);
                break;
            case FAST:
                drivetrain.useBrake(true);
                outtake.neutralPosition();
                lift.init();
                lift.useEncoders(true);
                lift.useBrake(true);
                lift.setMaxPower(.8);
                break;
        }
    }

    public void resetTimers() {
        lift.getTimer().reset();
        carousel.getTimer().reset();
    }
}



