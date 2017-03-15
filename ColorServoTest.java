package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Hasan on 12/1/2016.
 */
@TeleOp(name="ColorTest", group="Test")
public class ColorServoTest extends OpMode {

    Servo buttonPresser;

    ColorSensor color;
    @Override
    public void init() {
        buttonPresser = hardwareMap.servo.get("button");
        color = hardwareMap.colorSensor.get("color");
    }

    @Override
    public void loop() {
        color.enableLed(false);
        if(color.red() < color.blue()) {
            buttonPresser.setPosition(1.0);
        } else {
            buttonPresser.setPosition(0.0);
        }
    }
}
