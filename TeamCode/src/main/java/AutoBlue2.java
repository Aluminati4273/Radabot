import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by londonjenks on 12/5/17.
 */


@Autonomous(name="AutoBlue1", group="Auto")

public class AutoBlue2 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareRadabot         robot   = new HardwareRadabot();   // Use a Radabot's hardware

    private ElapsedTime runtime = new ElapsedTime();

    //used to restrict while loop for color sensing to one trip
    private int autoTrip = 0;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        //set the correct color sensor address
        robot.blueColor.setI2cAddress(I2cAddr.create7bit(0x20));

        waitForStart();

        //move the color sensor arm into position
        robot.jewelServoBlue.setPosition(0.95);

        //slide plate toward the jewel
        robot.bluePlateDistance(0.5, 500);

        while(opModeIsActive() && autoTrip <1)
        {
            //if colors sensor is blue, drive backward
            if (robot.blueColorNumber < 4 && robot.blueColorNumber > 2)
            {
                //drive backward
                robot.driveBackDistance(1.0, 500);
                //increment autoTrip
                autoTrip ++;
            }

            //if color sensor is red, drive forward
            if(robot.blueColorNumber < 11 && robot.blueColorNumber > 8)
            {
                //drive forward
                robot.driveForwardDistance(1.0, 500);
                //increment autoTrip
                autoTrip++;
            }
        }

        //retract slide plate into starting position
        robot.bluePlateDistance(0.5,-500);

        // wait a quarter of a second
        sleep(250);

        //retract color sensor arm into stating position
        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_START);

        // wait a quarter of a second
        sleep (250);

        //drive forward toward the parking zone
        robot.driveForwardDistance(0.7, 900);

        // wait a quarter of a second
        sleep (250);

        //turn toward the parking zone
        robot.turnRightDistance(0.7, 500);

        // wait a quarter of a second
        sleep (250);

        //drive into the parking zone
        robot.driveForwardDistance(0.7, 700);
    }


}
