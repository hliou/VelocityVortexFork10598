package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.MethodSlave.gyroTurn;
import static org.firstinspires.ftc.teamcode.MethodSlave.swingRight;

/**
 * Created by Hasan on 12/1/2016.
 */

//sets program name and group on phone, and groups are in alphabetic order
@Autonomous(name="Test 180", group="Test")
public class Test180 extends LinearOpMode {

    //initialize motors, servos, booleans, and sensors
    DcMotor leftMotor;
    DcMotor rightMotor;
    GyroSensor gyro;
    Servo buttonPresser;

    @Override
    public void runOpMode() throws InterruptedException {
        //initializes components to names on phone
        leftMotor = hardwareMap.dcMotor.get("left");
        rightMotor = hardwareMap.dcMotor.get("right");
        gyro = hardwareMap.gyroSensor.get("gyro");
        buttonPresser = hardwareMap.servo.get("button");

        buttonPresser.setPosition(0);

        //waits for user to press start
        waitForStart();

        gyro.calibrate();
        while (gyro.isCalibrating()) {
            leftMotor.setPower(0);
        }
        swingRight(167, 0.4, leftMotor, rightMotor, gyro, opModeIsActive());
    }


}
