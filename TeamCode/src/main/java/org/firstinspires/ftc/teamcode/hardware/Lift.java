package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
Author: Slimeafro and jayzeekay
Date: 10.7.21
System: Lift
 */


public class Lift implements Constants{

   private DcMotor lift;
   private double power;
   private int tickGoal, currentLevel, currentTick;
   private Telemetry telem;

   public enum LiftState {
       START, EXTEND, DUMP, RETRACT
   }

   private LiftState liftState = LiftState.START;

   ElapsedTime liftTimer;
   ElapsedTime liftTimeout;

   public Lift(DcMotor l) {
        this.lift = l;
   }

   public void init() {
       lift.setDirection(DcMotorSimple.Direction.FORWARD);

       power = STOP;
       currentLevel = 0;
   }

   public void setTelemetry(Telemetry telemetry) {
       telem = telemetry;
   }

   public void up(double power) {
       rotate(power);
   }

   public void down (double power) {
       rotate(-power);
   }

   public DcMotor getLift () {return lift;}

   public void stopLift () {
       rotate(0);
   }

   public void rotate(double l) {
       lift.setPower(l);
   }

   public void setPower(double power) {
       this.power = power;
   }

    /**
     * @param use RUN_USING_ENCODER
     */
    public void useEncoders(boolean use) {
        if (use) {
            lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * @param use ZeroPowerBehavior.BRAKE
     */
    public void useBrake(boolean use) {
        if (use) {
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    /**
     * Encoder method for moving lift
     * @param ticks to travel to
     */
    public void move(int ticks) {
        tickGoal = ticks;
        //lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setTargetPosition(tickGoal);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(power);
        while (lift.isBusy()) {
            telem.addData("TickGoal", getTickGoal());
            telem.addData("CurrentPos", lift.getCurrentPosition());
            telem.addData("Speed", lift.getPower());
            telem.update();
        }
        lift.setPower(STOP);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Increases currentLevel by 1
     */
    public void increaseLevel() {
        if (currentLevel + 1 <= MAX_LEVEL) {
            currentLevel++;
        }
    }

    /**
     * Decreases currentLevel by 1
     */
    public void decreaseLevel() {
        if (currentLevel - 1 >= MIN_LEVEL) {
            currentLevel--;
        }
    }

    /**
     * @param level of currentLevel
     */
    public void setLevel(int level) {
        currentLevel = level;
    }

    /**
     * Updates position of lift motor
     */
    public void updateLevel() {
        switch (currentLevel) {
            case 0:
                currentTick = LEVEL_ZERO;
                break;
            case 1:
                currentTick = LEVEL_ONE;
                break;
            case 2:
                currentTick = LEVEL_TWO;
                break;
            case 3:
                currentTick = LEVEL_THREE;
                break;
            case 4:
                currentTick = LEVEL_FOUR;
                break;
            case 5:
                currentTick = LEVEL_FIVE;
                break;
            case -1:
                currentTick = LEVEL_SUBONE;
                break;
            case -2:
                currentTick = LEVEL_SUBTWO;
                break;
            default:
                break;
        }
        if (lift.getCurrentPosition() > getCurrentTick() + 10 || lift.getCurrentPosition() < getCurrentTick() - 10) {
            move(currentTick);
        }
    }

    /**
     * @param power out of 1 of the max power
     */
    public void setMaxPower(double power) {
        this.power = power;
    }

    public void setState(LiftState state) {
        liftState = state;
    }

    public void setTimer(ElapsedTime time) {
        liftTimer = time;
    }

    public void setLiftTimeout(ElapsedTime time) { liftTimeout = time; }

    public ElapsedTime getLiftTimeout() {
        return liftTimeout;
    }

    /**
     * @return tickGoal of lift
     */
    public int getTickGoal() {
        return tickGoal;
    }

    /**
     * @return currentTick
     */
    public int getCurrentTick() {
        return currentTick;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public LiftState getState() {
        return liftState;
    }

    public ElapsedTime getTimer() {
        return liftTimer;
    }
}
