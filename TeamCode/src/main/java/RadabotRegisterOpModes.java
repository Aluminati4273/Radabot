

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

/**
 *Register opmodes.
 */
public class RadabotRegisterOpModes
{
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager) {

        // Our Team opmodes
        manager.register ("VuMark", VuMark.class);
        manager.register("RadabotTeleOp", RadabotTeleOp.class);
        manager.register("ColorCheck",ColorCheck.class);
        manager.register("AutoBlue1", AutoBlue1.class);
        manager.register("AutoBlue2", AutoBlue2.class);
        manager.register("AutoRed1", AutoRed1.class);
        manager.register("AutoRed2", AutoRed2.class);
        manager.register("AutoColorTest", AutoColorTest.class);
        manager.register("DriveByEncoderSimple", DriveByEncoderSimple.class);
        manager.register("DriveByEncoderSimple2", DriveByEncoderSimple2.class);
        manager.register("AutoBlue1Advanced",AutoBlue1Advanced.class);
        manager.register("GyroTesting",GyroTesting.class);
        manager.register("AutoBlue2Advanced", AutoBlue2Advanced.class);


      // Basic Templates
      // manager.register("Iterative Opmode",       BasicOpMode_Iterative.class);
    }
}
