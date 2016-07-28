package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class SetHighSpeed extends Command{

private boolean highSpeed;
	
	public SetHighSpeed(boolean highSpeed){
		requires(RobotMap.driveTrain);
		this.highSpeed = highSpeed;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		if(highSpeed){
			RobotMap.driveTrain.setHighSpeed();
		} else {
			RobotMap.driveTrain.setNormalSpeed();
		}
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}


}
