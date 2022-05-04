package org.firstinspires.ftc.teamcode.hardware;

public interface Constants {
//nuts
    int STOP = 0;

    double forwardPos = 0 / 300.0;
    double neutralPos = 90 / 300.0;
    double backPos = 225 / 300.0;

    double armStartPos = 300.0 / 300.0;
    double armExtendPos = 174 / 300.0;
    double armRestPos = 198 / 300.0;

    // 96 mm wheels = 537.6/((120 / 25.4)*Math.PI)
    // 120 mm wheels = (537.6/((120 / 25.4)*Math.PI)) * (5.0/6.0)
    //double TICKS_PER_IN = 537.6/((96 / 25.4)*Math.PI);

    double TICKS_PER_IN = (537.6/((120 / 25.4)*Math.PI));

    int LIFT_THRESHOLD = -500;

    int MIN_LEVEL = -2;
    int MAX_LEVEL = 5;

    int LEVEL_SUBTWO = 0;
    int LEVEL_SUBONE = 0;

    int LEVEL_ZERO = 0;

    int LEVEL_ONE = 900;
    int LEVEL_TWO = 1700;
    int LEVEL_THREE = 1100;
    int LEVEL_FOUR = 3100;

    //int LEVEL_FOUR = 0;
    int LEVEL_FIVE = 0;

    int SPIN_TIME = 1400;
    int DUMP_TIME = 500;
    int RETRACT_TIMEOUT = 7000;

    // 250 | 125
    // 1.05 | 1.13
    // -0.5
    double SPIN_RATE_MS = 125;
    double SPIN_RATE_MULT = 1.13;
    double SPIN_RATE_START = -0.5;

    int BARRIER_FORWARD = (int) (1.4 * 537.6);
    int TURN_ONE = (int) (1.3 * 537.6);
    int TURN_TWO = (int) (2.8 * 537.6);
    int HUB_FORWARD = (int) (0.3 * 537.6);
    int BARRIER_SHARED = (int) (-0.8 * 537.6);
    int TURN_SHARED = (int) (-0.3 * 537.6);
    int SHARED_BARRIER = (int) (0);

    /*
        barcode[0]: redLeft
        barcode[1]: redRight
        barcode[2]: blueLeft
        barcode[3]: blueRight
        barcode[i][0] / barcode[i][1]: BarcodePosition.LEFT coordinates x/y
        barcode[i][2] / barcode[i][3]: BarcodePosition.CENTER coordinates x/y
        barcode[i][4] / barcode[i][5]: BarcodePosition.RIGHT coordinates x/y
    */

    int[][] barcodeCoordinate = {
            {
                    (12 + 74), 128,
                    ( 110 + 80), 128,
                    (215 + 80), 128
            }, {
                90, 128,
                195, 128,
                290, 128
            }, {
                25, 128,
                115, 128,
                215, 128
            }, {
                //9, 135 | 110, 140 | 220, 140
                // 6, 130 | 105, 130 | 213, 135
                10, 128,
                110, 128,
                205, 128
            }
    };

    enum StartPos {
        REDLEFT, REDRIGHT,
        BLUELEFT, BLUERIGHT
    }

    enum Side {
        RED, BLUE
    }

    enum Status {
        FORWARDS, BACKWARDS,
        UP, DOWN,
        LEFT, RIGHT,
        OPEN, CLOSE,
        NEUTRAL,
        NORTH, SOUTH, EAST, WEST,
        RED, GREEN, BLUE,
        DARK, LIGHT,
        NORMAL, AUTO, SLOW, FAST;
    }
}
