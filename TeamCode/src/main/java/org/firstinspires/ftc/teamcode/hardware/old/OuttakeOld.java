package org.firstinspires.ftc.teamcode.hardware.old;

import com.qualcomm.robotcore.hardware.CRServo;

/**
 * Date: 10/20/21
 * Author:Na Zyia Nelson
 * Subsystem: Outtake
 */

public class OuttakeOld {
    private CRServo outtake;

            public OuttakeOld(CRServo o) {this.outtake = o;}

            // Move up
            public void moveForward(double power) {outtake.setPower(power);}
            //Move down
            public void  moveBack(double power)  {outtake.setPower(-power);}
            //stop
            public void  stop() {outtake.setPower(0);}
}
