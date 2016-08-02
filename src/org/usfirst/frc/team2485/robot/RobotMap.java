package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.robot.subsystems.IntakeRollers;
import org.usfirst.frc.team2485.util.ConstantsIO;
import org.usfirst.frc.team2485.util.EncoderWrapperRateAndDistance;
import org.usfirst.frc.team2485.util.InvertedAbsoluteEncoder;
import org.usfirst.frc.team2485.util.LidarWrapper;
import org.usfirst.frc.team2485.util.SpeedControllerWrapper;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public static int scLateralRollerPort = 9;
	public static int scIntakeRollerPort = 8;
	public static int scIntakeArmPort1 = 1;

	public static void init() {

		if (Robot.isSimulation()) {
			intakeRollerLateralsc = new Talon(scLateralRollerPort);
			intakeRollerIntakesc = new Talon(scIntakeRollerPort);
			intakeArmSC = new SpeedControllerWrapper(
					new Talon(scIntakeArmPort1));
			rightDrive = new SpeedControllerWrapper(new Talon(5));
			leftDrive = new SpeedControllerWrapper(new Talon(2));
		} else {
			intakeRollerLateralsc = new VictorSP(scLateralRollerPort);
			intakeRollerIntakesc = new VictorSP(scIntakeRollerPort);
			intakeArmVictorSP = new VictorSP(scIntakeArmPort1);
			rightDriveVictorSPs = new VictorSP[] { new VictorSP(5), new VictorSP(6),
					new VictorSP(7) };
			leftDriveVictorSPs = new VictorSP[] { new VictorSP(2), new VictorSP(3),
					new VictorSP(4) };

			intakeArmSC = new SpeedControllerWrapper(
					intakeArmVictorSP);
			rightDrive = new SpeedControllerWrapper(
					rightDriveVictorSPs);
			leftDrive = new SpeedControllerWrapper(
					leftDriveVictorSPs);
		}

		intakeAbsEncoder = new InvertedAbsoluteEncoder(
				new AnalogPotentiometer(0));
		
		if (Robot.isSimulation()) {
			leftDriveEnc = new Encoder(2, 3);
		} else {
			leftDriveEnc = new Encoder(3, 2);
		}
		rightDriveEnc = new Encoder(4, 5);

		leftDistEncoder = new EncoderWrapperRateAndDistance(
				leftDriveEnc, PIDSourceType.kDisplacement);
		leftRateEncoder = new EncoderWrapperRateAndDistance(
				leftDriveEnc, PIDSourceType.kRate);
		rightDistEncoder = new EncoderWrapperRateAndDistance(
				rightDriveEnc, PIDSourceType.kDisplacement);
		rightRateEncoder = new EncoderWrapperRateAndDistance(
				rightDriveEnc, PIDSourceType.kRate);

		if (!Robot.isSimulation()) {
			ahrs = new AHRS(SPI.Port.kMXP);
			lidar = new LidarWrapper(I2C.Port.kMXP);
		}
		
		
		intakeRollers = new IntakeRollers();
		intakeArm = new IntakeArm();
		driveTrain = new DriveTrain();
		
		if (Robot.isSimulation()) {
			
			leftDriveEnc.setDistancePerPulse(11.56/360.0);
			rightDriveEnc.setDistancePerPulse(11.56/360.0);
			
		} else {
			
			rightDrive.setInverted(false);
			leftDrive.setInverted(true);
			
			leftDriveEnc.setDistancePerPulse(0.01295 * 4);
			rightDriveEnc.setDistancePerPulse(0.01295 * 4);
			
		}
		
		
		rightDrive.setRampMode(true);
		leftDrive.setRampMode(true);

		
		
	}
	
	public static void updateConstants() {
		
		
		rightDrive.setRampRate(ConstantsIO.kDriveVoltageRamp);
		leftDrive.setRampRate(ConstantsIO.kDriveVoltageRamp);
		driveTrain.updateConstants();
		//shooter.updateConstants();
		//intake.updateConstants();
		
	}

	public static IntakeRollers intakeRollers;
	public static IntakeArm intakeArm;
	public static DriveTrain driveTrain;

	public static SpeedController intakeRollerLateralsc;
	public static SpeedController intakeRollerIntakesc;
	public static VictorSP intakeArmVictorSP;
	public static VictorSP[] rightDriveVictorSPs;
	public static VictorSP[] leftDriveVictorSPs;

	public static SpeedControllerWrapper intakeArmSC;
	public static SpeedControllerWrapper rightDrive;
	public static SpeedControllerWrapper leftDrive;

	public static InvertedAbsoluteEncoder intakeAbsEncoder;

	public static Encoder leftDriveEnc;
	public static Encoder rightDriveEnc;

	public static EncoderWrapperRateAndDistance leftDistEncoder;
	public static EncoderWrapperRateAndDistance leftRateEncoder;
	public static EncoderWrapperRateAndDistance rightDistEncoder;
	public static EncoderWrapperRateAndDistance rightRateEncoder;

	public static AHRS ahrs;

	public static LidarWrapper lidar;

	public static final float kMoveIntakeManuallyDeadband = 0.3f;
	public static final  shooterEnc = shooterEnc = new Encoder(Constants.kShooterEncoder[0], Constants.kShooterEncoder[1],
			false, EncodingType.k1X);; 
	
}
