package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;


public class SpinUpShooter extends Command {
	private double rpm;
	private long startTime = -1;
	
	public SpinUpShooter(double rpm) {
		this.rpm = rpm;
	}


	
	@Override
	protected void end() { }

	@Override
	protected void execute() { }

	@Override
	protected void initialize() {
		RobotMap.shooter.setTargetSpeed(rpm);
	}

	@Override
	protected void interrupted() { }

	@Override
	protected boolean isFinished() {
		return true;
	}
}
