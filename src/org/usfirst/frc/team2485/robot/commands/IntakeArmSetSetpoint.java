package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmSetSetpoint extends Command{
	
	private double setpoint;
	
	public IntakeArmSetSetpoint(double setpt){
		
		requires(RobotMap.intakeArm);
		
		setpoint = setpt;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		RobotMap.intakeArm.setSetpoint(setpoint);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		RobotMap.intakeArm.setSetpoint(RobotMap.intakeArm.getCurrPos());
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
