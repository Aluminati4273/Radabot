/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * General TeleOp Code - does lots of things...
 *
 */

@TeleOp(name="RadabotTeleOp", group="Radabot")

public class RadabotTeleOp extends LinearOpMode {


    /* Declare OpMode members. */
    HardwareRadabot robot = new HardwareRadabot();           // Use Hardware from HardwareRadabot

    // Declare

    @Override
    public void runOpMode() {
        double left;
        double right;


        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            left = -gamepad1.left_stick_y ;
            right = -gamepad1.right_stick_y ;


            // left drive for robot using gamepad1 sticks (tank drive)
            robot.leftFrontDrive.setPower(left);
            robot.leftBackDrive.setPower(left);

            //right drive for robot using gamepad1 sticks (tank drive)
            robot.rightFrontDrive.setPower(right);
            robot.rightBackDrive.setPower(right);

            // lift up using gamepad2 left stick
            robot.lift.setPower(gamepad2.left_stick_y);

            // control plate drive using gamepad2 right stick
            robot.plateDrive.setPower(-gamepad2.right_stick_x / 4.0);


            /* run the close claw method(if claw is open) when gamepad1 right trigger is pushed */
            if (robot.glyphState && (Math.abs(gamepad2.left_trigger) > robot.threshold1)) {
                robot.closeClaw();
                telemetry.addData("Claw Position", "CLOSED");
                telemetry.update();
            }

            /* run the open claw method(if claw is closed) when gamepad1 left trigger is pushed */
            if (!robot.glyphState && ((Math.abs(gamepad2.right_trigger) > robot.threshold1))) {
                robot.openClaw();
                telemetry.addData("Claw Position", "OPEN");
                telemetry.update();
            }


            //control for the relic extension arm

            // move relic extension out
            while (gamepad2.a) {
                robot.relic.setPower(1.0);
            }

            // move relic extension in
            while (gamepad2.b) {
                robot.relic.setPower(-1.0);
            }

            // stop moving the relic extension
            if (!gamepad2.a || !gamepad2.b) {
                robot.relic.setPower(0.0);
            }

            // move the relic claw incrementally if less than 1.0 position
            if (gamepad1.x)
            {
                if(robot.relicClaw.getPosition()< 1.0)
                {
                    robot.relicClaw.setPosition(robot.relicClaw.getPosition() + 0.05);
                }
            }

            // move the reclic claw incrementally if more than 0.0 position
            if (gamepad1.y)
            {
                if(robot.relicClaw.getPosition() > 0.0)
                {
                    robot.relicClaw.setPosition(robot.relicClaw.getPosition() - 0.05);
                }
            }

            // move the relic up (horizontal) if less than 1.0 position
            if (gamepad1.left_bumper)
            {
                if(robot.relicLift.getPosition()< 1.0)
                {
                    robot.relicLift.setPosition(robot.relicLift.getPosition() + 0.025);
                }
            }

            //move the relic down (vertical) if more than 0.0 position
            if(gamepad1.right_bumper)
            {
                if(robot.relicLift.getPosition()> 0.0)
                {
                    robot.relicLift.setPosition(robot.relicLift.getPosition() - 0.025);
                }
            }





            //set "bottom out" flag notification position based on touch sensor value, otherwise it is down
            if(robot.liftTouch.isPressed())
            {
                robot.liftFlag.setPosition(robot.FLAG_UP);
            }
            else
            {
                robot.liftFlag.setPosition(robot.FLAG_DOWN);
            }

        }
    }
}
