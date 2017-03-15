package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Hasan on 11/13/2016.
 */

@TeleOp(name="Driver Control", group="TeleOp")
public class VelocityVortexTank extends OpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor loader;
    DcMotor launcher;
    DcMotor noodler;

    Servo buttonPresser;
    Servo floodgate;

    boolean isBackWheelDrive;

    //sets several constants
    final static int ENCODER_CPR = 1600;
    final static double GEAR_RATIO = 4;

    final static int ROTATIONS = 1;

    //sets value to be sent to encoder
    final static double COUNTS1 = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

    @Override
    public void init() {

        leftMotor = hardwareMap.dcMotor.get("left");
        rightMotor = hardwareMap.dcMotor.get("right");
        loader = hardwareMap.dcMotor.get("loader");
        launcher = hardwareMap.dcMotor.get("launcher");
        noodler = hardwareMap.dcMotor.get("noodler");

        buttonPresser = hardwareMap.servo.get("button");
        floodgate = hardwareMap.servo.get("floodGate");

        isBackWheelDrive = false;
        floodgate.setPosition(1);
        buttonPresser.setPosition(0);
    }

    @Override
    public void loop() {

        if (isBackWheelDrive) {
            float leftDrive = -gamepad1.left_stick_y;
            float rightDrive = gamepad1.right_stick_y;

            rightMotor.setPower(leftDrive);
            leftMotor.setPower(rightDrive);

            if(gamepad2.a) {
                noodler.setPower(1.0);
            }

            else if(gamepad2.x) {
                loader.setPower(-0.5);
                noodler.setPower(-1.0);
            } else {
                loader.setPower(0);
                noodler.setPower(0);
            }

            if(gamepad1.y || gamepad2.y) {
                launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                launcher.setPower(-0.1);
            } else if(gamepad1.b || gamepad2.b) {
                launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                launcher.setPower(0.1);
            } else {
                launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                launcher.setPower(0);
            }

            if (gamepad2.right_bumper || gamepad1.right_bumper) {
                buttonPresser.setPosition(1);
            } else if (gamepad2.left_bumper || gamepad1.left_bumper){
                buttonPresser.setPosition(0);
            } else {
            buttonPresser.setPosition(0);
        }
            if (gamepad1.dpad_up) {
                floodgate.setPosition(0);

                launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                launcher.setTargetPosition((int) -COUNTS1);

                launcher.setPower(-1.0);

                while(launcher.isBusy()) {
                    launcher.setPower(-1.0);
                }

                floodgate.setPosition(1);
                launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if (gamepad1.x) {
                isBackWheelDrive = false;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {

            float leftDrive = gamepad1.left_stick_y;
            float rightDrive = -gamepad1.right_stick_y;

            rightMotor.setPower(rightDrive);
            leftMotor.setPower(leftDrive);

            if(gamepad2.a) {
                noodler.setPower(1.0);
            }

            else if(gamepad2.x) {
                loader.setPower(-0.5);
                noodler.setPower(-1.0);
            } else {
                loader.setPower(0);
                noodler.setPower(0);
            }

            if(gamepad1.y || gamepad2.y) {
                launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                launcher.setPower(-0.1);
            } else if(gamepad2.b || gamepad2.b) {
                launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                launcher.setPower(0.1);
            } else {
                launcher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                launcher.setPower(0);
            }

            if (gamepad2.right_bumper || gamepad1.right_bumper) {
                buttonPresser.setPosition(1);
            }
            else if (gamepad2.left_bumper || gamepad1.left_bumper) {
                buttonPresser.setPosition(0);
            } else {
                buttonPresser.setPosition(0);
            }

            if (gamepad1.dpad_up) {
                floodgate.setPosition(0);

                launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                launcher.setTargetPosition((int) -COUNTS1);

                launcher.setPower(-1.0);

                while(launcher.isBusy()) {
                    launcher.setPower(-1.0);
                }

                floodgate.setPosition(1);
                launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if (gamepad1.x) {
                isBackWheelDrive = true;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        telemetry.addData("Is Front Wheel Drive?", isBackWheelDrive);
    }
}