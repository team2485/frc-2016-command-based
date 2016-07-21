package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeArmSetManual extends Command{
	
	double pwm;
	
	public IntakeArmSetManual(double PWM){
		
		requires(RobotMap.intakeArm);
		
		pwm = PWM;
		
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		RobotMap.intakeArm.setManual(pwm);
		
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
		end();
		
	}
	
	

}
