/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.hardware.modernrobotics.ModernRoboticsTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.tree.DCTree;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single 
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class HardwareRadabot
{
    /* Public OpMode members. */
    // set up the drive train, set = null that way it doesnt run
    // set up every motor/servo
    public DcMotor  leftFrontDrive = null;
    public DcMotor  leftBackDrive = null;
    public DcMotor  rightFrontDrive = null;
    public DcMotor  rightBackDrive = null;
    public DcMotor  plateDrive = null;

    //color sensor arm servo
    public Servo  jewelServoBlue = null;

    //four glyph grabing servos
    public Servo glyphServo1 = null;
    public Servo glyphServo2 = null;
    public Servo glyphServo3 = null;
    public Servo glyphServo4 = null;

    //relic servos and motors
    public Servo relicClaw = null;
    public Servo relicLift = null;
    public DcMotor  lift = null;
    public DcMotor relic = null;

    //lift "bottom out" notification servo flag
    public Servo liftFlag = null;

    //color sensor setup
    public ModernRoboticsI2cColorSensor blueColor = null;

    //touch sensor set up
    public TouchSensor liftTouch = null;

    //part of set up for Core Device Interface
    DeviceInterfaceModule cdi;


    //this is to initialize the end and start position of the jewel servo fairly self explanatory
    // set all locations and positions for jewel servos
    public final static double JEWEL_SERVO_BLUE_START = 0.1;

    //set up and down positions for the lift "bottom out" notification servo flag
    public final static double FLAG_UP = 0.0;
    public final static double FLAG_DOWN = 0.5;

    //set power for the lift motor
    public final static double down = 0.5;
    public final static double up = 1.0;



     public final static double GLYPH1_START = 0.8;
   public final static double GLYPH2_START = 0.0;
   public final static double GLYPH3_START = 0.1;
   public final static double GLYPH4_START = 0.85;
   public final static double GLYPH1_OPEN = 0.24;
   public final static double GLYPH2_OPEN = 0.44;
   public final static double GLYPH3_OPEN = 0.7;
   public final static double GLYPH4_OPEN = 0.3;
   public final static double GLYPH1_CLOSED = 0.6;
   public final static double GLYPH2_CLOSED = 0.2;
   public final static double GLYPH3_CLOSED = 0.32;
   public final static double GLYPH4_CLOSED = 0.7;



    // set all locations and positions for the glyph servos


    // set positions for relic claw
    public final static double RELIC_CLAW_OPEN = 0.0;
    public final static double RELIC_CLAW_CLOSED = 0.80;



    public static int blueColorNumber;


    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Public OpMode members. */
    public double threshold1 = 0.1;
    public double threshold2 = -0.1;

    // glyphState represents the state of the glyph claw.
    boolean glyphState = true;

    /* Constructor */
    public HardwareRadabot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        //Initialize color sensor for jewel sensing
        blueColor = hwMap.get(ModernRoboticsI2cColorSensor.class, "blueColor");

        //touch sensor set up for driver lift notification (when it hits the bottom)
        liftTouch = hwMap.touchSensor.get("lift_touch");

        // turn on the LED for the color sensor (Active mode)
        blueColor.enableLed(true);

        blueColorNumber = blueColor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER);

        // Define and Initialize Motors
        leftFrontDrive = hwMap.get(DcMotor.class, "left_Front_Drive");
        leftBackDrive = hwMap.get(DcMotor.class, "left_Back_Drive");
        rightBackDrive = hwMap.get(DcMotor.class, "right_Back_Drive");
        rightFrontDrive = hwMap.get(DcMotor.class, "right_Front_Drive");

        //this sets the left side to go the reverse direction because of how the motors are set up.
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        plateDrive = hwMap.get(DcMotor.class, "plate_Drive");

        lift = hwMap.get(DcMotor.class, "lift");

        relic = hwMap.get(DcMotor.class, "relic");
        // Set all motors to zero power
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        plateDrive.setPower(0);
        relic.setPower(0);


        // RUN_USING_ENCODERS on drivetrain.
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Define and initialize ALL installed servos.
        relicLift = hwMap.get(Servo.class,"relic_lift");
        relicLift.setPosition(down);
        relicClaw = hwMap.get(Servo.class, "relic_claw");
        relicClaw.setPosition(RELIC_CLAW_CLOSED);
        jewelServoBlue = hwMap.get(Servo.class, "jewel_blue");
        jewelServoBlue.setPosition(JEWEL_SERVO_BLUE_START);
        glyphServo1 = hwMap.get(Servo.class, "glyph1");
        glyphServo1.setPosition(GLYPH1_START);
        glyphServo2 = hwMap.get(Servo.class, "glyph2");
        glyphServo2.setPosition(GLYPH2_START);
        glyphServo3 = hwMap.get(Servo.class, "glyph3");
        glyphServo3.setPosition(GLYPH3_START);
        glyphServo4 = hwMap.get(Servo.class, "glyph4");
        glyphServo4.setPosition(GLYPH4_START);
       // relicClaw.setPosition(RELIC_CLAW_STOP);

        //initialize and set initialize position for liftFlag
        liftFlag = hwMap.get(Servo.class, "lift_Flag");
        liftFlag.setPosition(FLAG_DOWN);


    }
        //method for opening claw
        public void openClaw()
        {
            glyphServo1.setPosition(GLYPH1_OPEN);
            glyphServo2.setPosition(GLYPH2_OPEN);
            glyphServo3.setPosition(GLYPH3_OPEN);
            glyphServo4.setPosition(GLYPH4_OPEN);
            glyphState = true;
        }

        //method for closing claw
        public void closeClaw()
        {
            glyphServo1.setPosition(GLYPH1_CLOSED);
            glyphServo2.setPosition(GLYPH2_CLOSED);
            glyphServo3.setPosition(GLYPH3_CLOSED);
            glyphServo4.setPosition(GLYPH4_CLOSED);
            glyphState = false;
        }

    // method to move the plate to the blue color sensor side
    public void bluePlateDistance( double power, int distance)
    {
        //reset encoder
        plateDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        plateDrive.setTargetPosition(distance);

        //set to run to position mode
        plateDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //set drive power
        plateDrive.setPower(power);

        while(plateDrive.isBusy())
        {
            //wait for motor to reach position
        }

        //stop motor
        plateDrive.setPower(0);

        //reset encoder
        plateDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // method to move the plate to the red color sensor side
    public void redPlateDistance( double power, int distance)
    {
        //reset encoder
        plateDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        plateDrive.setTargetPosition(-distance);

        //set to run to position mode
        plateDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //set drive power
        plateDrive.setPower(power);

        while(plateDrive.isBusy())
        {
            //wait for motor to reach position
        }

        //stop motor
        plateDrive.setPower(0);

        //reset encoder
        plateDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // method to move robot to turn to the right (two parameters)
    public void turnRightDistance (double power, int distance)
    {
        // reset encoders
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        leftFrontDrive.setTargetPosition(distance);
        leftBackDrive.setTargetPosition(distance);
        rightFrontDrive.setTargetPosition(-distance);
        rightBackDrive.setTargetPosition(-distance);

        //set to run to position mode
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //set drive power
        leftFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightFrontDrive.setPower(power);
        rightBackDrive.setPower(power);

        while(leftFrontDrive.isBusy() && leftBackDrive.isBusy() && rightFrontDrive.isBusy() && rightBackDrive.isBusy())
        {
            //wait for motors to reach position
        }

        //stop all motors
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);

        // reset encoders
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // method to move robot to turn to the left (two parameters)
    public void turnLeftDistance (double power, int distance)
    {
        // reset encoders
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        leftFrontDrive.setTargetPosition(-distance);
        leftBackDrive.setTargetPosition(-distance);
        rightFrontDrive.setTargetPosition(distance);
        rightBackDrive.setTargetPosition(distance);

        //set to run to position mode
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //set drive power
        leftFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightFrontDrive.setPower(power);
        rightBackDrive.setPower(power);

        while(leftFrontDrive.isBusy() && leftBackDrive.isBusy() && rightFrontDrive.isBusy() && rightBackDrive.isBusy())
        {
            //wait for motors to reach position
        }

        //stop all motors
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);

        // reset encoders
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //method to move the robot forward (two parameters)
    public void driveForwardDistance (double power, int distance)
    {
        // reset encoders
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        leftFrontDrive.setTargetPosition(distance);
        leftBackDrive.setTargetPosition(distance);
        rightFrontDrive.setTargetPosition(distance);
        rightBackDrive.setTargetPosition(distance);

        //set to run to position mode
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //set drive power
        leftFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightFrontDrive.setPower(power);
        rightBackDrive.setPower(power);

        while(leftFrontDrive.isBusy() && leftBackDrive.isBusy() && rightFrontDrive.isBusy() && rightBackDrive.isBusy())
        {
            //wait for motors to reach position
        }

        //stop all motors
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);

        // reset encoders
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //method to move the robot backward (two parameters)
    public void driveBackDistance (double power, int distance)
    {
        // reset encoders
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //set target position
        leftFrontDrive.setTargetPosition(-distance);
        leftBackDrive.setTargetPosition(-distance);
        rightFrontDrive.setTargetPosition(-distance);
        rightBackDrive.setTargetPosition(-distance);

        //set to run to position mode
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //set drive power
        leftFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        rightFrontDrive.setPower(power);
        rightBackDrive.setPower(power);

        while(leftFrontDrive.isBusy() && leftBackDrive.isBusy() && rightFrontDrive.isBusy() && rightBackDrive.isBusy())
        {
            //wait for motors to reach position
        }

        //stop all motors
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);

        // reset encoders
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
