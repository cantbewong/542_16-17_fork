package org.whs542.ftc2017;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whs542.ftc2017.subsys.WHSRobot;

/**
 * Created by Amar2 on 10/22/2016.
 */
@TeleOp(name = "TeleOpTest", group = "TeleOp")
public class TestTeleOp extends OpMode{

    WHSRobot robot;

    @Override
    public void init() {
        robot = new WHSRobot(hardwareMap);
    }

    @Override
    public void loop() {

        telemetry.addData("LeftStick Y", gamepad1.left_stick_y);
        telemetry.addData("LeftStick X", gamepad1.right_stick_y);

        telemetry.addData("Trigger", gamepad1.left_trigger);

        telemetry.addData("FWheelStat:", robot.flywheel.getStatus());
        telemetry.addData("FGateStat:", robot.flywheel.getGateStatus());

        robot.drivetrain.setRLPower(gamepad1.left_stick_y, gamepad1.right_stick_y);
        robot.intake.runIntake(gamepad1.left_bumper, gamepad1.left_trigger, 1.0);
        robot.flywheel.run(gamepad1.right_bumper, 0.5);
        robot.flywheel.operateGate(gamepad1.a);



    }
}
