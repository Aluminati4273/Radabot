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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.tree.DCTree;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
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
    public Servo  jewelServoBlue = null;
    public Servo jewelServoRed = null;
    public Servo glyphServo1 = null;
    public Servo glyphServo2 = null;
    public Servo glyphServo3 = null;
    public Servo glyphServo4 = null;
   // public Servo relicClaw = null;
 //   public Servo relicGrabber = null;
    public DcMotor  lift = null;
    public DcMotor relic = null;
    public ModernRoboticsI2cColorSensor redColor = null;
    public ModernRoboticsI2cColorSensor blueColor = null;


    //servo start position/end position
    //this is to initialize the end and start position of the jewel servo fairly self explanatory
    // set all locations and positions for jewel servos
    public final static double JEWEL_SERVO_BLUE_START = 0.0;
    public final static double JEWEL_SERVO_RED_START = 1.0;
    public final static double JEWEL_SERVO_BLUE_DETECT = 0.95;
    public final static double JEWEL_SERVO_RED_DETECT = 0.05;
    //setting the stop positions to 0.50 because a continuous servo always thinks it is centered
    //might need to adjust this later
    // someone kill 4273 programmer
   // public final static double RELIC_CLAW_STOP = 0.50;
   // public final static double RELIC_GRABBER_STOP = 0.50;
    // set all locations and positions for the glyph servos
    public final static double GLYPH1_START = 0.8;
    public final static double GLYPH2_START = 0.0;
    public final static double GLYPH3_START = 0.85;
    public final static double GLYPH4_START = 0.1;
    public final static double GLYPH1_OPEN = 0.05;
    public final static double GLYPH2_OPEN = 0.6;
    public final static double GLYPH3_OPEN = 0.15;
    public final static double GLYPH4_OPEN = 0.775;
    public final static double GLYPH1_CLOSED = 0.6;
    public final static double GLYPH2_CLOSED = 0.2;
    public final static double GLYPH3_CLOSED = 0.7;
    public final static double GLYPH4_CLOSED = 0.3;

    public final static double platePower = 0.5;


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

        //Initialize two color sensors for jewels
        blueColor = hwMap.get(ModernRoboticsI2cColorSensor.class, "blueColor");
        redColor = hwMap.get(ModernRoboticsI2cColorSensor.class, "redColor");

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
        jewelServoRed = hwMap.get(Servo.class, "jewel_red");
        jewelServoRed.setPosition(JEWEL_SERVO_RED_START);
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
            //Why does God put us on Earth? Is it just to suffer?
        //method for closing claw
        public void closeClaw()
        {
            glyphServo1.setPosition(GLYPH1_CLOSED);
            glyphServo2.setPosition(GLYPH2_CLOSED);
            glyphServo3.setPosition(GLYPH3_CLOSED);
            glyphServo4.setPosition(GLYPH4_CLOSED);
            glyphState = false;
        }

}
