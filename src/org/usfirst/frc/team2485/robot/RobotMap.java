package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.robot.subsystems.IntakeRollers;
import org.usfirst.frc.team2485.util.EncoderWrapperRateAndDistance;
import org.usfirst.frc.team2485.util.InvertedAbsoluteEncoder;
import org.usfirst.frc.team2485.util.SpeedControllerWrapper;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	public static int scIntakeRollerPort1 = 1;
	public static int scIntakeRollerPort2 = 2;
	public static int scIntakeArmPort1 = 3;
	
	public static IntakeRollers intakeRollers = new IntakeRollers();
	public static IntakeArm intakeArm = new IntakeArm();
	public static DriveTrain driveTrain = new DriveTrain();
	
	public static VictorSP intakeRollerLateralsc = new VictorSP(scIntakeRollerPort1);
	public static VictorSP intakeRollerIntakesc = new VictorSP(scIntakeRollerPort2);
	public static VictorSP intakeArmVictorSP = new VictorSP(scIntakeArmPort1);
	public static VictorSP[] rightDriveVictorSPs = {
													new VictorSP(5), new VictorSP(6), new VictorSP(7)
													};
	public static VictorSP[] leftDriveVictorSPs = {
			new VictorSP(2), new VictorSP(3), new VictorSP(4)
			};
	
	public static SpeedControllerWrapper intakeArmSC = new SpeedControllerWrapper(intakeArmVictorSP);
	public static SpeedControllerWrapper rightDrive = new SpeedControllerWrapper(rightDriveVictorSPs);
	public static SpeedControllerWrapper leftDrive = new SpeedControllerWrapper(leftDriveVictorSPs);
	
	public static InvertedAbsoluteEncoder intakeAbsEncoder = new InvertedAbsoluteEncoder(new AnalogPotentiometer(0));
	
	public static Encoder leftDriveEnc = new Encoder(3, 2);
	public static Encoder rightDriveEnc = new Encoder(4, 5);
	
	public static EncoderWrapperRateAndDistance leftDistEncoder = new EncoderWrapperRateAndDistance(leftDriveEnc, PIDSourceType.kDisplacement);
	public static EncoderWrapperRateAndDistance leftRateEncoder = new EncoderWrapperRateAndDistance(leftDriveEnc, PIDSourceType.kRate);
	public static EncoderWrapperRateAndDistance rightDistEncoder = new EncoderWrapperRateAndDistance(rightDriveEnc, PIDSourceType.kDisplacement);
	public static EncoderWrapperRateAndDistance rightRateEncoder = new EncoderWrapperRateAndDistance(rightDriveEnc, PIDSourceType.kRate);
	
	public static AHRS ahrs = new AHRS(SPI.Port.kMXP);



	
}
