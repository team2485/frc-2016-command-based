package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class SetLowSpeed extends Command{
	
	private boolean lowSpeed;
	
	public SetLowSpeed(boolean lowSpeed){
		requires(RobotMap.driveTrain);
		this.lowSpeed = lowSpeed;
	}

	protected void initialize() {
		// TODO Auto-generated method stub
		if(lowSpeed){
			RobotMap.driveTrain.setLowSpeed();
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
