package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Hasan on 12/1/2016.
 */
@TeleOp(name="EOPD Test", group="Test")
public class EOPDTest extends OpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;

    OpticalDistanceSensor eopd;
    @Override
    public void init() {

        leftMotor = hardwareMap.dcMotor.get("left");
        rightMotor = hardwareMap.dcMotor.get("right");

        eopd = hardwareMap.opticalDistanceSensor.get("eopd");
    }

    @Override
    public void loop() {
        if(eopd.getLightDetected() < 0.25) {
            leftMotor.setPower(0.2);
            rightMotor.setPower(0);
        }
        else {
            rightMotor.setPower(-0.2);
            leftMotor.setPower(0);
        }

        telemetry.addData("light reflectance: ", eopd.getLightDetected());
    }
}
