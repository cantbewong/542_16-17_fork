package org.whs542.ftc2017;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.whs542.ftc2017.subsys.WHSRobot;

/**
 * Created by Amar2 on 10/22/2016.
 */
public class TestTeleOp extends OpMode{

    WHSRobot robot;

    @Override
    public void init() {
        robot = new WHSRobot(hardwareMap);
    }

    @Override
    public void loop() {

        telemetry.addData("LeftStick Y", gamepad1.left_stick_y);
        telemetry.addData("LeftStck X", gamepad1.right_stick_y);

        telemetry.addData("Trigger", gamepad1.left_trigger);

        robot.drivetrain.setRLPower(gamepad1.left_stick_y, gamepad1.right_stick_y);
        robot.intake.runIntake(gamepad1.left_bumper, gamepad1.left_trigger, 1.0);


    }
}
