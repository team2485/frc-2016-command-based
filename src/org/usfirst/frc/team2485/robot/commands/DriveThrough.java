package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class DriveThrough extends Command{
	private double setpoint;
	private double maxAbsVelocity;
	private double startAngel;
	private double endAngel;
	private double finishedAt;
	
	public DriveThrough(double setpoint, double finishedAt, double maxAbsVelocity, double startAngle, double endAngle){
		requires(RobotMap.driveTrain);
		this.setpoint = setpoint;
		this.finishedAt = finishedAt;
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
		RobotMap.driveTrain.driveToAndRotateTo(setpoint, startAngel, endAngel, maxAbsVelocity);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		double dist = RobotMap.driveTrain.getDist();
		
		System.out.println("Dist: " + dist);
		
		return finishedAt - dist < 2.0;
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
