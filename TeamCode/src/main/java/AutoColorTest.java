import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;
import android.graphics.Color;

/*
 * Autonomous code for identify the red jewel and knocking it off and then parking in the nearest
 * parking zone.
 */

@Disabled
@Autonomous(name="AutoColorTest", group="Auto")

public class AutoColorTest extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareRadabot         robot   = new HardwareRadabot();   // Use a Radabot's hardware

    private ElapsedTime runtime = new ElapsedTime();

    //===================== Color Sensor Setup ===========================

    // hsvValues is an array that will hold the hue, saturation, and value information.
    private float hsvValues[] = {0F,0F,0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;
    //====================================================================


    //
    private int autoTrip = 0;
    private boolean direction = false;
    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        //set color sensor address
        robot.blueColor.setI2cAddress(I2cAddr.create7bit(0x10));

        //wait for player to hit start
        waitForStart();

        // convert the RGB values to HSV values.
        Color.RGBToHSV(robot.blueColor.red() * 8, robot.blueColor.green() * 8, robot.blueColor.blue() * 8, hsvValues);

        //move the servo into detect position
        robot.jewelServoBlue.setPosition(1.0);

        sleep(250);

        robot.bluePlateDistance(0.5, 500);

        while(opModeIsActive() && autoTrip <1 && getRuntime() < 6.0)
        {

            // send the info back to driver station using telemetry function.
            telemetry.addData("Clear", robot.blueColor.alpha());
            telemetry.addData("Red ", robot.blueColor.red());
            telemetry.addData("Green", robot.blueColor.green());
            telemetry.addData("Blue ", robot.blueColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();


            if (robot.blueColor.red() > robot.blueColor.blue() && robot.blueColor.red() > robot.blueColor.green())
            {
                //drive forward power and distance
                robot.driveForwardDistance(1.0, 150);

                //increment so robot only passes through sequence once
                autoTrip ++;

                //helps track the direction the robot moved following color detection
                direction = true;
            }

            //if color sensor is blue, drive backward
            if (robot.blueColor.blue() > robot.blueColor.red() && robot.blueColor.blue() > robot.blueColor.green())
            {

                //drive backward power and distance
                robot.driveBackDistance(0.4, 150);

                // increment so robot only passes through sequence once
                autoTrip++;
            }
        }

        //retract slide plate to cener
        robot.bluePlateDistance(0.5,-500);

        //wait a quarter of a second
        sleep(250);

        //return color sensor arm to start position
        robot.jewelServoBlue.setPosition(HardwareRadabot.JEWEL_SERVO_BLUE_START);

        //wait a quarter of a second
        sleep (250);


        //if the robot drove backward to hit the red jewel then drive forward more to park
        if(direction = true)
        {
            robot.driveForwardDistance(1.0, 2000);
        }

        // if the robot drove forward to hit the red jewel then drive forward more to park
        else
        {
            robot.driveForwardDistance(1.0, 1800);
        }


    }


}






