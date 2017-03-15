package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Hasan on 12/1/2016.
 */
@TeleOp(name="Launcher Test", group="Test")
public class LauncherTest extends OpMode {

    DcMotor launcher;

    //sets several constants
    final static int ENCODER_CPR_LAUNCHER = 1680;
    final static double GEAR_RATIO_LAUNCHER = 4;
    final static int ROTATIONS_LAUNCHER = 4;

    //sets value to be sent to encoder
    final static double COUNTS_LAUNCHER = ENCODER_CPR_LAUNCHER * ROTATIONS_LAUNCHER * GEAR_RATIO_LAUNCHER;
    @Override
    public void init() {
        launcher = hardwareMap.dcMotor.get("launcher");
    }

    @Override
    public void loop() {

        launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        launcher.setTargetPosition((int) COUNTS_LAUNCHER);

        launcher.setPower(1.0);

        while(launcher.isBusy()) {
            launcher.setPower(1.0);
        }

        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
}
