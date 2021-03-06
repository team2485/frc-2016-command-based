package org.usfirst.frc.team2485.robot.subsystems;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.util.ConstantsIO;
import org.usfirst.frc.team2485.util.SpeedControllerWrapper;
import org.usfirst.frc.team2485.util.WarlordsPIDController;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Shooter extends Subsystem {
	/**
	 * Low Angle = Long Shot <br>
	 * High Angle = Batter Shot <br>
	 * Stowed = Don't Shoot <br>
	 */
	public static enum HoodPosition {
		LOW_ANGLE, HIGH_ANGLE, STOWED
	};

	//Adjusted by ConstantsIO
	public static double RPS_LONG_SHOT = 95, 
			RPS_BATTER_SHOT = 80;

	private static final HoodPosition DEFAULT_HOOD_POSITION = HoodPosition.HIGH_ANGLE;
	private SpeedControllerWrapper shooterMotors;
	private HoodPosition currHoodPosition;
	public WarlordsPIDController ratePID;


	public Shooter() {

		shooterMotors = new SpeedControllerWrapper(new SpeedController[] { RobotMap.shooterMotor });

		ratePID = new WarlordsPIDController(ConstantsIO.kP_Shooter, ConstantsIO.kI_Shooter, ConstantsIO.kD_Shooter,
				ConstantsIO.kF_Shooter, RobotMap.shooterRateEncoder, shooterMotors);
		ratePID.setBufferLength(3);
		ratePID.setOutputRange(0, 1);

		currHoodPosition = DEFAULT_HOOD_POSITION;

		RPS_BATTER_SHOT = ConstantsIO.kBatterShotRPS;
		RPS_LONG_SHOT = ConstantsIO.kLongShotRPS;

		SmartDashboard.putNumber("Batter Shot RPS", RPS_BATTER_SHOT);
		SmartDashboard.putNumber("Long Shot RPS", RPS_LONG_SHOT);

		disableShooter();

	}
	
	public boolean isPIDEnabled() {
		return (ratePID.isEnabled());
	}
	
	public void setTargetSpeed(double rpm) {
		if (!isPIDEnabled()){
			ratePID.enable();
		}
		ratePID.setSetpoint(rpm);
	}
	
	public void setManual(double pwm) {
		if (isPIDEnabled()) {
			ratePID.disable();
		}
		shooterMotors.set(pwm);
	}
	public void setHoodPosition (HoodPosition desiredHoodPosition) {	
		currHoodPosition = desiredHoodPosition;
	}
	
	public HoodPosition getHoodPosition() {
		return currHoodPosition;
	}
	
	public void disableShooter() {

		if (ratePID.isEnabled()) {
			ratePID.disable();
		}
		shooterMotors.emergencyStop();

	}

	public double getSetpoint() {
		return ratePID.getSetpoint();
	}

	public double getRate() {
		return RobotMap.shooterEnc.getRate();
	}
	
	public double getCurrentPower() {
		return shooterMotors.get();
	}

	public double getError() {

		return getSetpoint() - getRate();

	}

	public double getAvgError() {

		return ratePID.getAvgError();

	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	public void updateConstants() {
		ratePID.setPID(ConstantsIO.kP_Shooter, ConstantsIO.kI_Shooter, ConstantsIO.kD_Shooter,
				ConstantsIO.kF_Shooter);
		
		RPS_BATTER_SHOT = ConstantsIO.kBatterShotRPS;
		RPS_LONG_SHOT = ConstantsIO.kLongShotRPS;
	}
	
	public void reset() {
		disableShooter();
		RobotMap.upperShooterHoodSolenoid.set(false);
		RobotMap.lowerShooterHoodSolenoid.set(false);
		currHoodPosition = DEFAULT_HOOD_POSITION;
	}
}