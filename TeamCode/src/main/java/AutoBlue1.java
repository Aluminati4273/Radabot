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

        //stop and reset encoders on plate motor
        robot.plateDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set plate motor to run using encoder
        robot.plateDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int plateTarget = robot.plateDrive.getCurrentPosition() + 2200;
        int plateTarget1 = robot.plateDrive.getCurrentPosition() - 2200;

        robot.init(hardwareMap);

        waitForStart();

        //drop the servo arm (with color sensor)
        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_DETECT);

        // Turn On RUN_TO_POSITION for plate motor, set power, and run to position (see above)
        robot.plateDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.plateDrive.setPower(0.7);
        robot.plateDrive.setTargetPosition(plateTarget);

        //check color on sensor


        //if color sensor is blue, drive forward


        //else if color sensor is red, drive backward


        //retract plate slide
        robot.plateDrive.setPower(0.7);
        robot.plateDrive.setTargetPosition(plateTarget1);

        //retract the servo arm
        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_START);


        // move robot forward (following retraction of the color sensor and the slide plate)
        encoder.encoderDrive(encoder.DRIVE_SPEED, 15, 15,3.0 );

    }


}
