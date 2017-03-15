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
import static org.firstinspires.ftc.teamcode.MethodSlave.gyroTurn;
import static org.firstinspires.ftc.teamcode.MethodSlave.lineApproach;
import static org.firstinspires.ftc.teamcode.MethodSlave.realEncoderForwardLeft;
import static org.firstinspires.ftc.teamcode.MethodSlave.shootTwo;
import static org.firstinspires.ftc.teamcode.MethodSlave.swingLeft;
import static org.firstinspires.ftc.teamcode.MethodSlave.swingRight;

/**
 * Created by Hasan on 12/1/2016.
 */

//sets program name and group on phone, and groups are in alphabetic order
@Autonomous(name="Center Shoot Two Beacons Park Blue", group="Beacon")
public class VVShootTwoBeaconsParkCenterBlue extends LinearOpMode {

    //sets the shooting distance, can be a simple fix after consistency testing
    private double SHOOTING_DISTANCE = 7;

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

        encoderForward(2, 1.0, leftMotor, rightMotor, opModeIsActive());
        shootTwo(floodgate, launcher, opModeIsActive());
        encoderForward(26, 1.0, leftMotor, rightMotor, opModeIsActive());
        realEncoderForwardLeft(10.524335, 0.3, leftMotor, rightMotor, opModeIsActive());

        //we run into the wall to create the constant
        encoderForward(65, -1.0, leftMotor, rightMotor, opModeIsActive());
        swingRight(92, 0.25, leftMotor, rightMotor, gyro, opModeIsActive());
        lineApproach(0.15, 0.15, 13, true, leftMotor, rightMotor, eopd, rangeFront, opModeIsActive());
        do {
            beaconCheckOut(buttonPresser);
            sleep(700);
            beaconCheckIn(buttonPresser);
            sleep(700);
        } while (color.blue() < color.red());

        lineApproach(0.15, 0.15, 11, true, leftMotor, rightMotor, eopd, rangeFront, opModeIsActive());

        do {
            beaconCheckOut(buttonPresser);
            sleep(700);
            beaconCheckIn(buttonPresser);
            sleep(700);
        } while (color.blue() < color.red());

        //turn toward cap ball and bump
        swingLeft(130, 0.3,leftMotor, rightMotor, gyro, opModeIsActive());
        encoderForward(80.823374593, 1.0, leftMotor, rightMotor, opModeIsActive());
        }
    }



