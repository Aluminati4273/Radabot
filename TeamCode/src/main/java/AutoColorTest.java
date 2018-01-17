import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by londonjenks on 1/5/18.
 */


@Autonomous(name="AutoColorTest", group="Auto")

public class AutoColorTest extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareRadabot         robot   = new HardwareRadabot();   // Use a Radabot's hardware

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        //Set color sensor I2C addresses

        robot.blueColor.setI2cAddress(I2cAddr.create7bit(0x20));


        waitForStart();

        {

            //Turn on LED of Color Sensors (both)

            robot.blueColor.enableLed(true);

            //check color on sensor
            robot.redPlateDistance(.6, 500);


        }

    }


}
