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

    //
    private int autoTrip = 0;
    private boolean direction = false;
    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        robot.blueColor.setI2cAddress(I2cAddr.create7bit(0x20));

        waitForStart();


        robot.jewelServoBlue.setPosition(0.95);

        robot.bluePlateDistance(0.5, 500);

        while(opModeIsActive() && autoTrip <1)
        {
            if (robot.blueColorNumber < 4 && robot.blueColorNumber > 2)
            {
                robot.driveBackDistance(1.0, 700);
                autoTrip ++;
                direction = true;
            }

            //if color sensor is blue, drive backward
            if(robot.blueColorNumber < 11 && robot.blueColorNumber > 8)
            {


                robot.driveForwardDistance(1.0, 200);
                autoTrip++;
            }
        }

        robot.bluePlateDistance(0.5,-500);

        sleep(250);

        robot.jewelServoBlue.setPosition(robot.JEWEL_SERVO_BLUE_START);

        sleep (250);


        if(direction = true) {
            robot.driveForwardDistance(1.0, 1200);
        }
        else{
            robot.driveForwardDistance(1.0, 1600);
        }









    }


}
