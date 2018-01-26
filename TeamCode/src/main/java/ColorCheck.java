

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.robotcore.internal.hardware.DragonboardGPIOPin;

public class ColorCheck extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareRadabot         robot   = new HardwareRadabot();   // Use Radabot's hardware

    public void runOpMode()
    {
        robot.init(hardwareMap);

        //Set color sensor I2C addresses

        robot.blueColor.setI2cAddress(I2cAddr.create7bit(0x20));

        while (opModeIsActive())
        {

        }
    }

    public int colorValue()
    {
        return robot.blueColorNumber;
    }


}
