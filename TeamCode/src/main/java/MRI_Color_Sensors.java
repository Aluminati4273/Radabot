package org.firstinspires.ftc.teamcode;

/*
Modern Robotics Color Sensors Example with color number
Created 9/29/2016 by Colton Mehlhoff of Modern Robotics using FTC SDK 2.2
Reuse permitted with credit where credit is due

Configuration:
I2CDevice "ca" (MRI Color Sensor with I2C address 0x3a (0x1d 7-bit)
I2CDevice "cc" (MRI Color Sensor with default I2C address 0x3c (0x1e 7-bit)

ModernRoboticsI2cColorSensor class is not being used because it can not access color number.
ColorSensor class is not being used because it can not access color number.

To change color sensor I2C Addresses, go to http://modernroboticsedu.com/mod/lesson/view.php?id=96
Support is available by emailing support@modernroboticsinc.com.
*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.TouchSensor;



@Autonomous

public class MRI_Color_Sensors extends OpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    byte[] colorRedCache;
    byte[] colorBlueCache;

    I2cDevice colorRed;
    I2cDevice colorBlue;
    I2cDeviceSynch colorRedReader;
    I2cDeviceSynch colorBlueReader;


    boolean LEDState = true;     //Tracks the mode of the color sensor; Active = true, Passive = false

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        //the below lines set up the configuration file
        colorRed = hardwareMap.i2cDevice.get("colorRed");
        colorRed = hardwareMap.i2cDevice.get("colorBlue");

        colorRedReader = new I2cDeviceSynchImpl(colorRed, I2cAddr.create8bit(0x3a), false);
        colorBlueReader = new I2cDeviceSynchImpl(colorBlue, I2cAddr.create8bit(0x3c), false);

        colorRedReader.engage();
        colorBlueReader.engage();


    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();

        if(LEDState){
            colorRedReader.write8(3, 0);    //Set the mode of the color sensor using LEDState
            colorBlueReader.write8(3, 0);    //Set the mode of the color sensor using LEDState
        }
        else{
            colorRedReader.write8(3, 1);    //Set the mode of the color sensor using LEDState
            colorBlueReader.write8(3, 1);    //Set the mode of the color sensor using LEDState
        }
        //Active - For measuring reflected light. Cancels out ambient light
        //Passive - For measuring ambient light, eg. the FTC Color Beacon
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());


        colorRedCache = colorRedReader.read(0x04, 1);
        colorBlueCache = colorBlueReader.read(0x04, 1);

        //display values
        telemetry.addData("1 #A", colorRedCache[0] & 0xFF);
        telemetry.addData("2 #C", colorBlueCache[0] & 0xFF);

        telemetry.addData("3 A", colorRedReader.getI2cAddress().get8Bit());
        telemetry.addData("4 A", colorBlueReader.getI2cAddress().get8Bit());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}