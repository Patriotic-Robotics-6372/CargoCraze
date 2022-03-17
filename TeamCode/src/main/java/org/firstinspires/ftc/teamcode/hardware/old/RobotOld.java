package org.firstinspires.ftc.teamcode.hardware.old;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Carousel;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Outtake;

public class RobotOld {

public DrivetrainOld drivetrain;
public Intake intake;
public Outtake outtake;
public Lift lift;
public Carousel carousel;


public void init(HardwareMap hwMap, Telemetry telemetry) {
    drivetrain = new DrivetrainOld(hwMap.dcMotor.get("frontLeft"), hwMap.dcMotor.get("frontRight"), hwMap.dcMotor.get("backLeft"), hwMap.dcMotor.get("backRight"));
    intake = new Intake(hwMap.dcMotor.get("intake"));
    outtake = new Outtake(hwMap.servo.get("outtake"));
    lift = new Lift(hwMap.dcMotor.get("lift"));
    carousel = new Carousel(hwMap.dcMotor.get("leftCarousel"), hwMap.dcMotor.get("rightCarousel"));
    }


}



