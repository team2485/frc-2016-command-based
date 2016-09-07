package org.usfirst.frc.team2485.robot.commands;


import org.usfirst.frc.team2485.robot.OI;
import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;

import org.usfirst.frc.team2485.util.ThresholdHandler;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmWithControllers extends Command{
	
	public IntakeArmWithControllers(){
		requires(RobotMap.intakeArm);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		double joystickY = OI.joystick.getRawAxis(1);

		if (Math.abs(joystickY) > RobotMap.kMoveIntakeManuallyDeadband) {
			
			if (joystickY > 0) {

				joystickY = ThresholdHandler.deadbandAndScale(joystickY,
						RobotMap.kMoveIntakeManuallyDeadband, 0.05, 0.6);
			} else {
				joystickY = ThresholdHandler.deadbandAndScale(joystickY,
						RobotMap.kMoveIntakeManuallyDeadband, 0.00, 0.2);
			}
			// joystickY = ThresholdHandler.deadbandAndScaleDualRamp(joystickY,
			// Constants.kMoveIntakeManuallyDeadband, 0.05, 0.8, 0.4, 1.0);

			double encoderPos = RobotMap.intakeAbsEncoder.get();

			boolean disableDownwards = false;

			if (IntakeArm.FLOOR_POSITION > 0.1) {
				if (encoderPos < IntakeArm.FLOOR_POSITION
						&& Math.abs(encoderPos - IntakeArm.FLOOR_POSITION) < 0.1) {
					disableDownwards = true;
				}
			} else {
				if (encoderPos < IntakeArm.FLOOR_POSITION
						|| Math.abs((encoderPos - 1) - IntakeArm.FLOOR_POSITION) < 0.1) {
					disableDownwards = true;
				}
			}

			boolean disableUpwards = false;
			if (IntakeArm.FULL_UP_POSITION < 0.9) {
				if (encoderPos > IntakeArm.FULL_UP_POSITION
						&& Math.abs(encoderPos - IntakeArm.FULL_UP_POSITION) < 0.1) {
					disableUpwards = true;
				}
			} else {
				if (encoderPos > IntakeArm.FULL_UP_POSITION
						|| Math.abs((encoderPos + 1) - IntakeArm.FULL_UP_POSITION) < 0.1) {
					disableUpwards = true;
				}
			}

			if (joystickY < 0) {
				if (!disableDownwards) {
					RobotMap.intakeArm.setManual(joystickY);
				} else {
					RobotMap.intakeArm.setManual(0);
				}
			} else {
				if (disableUpwards) {
					RobotMap.intakeArm.setManual(0);
				} else {
					RobotMap.intakeArm.setManual(joystickY);
				}
			}

		} else if (!RobotMap.intakeArm.isPIDEnabled()) {
			System.out.println("Intake arm PID");
			RobotMap.intakeArm.setSetpoint(RobotMap.intakeArm.getCurrPos());
		}
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
		
	}
	
	

}
