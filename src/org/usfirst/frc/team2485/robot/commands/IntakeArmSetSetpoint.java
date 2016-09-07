package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmSetSetpoint extends Command {
	
	private double setpoint;

	public IntakeArmSetSetpoint(double setpoint) {
		requires(RobotMap.intakeArm);
		
		this.setpoint = setpoint;
	}
	
	@Override
	protected void initialize() {
		RobotMap.intakeArm.setSetpoint(setpoint);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
