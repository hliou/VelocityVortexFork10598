package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import static org.firstinspires.ftc.teamcode.MethodSlave.beaconCheckIn;
import static org.firstinspires.ftc.teamcode.MethodSlave.beaconCheckOut;
import static org.firstinspires.ftc.teamcode.MethodSlave.encoderForward;
import static org.firstinspires.ftc.teamcode.MethodSlave.lineApproach;
import static org.firstinspires.ftc.teamcode.MethodSlave.realEncoderForwardLeft;
import static org.firstinspires.ftc.teamcode.MethodSlave.shootOne;

/**
 * Created by Fluff on 2/26/2017.
 */

//sets program name and group on phone, and groups are in alphabetic order
@Autonomous(name="Beacons Park Red", group="Beacon")
public class VVBeaconsRed extends LinearOpMode {

    //initialize motors, servos, booleans, and sensors
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor loader;
    DcMotor launcher;

    Servo buttonPresser;
    Servo floodgate;

    ColorSensor color;

    OpticalDistanceSensor eopd;

    TouchSensor touch;

    GyroSensor gyro;

    ModernRoboticsI2cRangeSensor rangeFront;

    @Override
    public void runOpMode() throws InterruptedException {
        //initializes components to names on phone
        leftMotor = hardwareMap.dcMotor.get("left");
        rightMotor = hardwareMap.dcMotor.get("right");
        loader = hardwareMap.dcMotor.get("loader");
        launcher = hardwareMap.dcMotor.get("launcher");

        buttonPresser = hardwareMap.servo.get("button");
        floodgate = hardwareMap.servo.get("floodGate");

        color = hardwareMap.colorSensor.get("color");

        eopd = hardwareMap.opticalDistanceSensor.get("eopd");

        touch = hardwareMap.touchSensor.get("touch");

        gyro = hardwareMap.gyroSensor.get("gyro");

        rangeFront = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "frange");

        //close the floodgate
        floodgate.setPosition(1);
        buttonPresser.setPosition(1);
        //waits for user to press start
        waitForStart();

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderForward(-2, -1.0, leftMotor, rightMotor,opModeIsActive());
        shootOne(floodgate, launcher, opModeIsActive());
        encoderForward(-28, -1.0, leftMotor, rightMotor, opModeIsActive());
        realEncoderForwardLeft(15, 1.0, leftMotor, rightMotor, opModeIsActive());
        encoderForward(65.7, 1.0, leftMotor, rightMotor, opModeIsActive());
        realEncoderForwardLeft(5, 1.0, leftMotor, rightMotor, opModeIsActive());
        encoderForward(31, 1.0, leftMotor, rightMotor, opModeIsActive());
        leftMotor.setPower(-0.2);
        rightMotor.setPower(0.2);
        sleep(50);
        lineApproach(0.25, 0.2, 11, true, leftMotor, rightMotor, eopd, rangeFront, opModeIsActive());

        beaconCheckOut(buttonPresser);
        sleep(700);
        beaconCheckIn(buttonPresser);
        sleep(700);

        if(color.blue() > color.red()){
            sleep(4400);
        }

        while(color.blue() > color.red()){
            beaconCheckOut(buttonPresser);
            sleep(700);
            beaconCheckIn(buttonPresser);
            sleep(700);
        }

        lineApproach(0.25, 0.2, 11, true, leftMotor, rightMotor, eopd, rangeFront, opModeIsActive());

        beaconCheckOut(buttonPresser);
        sleep(700);
        beaconCheckIn(buttonPresser);
        sleep(700);

        if(color.blue() > color.red()){
            sleep(4400);
        }

        while(color.blue() > color.red()){
            beaconCheckOut(buttonPresser);
            sleep(700);
            beaconCheckIn(buttonPresser);
            sleep(700);
        }
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderForward(-28, -1.0, leftMotor, rightMotor,opModeIsActive());
    }


}
