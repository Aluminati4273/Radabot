import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by londonjenks on 12/5/17.
 */


@Autonomous(name="AutoBlue1", group="Auto")

public class AutoBlue1 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareRadabot         robot   = new HardwareRadabot();   // Use a Radabot's hardware

    private ElapsedTime runtime = new ElapsedTime();

    int b = 1;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to see");
        telemetry.update();

       // ================== Color Sensor Code and setup =================
        //set the I2C address for the attached color sensor (change from the default)
        robot.blueColor.setI2cAddress(I2cAddr.create7bit(0x10));

        //hsvValues is an array that will hold the hue, saturation, and value information
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array
        final float values[] = hsvValues;

        //turn off the LED on the color sensor
        robot.blueColor.enableLed(false);

        //convert the RGB values to HSV Values
        Color.RGBToHSV(robot.blueColor.red() * 8, robot.blueColor.green() * 8, robot.blueColor.blue()* 8, hsvValues);
        // ==============================================================

        waitForStart();

        boolean direction = false;

        //wait half a second
        sleep(500);

        //turn on the color sensor LED
        robot.blueColor.enableLed(true);

        //move the color sensor arm into the sensing position
        robot.jewelServoBlue.setPosition(1.0);

        sleep(250);

        // slide the plate drive into position (toward the blue color sensor side)
        robot.bluePlateDistance(0.5, 500);

        while(opModeIsActive())
        {
            // check to see if the color sensed is red, drive forward (know off the red jewel in front of the sensor)
            if (robot.blueColor.red() > robot.blueColor.green() && robot.blueColor.red() > robot.blueColor.blue())
            {
                telemetry.addData("Hey", robot.blueColor.red());
                telemetry.update();
                robot.driveForwardDistance(.50, 1000);
                direction = true;
            }

            //if color sensor is blue, drive backward (knock off the red jewel behind the sensor)
            if(robot.blueColor.blue() > robot.blueColor.red() && robot.blueColor.blue() > robot.blueColor.green())
            {
                robot.driveBackDistance(.50, 200);
                telemetry.addData("color", robot.blueColor.red());
                telemetry.update();
            }

            telemetry.addData("Clear", robot.blueColor.alpha());
            telemetry.addData("Red", robot.blueColor.red());
            telemetry.addData("Green", robot.blueColor.green());
            telemetry.addData("Blue", robot.blueColor.blue());
            telemetry.addData("Hue",hsvValues[0]);

            telemetry.update();

            sleep(200);

            robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_START);

            robot.redPlateDistance(1.0, 500)
            ;

            if(direction = true)
            {
                robot.driveForwardDistance(1.0, 800);
            }

            if(direction = false)
            {
                robot.driveForwardDistance(1.0, 1200);
            }
        }





    }


}
