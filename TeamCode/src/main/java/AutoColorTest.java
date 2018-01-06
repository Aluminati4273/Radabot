import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by londonjenks on 1/5/18.
 */


@Autonomous(name="AutoColorTest", group="Auto")

public class AutoColorTest extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareRadabot         robot   = new HardwareRadabot();   // Use a Radabot's hardware
    DriveByEncoder  encoder = new DriveByEncoder(); // Use DriveByEncoder class
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);



        waitForStart();
        {

            //Turn on LED of Color Sensors (both)
            robot.redColor.enableLed(true);
            robot.blueColor.enableLed(true);

            while (opModeIsActive())
            {
                telemetry.addData("Blue Color Number", robot.blueColor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
                telemetry.update();
                telemetry.addData("Red Color Number", robot.redColor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
                telemetry.update();
            }

            //check color on sensor


            //if color sensor is blue, drive forward
           // if (robot.blueColor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER = ModernRoboticsI2cColorSensor.Register.BLUE))
            {

            }


            //else if color sensor is red, drive backward


            //retract the servo arm


            // move robot forward (following retraction of the color sensor and the slide plate)
        }

    }


}
