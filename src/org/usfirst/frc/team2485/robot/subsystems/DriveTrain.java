package org.usfirst.frc.team2485.robot.subsystems;

import org.usfirst.frc.team2485.robot.Robot;
import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.DriveWithControllers;
import org.usfirst.frc.team2485.util.ConstantsIO;
import org.usfirst.frc.team2485.util.DummyOutput;
import org.usfirst.frc.team2485.util.MultipleEncoderWrapper;
import org.usfirst.frc.team2485.util.MultipleEncoderWrapper.MultipleEncoderWrapperMode;
import org.usfirst.frc.team2485.util.RampRate;
import org.usfirst.frc.team2485.util.SpeedControllerWrapper;
import org.usfirst.frc.team2485.util.ThresholdHandler;
import org.usfirst.frc.team2485.util.WarlordsPIDController;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Jeremy McCulloch
 */
public class DriveTrain extends Subsystem {

	private final double NORMAL_SPEED_RATING = 0.8, FAST_SPEED_RATING = 1.0,
			SLOW_SPEED_RATING = 0.6; // virtual gears

	private double driveSpeed = NORMAL_SPEED_RATING;

	// AUTONOMOUS
	private DummyOutput dummyRotateToOutput, dummyDriveToEncoderOutput;
	public WarlordsPIDController rotateToPID, driveToPID;

	private WarlordsPIDController leftVelocityPID, rightVelocityPID;

	private RampRate leftVelocityRamp, rightVelocityRamp;

	private MultipleEncoderWrapper minDriveEncoderDist; //used as source for driveToEncoderPID in case one tread slips

	private int ahrsOnTargetCounter = 0;
	private static final int MINIMUM_AHRS_ON_TARGET_ITERATIONS = 4;

	private static final double ABS_TOLERANCE_ROTATETO = 1;
	private static final double ABS_TOLERANCE_DRIVETO_DISTANCE = 5;
	private static final double ABS_TOLERANCE_DRIVETO_ANGLE = 2.0;
	private final static double LOW_ENC_RATE = 5;

	// W.A.R. LORD DRIVE
	private double oldSteering = 0.0;
	private double quickStopAccumulator = 0.0;
	private static final double THROTTLE_DEADBAND = 0.1;
	private static final double STEERING_DEADBAND = 0.1;

	private static final double SENSITIVITY_HIGH = 0.85;
	private static final double SENSITIVITY_LOW = 0.75;
	private boolean isQuickTurn = false;

	public DriveTrain() {

		rightVelocityRamp = new RampRate(ConstantsIO.kDriveVelocityUpRamp, ConstantsIO.kDriveVelocityDownRamp);
		leftVelocityRamp = new RampRate(ConstantsIO.kDriveVelocityUpRamp, ConstantsIO.kDriveVelocityDownRamp);

		minDriveEncoderDist = new MultipleEncoderWrapper(PIDSourceType.kDisplacement, 
				MultipleEncoderWrapperMode.MIN, RobotMap.leftDriveEnc,
				RobotMap.rightDriveEnc);

		dummyRotateToOutput = new DummyOutput();

		rotateToPID = new WarlordsPIDController(
				ConstantsIO.kP_Rotate,
				ConstantsIO.kI_Rotate,
				ConstantsIO.kD_Rotate, RobotMap.gyro,
				dummyRotateToOutput); 

		rotateToPID.setAbsoluteTolerance(ABS_TOLERANCE_ROTATETO);
		rotateToPID.setOutputRange(-10, 10);
		rotateToPID.setInputRange(-180, 180);
		rotateToPID.setContinuous(true);

		dummyDriveToEncoderOutput = new DummyOutput();
		driveToPID = new WarlordsPIDController(ConstantsIO.kP_DriveTo,
				ConstantsIO.kI_DriveTo, ConstantsIO.kD_DriveTo, minDriveEncoderDist,
				dummyDriveToEncoderOutput);
		driveToPID.setAbsoluteTolerance(ABS_TOLERANCE_DRIVETO_DISTANCE);

		rightVelocityPID = new WarlordsPIDController(ConstantsIO.kP_DriveVelocity, ConstantsIO.kI_DriveVelocity, 
				ConstantsIO.kD_DriveVelocity, ConstantsIO.kF_DriveVelocity, 
				RobotMap.rightRateEncoder, RobotMap.rightDrive);
		leftVelocityPID = new WarlordsPIDController(ConstantsIO.kP_DriveVelocity, ConstantsIO.kI_DriveVelocity, 
				ConstantsIO.kD_DriveVelocity, ConstantsIO.kF_DriveVelocity, 
				RobotMap.leftRateEncoder, RobotMap.leftDrive);

		RobotMap.leftDriveEnc.reset();
		RobotMap.rightDriveEnc.reset();

		rightVelocityPID.setOutputRange(-.3, .3);
		leftVelocityPID.setOutputRange(-.3, .3);

	}

	public void updateConstants() {

		driveToPID.setPID(ConstantsIO.kP_DriveTo,
				ConstantsIO.kI_DriveTo, ConstantsIO.kD_DriveTo, 0);
		rotateToPID.setPID(ConstantsIO.kP_Rotate,
				ConstantsIO.kI_Rotate, ConstantsIO.kD_Rotate, 0);
	}

	/**
	 * Resets DriveTrain but does not zero encoders
	 */
	public void reset() {
		driveToPID.disable();
		rotateToPID.disable();
		emergencyStop();
	}

	/**
	 * W.A.R. Lord Drive This drive method is based off of Team 254's Ultimate
	 * Ascent cheesyDrive code.
	 *
	 * @param controllerY
	 *            controllerY should be positive for forward motion
	 * @param controllerX
	 */
	public void warlordDrive(double controllerY, double controllerX) {

		boolean isHighGear = isQuickTurn;

		double steeringNonLinearity;

		double steering = ThresholdHandler.deadbandAndScale(controllerX,
				STEERING_DEADBAND, 0.01, 1);
		double throttle = ThresholdHandler.deadbandAndScale(controllerY,
				THROTTLE_DEADBAND, 0.01, 1);

		double negInertia = steering - oldSteering;
		oldSteering = steering;

		if (isHighGear) {
			steeringNonLinearity = 0.6;
			// Apply a sin function that's scaled to make it feel better.
			steering = Math
					.sin(Math.PI / 2.0 * steeringNonLinearity * steering)
					/ Math.sin(Math.PI / 2.0 * steeringNonLinearity);
			steering = Math
					.sin(Math.PI / 2.0 * steeringNonLinearity * steering)
					/ Math.sin(Math.PI / 2.0 * steeringNonLinearity);
		} else {
			steeringNonLinearity = 0.5;
			// Apply a sin function that's scaled to make it feel better.
			steering = Math
					.sin(Math.PI / 2.0 * steeringNonLinearity * steering)
					/ Math.sin(Math.PI / 2.0 * steeringNonLinearity);
			steering = Math
					.sin(Math.PI / 2.0 * steeringNonLinearity * steering)
					/ Math.sin(Math.PI / 2.0 * steeringNonLinearity);
			steering = Math
					.sin(Math.PI / 2.0 * steeringNonLinearity * steering)
					/ Math.sin(Math.PI / 2.0 * steeringNonLinearity);
		}

		double leftPwm, rightPwm, overPower;
		double sensitivity = 1.7;

		double angularPower;
		double linearPower;

		// Negative inertia!
		double negInertiaAccumulator = 0.0;
		double negInertiaScalar;
		if (isHighGear) {
			negInertiaScalar = 5.0;
			sensitivity = SENSITIVITY_HIGH;
		} else {
			if (steering * negInertia > 0) {
				negInertiaScalar = 2.5;
			} else {
				if (Math.abs(steering) > 0.65) {
					negInertiaScalar = 5.0;
				} else {
					negInertiaScalar = 3.0;
				}
			}
			sensitivity = SENSITIVITY_LOW;
		}
		double negInertiaPower = negInertia * negInertiaScalar;
		negInertiaAccumulator += negInertiaPower;

		steering = steering + negInertiaAccumulator;
		linearPower = throttle;

		// Quickturn!
		if (isQuickTurn) {
			if (Math.abs(linearPower) < 0.2) {
				double alpha = 0.1;
				steering = steering > 1 ? 1.0 : steering;
				quickStopAccumulator = (1 - alpha) * quickStopAccumulator
						+ alpha * steering * 0.5;
			}
			overPower = 1.0;
			if (isHighGear) {
				sensitivity = 1.0;
			} else {
				sensitivity = 1.0;
			}
			angularPower = steering;
		} else {
			overPower = 0.0;
			angularPower = throttle * steering * sensitivity
					- quickStopAccumulator;
			if (quickStopAccumulator > 1) {
				quickStopAccumulator -= 1;
			} else if (quickStopAccumulator < -1) {
				quickStopAccumulator += 1;
			} else {
				quickStopAccumulator = 0.0;
			}
		}

		rightPwm = leftPwm = linearPower;

		leftPwm += angularPower;
		rightPwm -= angularPower;

		if (leftPwm > 1.0) {
			rightPwm -= overPower * (leftPwm - 1.0);
			leftPwm = 1.0;
		} else if (rightPwm > 1.0) {
			leftPwm -= overPower * (rightPwm - 1.0);
			rightPwm = 1.0;
		} else if (leftPwm < -1.0) {
			rightPwm += overPower * (-1.0 - leftPwm);
			leftPwm = -1.0;
		} else if (rightPwm < -1.0) {
			leftPwm += overPower * (-1.0 - rightPwm);
			rightPwm = -1.0;
		}

		setLeftRight(leftPwm, rightPwm);
	}

	/**
	 * Sets the drive to quick turn mode
	 * 
	 * @param isQuickTurn
	 */
	public void setQuickTurn(boolean isQuickTurn) {
		this.isQuickTurn = isQuickTurn;
	}

	/**
	 * Sets target velocity of each tread in inches / sec
	 * @param leftOutput left target velocity
	 * @param rightOutput right target velocity
	 */
	public void setLeftRightVelocity(double leftOutput, double rightOutput) {

		System.out.println("L" + leftOutput + "R" + rightOutput);
		leftVelocityPID.setPID(ConstantsIO.kP_DriveVelocity, ConstantsIO.kI_DriveVelocity, 
				ConstantsIO.kD_DriveVelocity, ConstantsIO.kF_DriveVelocity);
		rightVelocityPID.setPID(ConstantsIO.kP_DriveVelocity, ConstantsIO.kI_DriveVelocity, 
				ConstantsIO.kD_DriveVelocity, ConstantsIO.kF_DriveVelocity);

		leftVelocityPID.enable();
		rightVelocityPID.enable();
		leftVelocityPID.setSetpoint(leftOutput);
		rightVelocityPID.setSetpoint(rightOutput);
	}

	/**
	 * Sends outputs values to the left and right side of the drive base after 
	 * scaling based on virtual gear.  
	 * <br>
	 * The parameters should both be positive to move forward. One side has
	 * inverted motors...do not send a negative to one side and a positive to
	 * the other for forward or backwards motion.
	 *
	 * @param leftOutput
	 * @param rightOutput
	 */
	public void setLeftRight(double leftOutput, double rightOutput) {

		leftVelocityPID.disable();
		rightVelocityPID.disable();

		leftOutput *= driveSpeed;
		rightOutput *= driveSpeed;

		RobotMap.leftDrive.set(leftOutput);
		RobotMap.rightDrive.set(rightOutput);
	}

	/**
	 * Stops both drive motors, overwriting voltage ramping
	 */
	public void emergencyStop() {

		leftVelocityPID.disable();
		rightVelocityPID.disable();

		RobotMap.leftDrive.emergencyStop();
		RobotMap.rightDrive.emergencyStop();
	}

	/**
	 * Switch into high speed mode
	 */
	public void setHighSpeed() {
		driveSpeed = FAST_SPEED_RATING;
	}

	/**
	 * Switch into low speed mode
	 */
	public void setLowSpeed() {
		driveSpeed = SLOW_SPEED_RATING;
	}

	/**
	 * Switch to normal speed mode
	 */
	public void setNormalSpeed() {
		driveSpeed = NORMAL_SPEED_RATING;
	}

	public void resetEncoders() {
		RobotMap.leftDriveEnc.reset();
		RobotMap.rightDriveEnc.reset();
	}

	/**
	 * Used to drive in a curve using closed loop control, set startAngle = endAngle to drive straight <br>
	 * Uses cascaded PIDControllers to achieve optimal performance
	 * @param inches distance to drive (inside tread if curving)
	 * @param startAngle heading at beginning of turn
	 * @param endAngle desired heading at end of turn
	 * @param maxSpeed maximum speed of center of robot in inches / second
	 * @return true if target has been reached
	 */
	public boolean driveToAndRotateTo(double inches, double startAngle, double endAngle, double maxSpeed) {

		System.out.println("DriveTrain:we got to here");
		if (!driveToPID.isEnabled()) {
			driveToPID.enable();
			rotateToPID.enable();
			rotateToPID.setSetpoint(startAngle);
		}
		driveToPID.setSetpoint(inches);

		driveToPID.setOutputRange(-maxSpeed, maxSpeed);
		rotateToPID.setOutputRange(-maxSpeed, maxSpeed);

		//uses % of distance to calculate where to turn to
		double percentDone = (RobotMap.leftDriveEnc.getDistance() + 
				RobotMap.rightDriveEnc.getDistance()) / 2 / (inches+0.00000001);//don't divide by 0
		if (percentDone > 1) {
			percentDone = 1;
		} else if (percentDone < 0) {
			percentDone = 0;
		}
		rotateToPID.setSetpoint(startAngle + (endAngle - startAngle) * percentDone); 

		double encoderOutput = dummyDriveToEncoderOutput.get();
		double rotateToOutput = dummyRotateToOutput.get();

		// use output from PIDControllers to calculate target velocities
		double leftVelocity = encoderOutput + rotateToOutput; 
		double rightVelocity = encoderOutput - rotateToOutput;

		// ramp output from PIDControllers to prevent saturating velocity control loop
		leftVelocity = leftVelocityRamp.getNextValue(leftVelocity);
		rightVelocity = rightVelocityRamp.getNextValue(rightVelocity);

		setLeftRightVelocity(leftVelocity, rightVelocity);

		if (Math.abs(rotateToPID.getError()) < ABS_TOLERANCE_DRIVETO_ANGLE) {
			ahrsOnTargetCounter++;
		} else {	
			ahrsOnTargetCounter = 0;
		}
		
		double avgVelocity = (RobotMap.leftDriveEnc.getRate() + RobotMap.rightDriveEnc.getRate()) / 2;

		if (Math.abs(driveToPID.getError()) < ABS_TOLERANCE_DRIVETO_DISTANCE 
				&& Math.abs(avgVelocity) < LOW_ENC_RATE &&
				ahrsOnTargetCounter >= MINIMUM_AHRS_ON_TARGET_ITERATIONS) {

			setLeftRightVelocity(0.0, 0.0); // actively stops driveTrain
			driveToPID.disable();
			rotateToPID.disable();
			return true;

		}

		return false;

	}

	public boolean rotateTo(double angle) {
		return rotateTo(angle, ABS_TOLERANCE_ROTATETO);
	}
	/**
	 * Used to rotate in place using closed loop control
	 * Uses cascaded PIDControllers to achieve optimal performance
	 * @param angle target heading as measured by ahrs
	 * @param tolerance accuracy that must be achieved before loop finished
	 * @return true if on target
	 */
	public boolean rotateTo(double angle, double tolerance) {
		if (rotateToPID == null)
			throw new IllegalStateException("can't rotateTo when ahrs is null");

		double SMALL_LARGE_THRESHOLD = 5;

		if (!rotateToPID.isEnabled()) {
			rotateToPID.enable();			
		}

		rotateToPID.setOutputRange(-15, 15);
		rotateToPID.setSetpoint(angle);

		double ahrsOutput = dummyRotateToOutput.get();

		if (Math.abs(rotateToPID.getError()) < tolerance) {
			ahrsOnTargetCounter++;
		} else {
			ahrsOnTargetCounter = 0;
		}


		if (ahrsOnTargetCounter >= MINIMUM_AHRS_ON_TARGET_ITERATIONS) {
			setLeftRightVelocity(0, 0);
			rotateToPID.disable();
			return true;
		}

		double leftVelocity = ahrsOutput;
		//noticed better results by only driving one belt instead of both for small angles, decreased friction
		double rightVelocity = Math.abs(rotateToPID.getError()) > SMALL_LARGE_THRESHOLD ? -ahrsOutput : 0;

		leftVelocity = leftVelocityRamp.getNextValue(leftVelocity);
		rightVelocity = rightVelocityRamp.getNextValue(rightVelocity);

		setLeftRightVelocity(leftVelocity, rightVelocity);

		return false;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithControllers());

	}

	public double getDist(){
		return minDriveEncoderDist.pidGet();
	}


}
