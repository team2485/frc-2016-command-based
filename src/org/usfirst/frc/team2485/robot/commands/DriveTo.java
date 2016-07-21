package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTo extends Command{
	
	private double inches;
	private double maxAbsVelocity;
	
	private boolean finished;
	private double startAngel;
	private double endAngel;
	
	public DriveTo(double inches, double maxAbsVelocity, double startAngle, double endAngle){
		requires(RobotMap.driveTrain);
		this.inches = inches;
		this.maxAbsVelocity = maxAbsVelocity;
		startAngel = startAngle;
		endAngel = endAngle;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		finished = RobotMap.driveTrain.driveToAndRotateTo(inches, startAngel, endAngel, maxAbsVelocity);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return finished;
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
