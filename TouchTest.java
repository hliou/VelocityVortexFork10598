package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Hasan on 1/2/2017.
 */

@TeleOp(name = "Touch Sensor Test", group = "Test")
public class TouchTest extends OpMode {

    DcMotor leftMotor;
    TouchSensor touch;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("left");
        touch = hardwareMap.touchSensor.get("touch");
    }

    @Override
    public void loop() {
        if(touch.isPressed()) {
            leftMotor.setPower(1);
        } else {
            leftMotor.setPower(0);
        }
    }
}
