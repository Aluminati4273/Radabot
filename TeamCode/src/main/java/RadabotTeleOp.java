/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;

/**
 *
 *
 */

@TeleOp(name="RadabotTeleOp", group="Radabot")

public class RadabotTeleOp extends LinearOpMode {


    /* Declare OpMode members. */
    HardwareRadabot   robot = new HardwareRadabot();           // Use Hardware from HardwareRadabot

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
            left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;

            // left drive for robot using gamepad1 sticks (tank drive)
            robot.leftFrontDrive.setPower(left);
            robot.leftBackDrive.setPower(left);

            //right drive for robot using gamepad1 sticks (tank drive)
            robot.rightFrontDrive.setPower(right);
            robot.rightBackDrive.setPower(right);

            // lift up using gamepad2 left stick
            robot.lift.setPower(gamepad2.left_stick_y);

            // control plate drive using gamepad2 right stick
            robot.plateDrive.setPower(-gamepad2.right_stick_x/2.5);

            /* run the close claw method(if claw is open) when gamepad1 right trigger is pushed */
           if(robot.glyphState && (Math.abs(gamepad2.left_trigger)>robot.threshold1)){
               robot.closeClaw();
               telemetry.addData("Claw Position","CLOSED");
               telemetry.update();
           }

            /* run the open claw method(if claw is closed) when gamepad1 left trigger is pushed */
            if(!robot.glyphState && ((Math.abs(gamepad2.right_trigger)>robot.threshold1)))
            {
                robot.openClaw();
                telemetry.addData("Claw Position","OPEN");
                telemetry.update();
            }

            if(gamepad2.a = true)
            {
                robot.relic.setPower(0.9);
            }

            if(gamepad2.b = true)
            {
                robot.relic.setPower(-0.9);
            }

        }

    }
}
