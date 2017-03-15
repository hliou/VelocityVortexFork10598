package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Hasan on 1/10/2017.
 */

@TeleOp(name = "Encoder Test", group = "test")
public class EncoderTest extends OpMode {
    DcMotor andymark;

    @Override
    public void init() {
        andymark = hardwareMap.dcMotor.get("launcher");
    }

    @Override
    public void loop() {
        andymark.setPower(1.0);
        telemetry.addData("encoder value:", andymark.getCurrentPosition());
    }
}
