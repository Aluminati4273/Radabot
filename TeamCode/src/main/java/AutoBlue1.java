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

        robot.init(hardwareMap);

        waitForStart();

        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_DETECT);

        // Turn On RUN_TO_POSITION for plate motoer
        robot.plateDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.plateDrive.setPower(0.7);
        robot.plateDrive.setTargetPosition(plateTarget);


        encoder.encoderDrive(encoder.DRIVE_SPEED, 15, 15,3.0 );

    }


}
