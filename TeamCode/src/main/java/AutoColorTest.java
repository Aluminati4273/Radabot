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
    DriveByEncoder  encoder = new DriveByEncoder(); // Use DriveByEncoder class
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        //Set color sensor I2C addresses
        robot.redColor.setI2cAddress(I2cAddr.create7bit(0x10));
        robot.blueColor.setI2cAddress(I2cAddr.create7bit(0x20));


        waitForStart();

        {

            //Turn on LED of Color Sensors (both)
            robot.redColor.enableLed(true);
            robot.blueColor.enableLed(true);

            while (opModeIsActive())
            {

                telemetry.addLine();
                telemetry.addData("Blue Color Number", robot.blueColor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
                telemetry.update();
                telemetry.addLine();
                telemetry.addData("Red Color Number", robot.redColor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
                telemetry.update();
            }

            //check color on sensor


            //if color sensor is blue, drive forward
            while (robot.redColorNumber < 4 && robot.redColorNumber >2)
            {
                robot.leftFrontDrive.setPower(1.0);
                robot.leftBackDrive.setPower(1.0);
                robot.rightFrontDrive.setPower(1.0);
                robot.rightBackDrive.setPower(1.0);
            }

            //if color sensor is red, drive backward
            while(robot.redColorNumber <11 && robot.redColorNumber > 8)
            {
                robot.leftFrontDrive.setPower(-1.0);
                robot.leftBackDrive.setPower(-1.0);
                robot.rightFrontDrive.setPower(-1.0);
                robot.rightBackDrive.setPower(-1.0);
            }


            //else if color sensor is red, drive backward


            //retract the servo arm


            // move robot forward (following retraction of the color sensor and the slide plate)
        }

    }


}
