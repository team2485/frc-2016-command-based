package org.usfirst.frc.team2485.util;

import java.util.Arrays;




import edu.wpi.first.wpilibj.SpeedController;

/**
 * 
 * Used to act on multiple speed controllers at once, or to treat many speed
 * controllers as one. Also has the ability to monitor current and ramp voltage.
 * 
 * @author Ben Clark
 * @author Patrick Wamsley
 * @author Anoushka Bose
 * @author Jeremy McCulloch
 * @author Nicholas Contreras
 */
public class SpeedControllerWrapper implements SpeedController {

	private SpeedController[] speedControllerList;
	private double[] scaleFactors;
	private boolean rampMode = false;
	private double rampRate;
	private boolean currentMonitoring = false;
	
	private double lastPWM;

	public SpeedControllerWrapper(SpeedController[] speedControllerList, double[] scaleFactors) {

		

		this.speedControllerList = speedControllerList;
	
		
		setScaleFactors(scaleFactors);


	}

	public SpeedControllerWrapper(SpeedController[] speedControllerList) {
		this(speedControllerList, null);
	}

	public SpeedControllerWrapper(SpeedController speedController) {
		this(new SpeedController[] {speedController});
	}
	
	/**
	 * Set scale factors for each motor, controlling how much each motor contributes to the overall effort <br>
	 * Values given are relative and immediately normalized
	 * 
	 * @param scaleFactors An array of non-negative values, whose proportion to each other determine the work each motor does
	 */

	public void setScaleFactors(double[] scaleFactors) {
		
		if (scaleFactors == null) {
			scaleFactors = new double[speedControllerList.length];
			Arrays.fill(scaleFactors, 1.0);
		}

		if (speedControllerList.length != scaleFactors.length) {
			System.err.println("The array of speed controllers and scaleFactors must be the same length");
		}

		double maxValue = -1;

		for (double curValue : scaleFactors) {

			if (curValue < 0) {
				System.err.println(
						"A Speed Controllers scale factor cannot be negative\nTo invert a speed controller, use it's \"setInverted\" method");
			} else {
				maxValue = Math.max(maxValue, curValue);
			}
		}

		for (int i = 0; i < scaleFactors.length; i++) {

			scaleFactors[i] /= maxValue;

		}
		this.scaleFactors = scaleFactors;
	}

	@Override
	public void pidWrite(double output) {
//		System.out.println("SpeedControllerWrapper " + pdpSlotsList[0] + ": output to pidWrite " + output );
		set(output);
	}

	@Override
	public double get() {

		double sum = 0;

		for (SpeedController s : speedControllerList)
			sum += s.get();

		return sum / speedControllerList.length;
	}

	@Override
	public void set(double speed, byte syncGroup) {
		
		for (int i = 0; i < speedControllerList.length; i++) {
			speedControllerList[i].set(speed * scaleFactors[i], syncGroup);
		}
	}

	@Override
	public void set(double speed) {
		
//		System.out.println("SpeedControllerWrapper " + pdpSlotsList[0] + ": Speed after rampAndMonitorCurrent: " + speed);
		for (int i = 0; i < speedControllerList.length; i++) {
			speedControllerList[i].set(speed * scaleFactors[i]);
		}
	}

	/**
	 * ignores all ramping and may stop very quickly, don't use lightly
	 */
	public void emergencyStop() {
		for (SpeedController s : speedControllerList)
			s.set(0);
		lastPWM = 0;
	}

	public void setInverted(boolean isInverted) {
		for (SpeedController s : speedControllerList)
			s.setInverted(isInverted);
	}

	@Override
	public boolean getInverted() {
		return speedControllerList[0].getInverted();
	}

	

	// public boolean isMoving() {
	// return speedControllerList[0].get() > .05;
	// }

	@Override
	public void disable() {
		for (SpeedController controller : speedControllerList) {
			controller.disable();
		}
	}

	public void setRampMode(boolean rampMode) {
		this.rampMode = rampMode;
	}

	public void setRampRate(double rampRate) {
		this.rampRate = rampRate;
		this.rampMode = true;
	}

	public boolean isRampMode() {
		return rampMode;
	}

	public double getRampRate() {
		return rampRate;
	}

	public void setCurrentMonitoring(boolean currentMonitoring) {
		this.currentMonitoring = currentMonitoring;
	}

	
	public boolean isCurrentMonitoring() {
		return currentMonitoring;
	}

	


	@Override
	public void stopMotor() {
		for (SpeedController sc : speedControllerList) {
			sc.stopMotor();
		}
	}
}
