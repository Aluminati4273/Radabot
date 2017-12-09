import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by londonjenks on 12/5/17.
 */


@Autonomous(name="AutoBlue1", group="Auto")

public class AutoBlue1 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareRadabot         robot   = new HardwareRadabot();   // Use a Radabot's hardware
    DriveByEncoder  encoder = new DriveByEncoder(); // Use DriveByEncoder class
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        //stop and reset encoders on plate motor
        //robot.plateDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set plate motor to run using encoder
        //robot.plateDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int plateTarget = robot.plateDrive.getCurrentPosition() + 2200;
        int plateTarget1 = robot.plateDrive.getCurrentPosition() - 2200;

        waitForStart();
        sleep(250);
        //drop the servo arm (with color sensor)
        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_DETECT);
        telemetry.addData("I'm working:", "yep!");
        telemetry.update();
        sleep(250);

        // Turn On RUN_TO_POSITION for plate motor, set power, and run to position
        robot.plateDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.plateDrive.setTargetPosition(plateTarget);
        robot.plateDrive.setPower(0.7);
        telemetry.addData("I'm also working:", "down here!");
        telemetry.update();

        while (opModeIsActive() && (robot.plateDrive.isBusy()))
        {
            telemetry.addData("I'm actually not working", "I'm waiting...");
            telemetry.update();
            telemetry.addData("PlateDrive motor position ",  robot.plateDrive.getCurrentPosition());
            telemetry.update();
        }

        /*
         * // Move plate drive by time and power (bad idea)
         * robot.plateDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         * robot.plateDrive.setPower(0.8);
         * sleep(700);
         * robot.plateDrive.setPower(0);
         */

        //check color on sensor


        //if color sensor is blue, drive forward


        //else if color sensor is red, drive backward


        //retract plate slide using encoder and motor position
        robot.plateDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.plateDrive.setTargetPosition(plateTarget1);
        robot.plateDrive.setPower(0.7);
        telemetry.addData("I'm also working:", "down here!");
        telemetry.update();

        while (opModeIsActive() && (robot.plateDrive.isBusy()))
        {
            telemetry.addData("I'm actually not working", "I'm waiting...");
            telemetry.update();
            telemetry.addData("PlateDrive motor position ",  robot.plateDrive.getCurrentPosition());
            telemetry.update();
        }

        /*
         * // Move plate drive by time and power (bad idea)
         * sleep(2050);
         * robot.plateDrive.setPower(-0.8);
         * sleep(700);
         * robot.plateDrive.setPower(0);
         * telemetry.addData("I'm not working:", "because I'm lazy!");
         * telemetry.update();
         */

        //retract the servo arm
        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_START);
        sleep(250);


        // move robot forward (following retraction of the color sensor and the slide plate)
        encoder.encoderDrive(encoder.DRIVE_SPEED, 15, 15,3.0 );

    }


}
