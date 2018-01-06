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

        waitForStart();

        //The suffering never ends. You just become numb to the pain.

        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_DETECT);
        robot.bluePlateDistance(.6,500);
        sleep(500);
        robot.driveForwardDistance(1.0,1000);
        robot.bluePlateDistance(.6, -500);
        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_START);

    }


}
