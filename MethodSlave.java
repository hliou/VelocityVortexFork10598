package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import static java.lang.Thread.sleep;

/**
 * Created by Hasan on 12/1/2016.
 */

public class MethodSlave {

    //sets several constants
    private final static int ENCODER_CPR = 1120;
    private final static double GEAR_RATIO = 1;
    private final static int WHEEL_DIAMETER = 3;

    //gets circumference of wheel
    private final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;

    //sets several constants
    // changed to neverest 40 encoder values but kept as ENCODER_CPR_NEVEREST60 for convenience of coding
    private final static int ENCODER_CPR_NEVEREST60 = 1120;
    private final static double GEAR_RATIO_NEVEREST60 = 4;
    private final static int ROTATIONS_NEVEREST60 = 1;

    //sets value to be sent to encoder
    private final static double COUNTS_NEVEREST60 = ENCODER_CPR_NEVEREST60 * ROTATIONS_NEVEREST60 * GEAR_RATIO_NEVEREST60;

    public static void rangeLeft(double range, double speed, DcMotor leftMotor, DcMotor rightMotor, ModernRoboticsI2cRangeSensor frange, boolean opModeIsActive){
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(opModeIsActive){
            if(frange.getDistance(DistanceUnit.CM) > range){
                while ((frange.getDistance(DistanceUnit.CM) > range)) {
                    leftMotor.setPower(speed);
                    rightMotor.setPower(speed);
                }
            } else if (frange.getDistance(DistanceUnit.CM) <= range){
                leftMotor.setPower(0);
                rightMotor.setPower(0);
            }
        } else if (!opModeIsActive){
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }

    }

    public static void rangeRight(double range, double speed, DcMotor leftMotor, DcMotor rightMotor, ModernRoboticsI2cRangeSensor frange, boolean opModeIsActive){
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(opModeIsActive){
            if(frange.getDistance(DistanceUnit.CM) > range){
                while ((frange.getDistance(DistanceUnit.CM) > range)) {
                    leftMotor.setPower(-speed);
                    rightMotor.setPower(-speed);
                }
            } else if (frange.getDistance(DistanceUnit.CM) <= range){
                leftMotor.setPower(0);
                rightMotor.setPower(0);
            }
        } else if (!opModeIsActive){
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }

    }

    public static void encoderSlow(double distance, double speed, DcMotor leftMotor, DcMotor rightMotor, boolean opModeIsActive) {
        double rotations = distance / CIRCUMFERENCE;
        double counts = ENCODER_CPR * rotations * GEAR_RATIO;

        //start encoder run cycle, turns to next beacon
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setTargetPosition((int) counts);
        rightMotor.setTargetPosition((int) -counts);

        leftMotor.setPower(-speed * 0.80);
        rightMotor.setPower(speed);

        while (leftMotor.isBusy() && rightMotor.isBusy() && opModeIsActive) {
            leftMotor.setPower(-speed * 0.8);
            rightMotor.setPower(speed);
        }
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public static void encoderForward(double distance, double speed, DcMotor leftMotor, DcMotor rightMotor, boolean opModeIsActive) {
        double rotations = distance / CIRCUMFERENCE;
        double counts = ENCODER_CPR * rotations * GEAR_RATIO;

        //start encoder run cycle, turns to next beacon
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setTargetPosition((int) counts);
        rightMotor.setTargetPosition((int) -counts);

        leftMotor.setPower(-speed*0.8);
        rightMotor.setPower(speed);

        while (leftMotor.isBusy() && rightMotor.isBusy() && opModeIsActive) {
            leftMotor.setPower(-speed*0.8);
            rightMotor.setPower(speed);
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //end of cycle
    }

    public static void gyroForward(double distance, double speed, DcMotor leftMotor, DcMotor rightMotor, GyroSensor gyro, boolean opModeIsActive) {
        double rotations = distance / CIRCUMFERENCE;
        double counts = ENCODER_CPR * rotations * GEAR_RATIO;

        //sets gyro to 1
        gyro.calibrate();

        //start encoder run cycle, turns to next beacon
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setTargetPosition((int) counts);
        rightMotor.setTargetPosition((int) -counts);

        leftMotor.setPower(speed);
        rightMotor.setPower(-speed);


        while (leftMotor.isBusy() && opModeIsActive) {
            if((gyro.getHeading())>1 && (gyro.getHeading())<20 && opModeIsActive){
                //if the robot is angled right, make the left motor slower
                leftMotor.setPower(speed * 0.75);
                rightMotor.setPower(-speed);
            }
            else if (((gyro.getHeading()) <= 359) && ((gyro.getHeading() >= 339)) && opModeIsActive ){
                //if the robot is angled left, make the right motor slower
                leftMotor.setPower(speed);
                rightMotor.setPower(-speed * 0.75);
            }
            else{
                //just go straight when facing straight
                leftMotor.setPower(speed);
                rightMotor.setPower(-speed);
            }
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //end of cycle
    }

    public static void realEncoderForwardLeft(double distance, double speed, DcMotor leftMotor, DcMotor rightMotor, boolean opModeIsActive) {
        double rotations = distance / CIRCUMFERENCE;
        double counts = ENCODER_CPR * rotations * GEAR_RATIO;

        //start encoder run cycle, turns to next beacon
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setTargetPosition((int) -counts);
        rightMotor.setTargetPosition((int) -counts);

        leftMotor.setPower(-speed);
        rightMotor.setPower(-speed);

        while (leftMotor.isBusy() && opModeIsActive) {
            leftMotor.setPower(-speed);
            rightMotor.setPower(-speed);
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //end of cycle
    }

    public static void realEncoderForwardRight(double distance, double speed, DcMotor leftMotor, DcMotor rightMotor, boolean opModeIsActive) {
        double rotations = distance / CIRCUMFERENCE;
        double counts = ENCODER_CPR * rotations * GEAR_RATIO;

        //start encoder run cycle, turns to next beacon
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setTargetPosition((int) counts);
        rightMotor.setTargetPosition((int) counts);

        leftMotor.setPower(speed);
        rightMotor.setPower(speed);

        while (leftMotor.isBusy() && opModeIsActive) {
            leftMotor.setPower(speed);
            rightMotor.setPower(speed);
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //end of cycle
    }

    public static void gyroTurn(double angle, double speed, boolean isLeft, DcMotor leftMotor, DcMotor rightMotor,GyroSensor gyro,
                                boolean opModeIsActive) {
        if (isLeft) {

            while ( opModeIsActive && (gyro.getHeading() > (360 - angle) || gyro.getHeading() <= 2)) {
                if (opModeIsActive) {
                    leftMotor.setPower(-speed);
                    rightMotor.setPower(-speed);
                } else {
                    return;
                }
            }

            leftMotor.setPower(0);
            rightMotor.setPower(0);
        } else {

            while (opModeIsActive && (gyro.getHeading() < angle || gyro.getHeading() == 0)) {
                if (opModeIsActive) {
                    leftMotor.setPower(speed);
                    rightMotor.setPower(speed);
                } else {
                    return;
                }
            }

            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }
    }

    public static void swingLeft(double angle, double speed, DcMotor leftMotor, DcMotor rightMotor, GyroSensor gyro,
                                 boolean opModeIsActive) {

        while (opModeIsActive && (gyro.getHeading() > (360 - angle) || (gyro.getHeading() <= 2))) {
            if (opModeIsActive) {
                leftMotor.setPower(0);
                rightMotor.setPower(-speed);
            } else {
                return;
            }
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    public static void swingRight(double angle, double speed, DcMotor leftMotor, DcMotor rightMotor, GyroSensor gyro,
                                  boolean opModeIsActive) {

        while (opModeIsActive && (gyro.getHeading() < angle || gyro.getHeading() == 0)) {
            if (opModeIsActive) {
                leftMotor.setPower(speed);
                rightMotor.setPower(0);
            } else {
                return;
            }
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    public static void shootOne(Servo floodgate, DcMotor launcher, boolean opModeIsActive) {
        floodgate.setPosition(1);
        //start encoder run cycle, turns to next beacon
        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        launcher.setTargetPosition((int) -COUNTS_NEVEREST60);

        launcher.setPower(-1.0);

        while (launcher.isBusy() && opModeIsActive) {
            launcher.setPower(-1.0);
        }

        launcher.setPower(0);

        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //end of cycle
        floodgate.setPosition(1);
    }

    public static void shootTwo (Servo floodgate, DcMotor launcher, boolean opModeIsActive)  throws InterruptedException{
        floodgate.setPosition(1);
        //start encoder run cycle, turns to next beacon
        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        launcher.setTargetPosition((int) -COUNTS_NEVEREST60);

        launcher.setPower(-1.0);

        while (launcher.isBusy() && opModeIsActive) {
            launcher.setPower(-1.0);
        }

        launcher.setPower(0);

        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //end of cycle
        floodgate.setPosition(0);
        sleep(1000);
        floodgate.setPosition(1);

        launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        launcher.setTargetPosition((int) -COUNTS_NEVEREST60);

        launcher.setPower(-1.0);

        while (launcher.isBusy() && opModeIsActive) {
            launcher.setPower(-1.0);
        }

        launcher.setPower(0);

        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //end of cycle
    }

    public static void wallAlign(double distance, double speed, DcMotor leftMotor, DcMotor rightMotor,
                                 ModernRoboticsI2cRangeSensor frange, boolean opModeIsActive){
        double rotations = distance / CIRCUMFERENCE;
        double counts = ENCODER_CPR * rotations * GEAR_RATIO;

        //start encoder run cycle, turns to next beacon
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setTargetPosition((int) counts);
        rightMotor.setTargetPosition((int) -counts);

        leftMotor.setPower(speed*0.8);
        rightMotor.setPower(-speed);

        while (opModeIsActive) {
            if (frange.getDistance(DistanceUnit.CM) > 13) {
                leftMotor.setPower(-speed);
                rightMotor.setPower(speed / 2.5);
            } else if (frange.getDistance(DistanceUnit.CM) < 9) {
                leftMotor.setPower(-speed / 2.5);
                rightMotor.setPower(speed);
            }
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public static void lineApproach(double intensity, double speed, double range, boolean isWhiteLine, DcMotor leftMotor, DcMotor rightMotor,
                                    OpticalDistanceSensor eopd, ModernRoboticsI2cRangeSensor frange, boolean opModeIsActive) {
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        if(isWhiteLine) {
            while (opModeIsActive) {
                if (eopd.getLightDetected() < intensity) {
                    frange.getDistance(DistanceUnit.CM);
                    if (frange.getDistance(DistanceUnit.CM) < range) {
                        leftMotor.setPower(speed * 0.3);
                        rightMotor.setPower(-speed);

                    } else if (frange.getDistance(DistanceUnit.CM) > range) {
                        leftMotor.setPower(speed);
                        rightMotor.setPower(-speed * 0.3);

                    } else {
                        leftMotor.setPower(speed);
                        rightMotor.setPower(-speed);
                    }
                }else if(eopd.getLightDetected() > intensity) {
                    break;
                }
            }

            leftMotor.setPower(0);
            rightMotor.setPower(0);
        } /* else {
           while (opModeIsActive && (eopd.getLightDetected() > intensity)) {
               leftMotor.setPower(speed);
               rightMotor.setPower(-speed);
           }
           leftMotor.setPower(0);
           rightMotor.setPower(0);
       }

   }
*/}
    public static void BackApproach(double intensity, double speed, boolean isWhiteLine, DcMotor leftMotor, DcMotor rightMotor,
                                    OpticalDistanceSensor eopd, ModernRoboticsI2cRangeSensor frange, boolean opModeIsActive){
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(isWhiteLine) {
            while (opModeIsActive) {
                if (eopd.getLightDetected() < intensity) {
                    frange.getDistance(DistanceUnit.CM);
                    if (frange.getDistance(DistanceUnit.CM) < 11) {
                        leftMotor.setPower(-speed / 2.5);
                        rightMotor.setPower(speed);

                    } else if (frange.getDistance(DistanceUnit.CM) > 11) {
                        leftMotor.setPower(-speed);
                        rightMotor.setPower(speed / 2.5);

                    } else {
                        leftMotor.setPower(-speed);
                        rightMotor.setPower(speed);
                    }
                }else if(eopd.getLightDetected() > intensity) {
                    break;
                }
            }

            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }
    }
    public static void lineFollow (double intensity, double speed, boolean isWhiteLine, DcMotor leftMotor, DcMotor rightMotor,
                                   TouchSensor touch, OpticalDistanceSensor eopd, boolean opModeIsActive) {
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(isWhiteLine) {
            while (!touch.isPressed() && opModeIsActive) {
                if(eopd.getLightDetected() < intensity) {
                    leftMotor.setPower(speed);
                    rightMotor.setPower(0);
                }
                else {
                    rightMotor.setPower(-speed);
                    leftMotor.setPower(0);
                }
            }

            leftMotor.setPower(0);
            rightMotor.setPower(0);
        } else {
            while (!touch.isPressed() && opModeIsActive) {
                if(eopd.getLightDetected() > intensity) {
                    leftMotor.setPower(speed);
                    rightMotor.setPower(0);
                }
                else {
                    rightMotor.setPower(-speed);
                    leftMotor.setPower(0);
                }
            }

            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }
    }

    public static void beaconCheckOut(Servo buttonPresser) {
        //checks color sensor value and presses beacon

        buttonPresser.setPosition(0);

    }

    public static void beaconCheckIn(Servo buttonPresser) {
        //checks color sensor value and presses beacon

        buttonPresser.setPosition(1.0);

    }
}

