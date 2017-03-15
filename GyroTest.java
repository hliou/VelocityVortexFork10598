package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Hasan on 12/1/2016.
 */
@TeleOp(name="Gyro Test", group="Test")
public class GyroTest extends OpMode {

    GyroSensor gyro;
    @Override
    public void init() {

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
    }

    @Override
    public void loop() {
            telemetry.addData("Heading: ", gyro.getHeading());
    }
}
