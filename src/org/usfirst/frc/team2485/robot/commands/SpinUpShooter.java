package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.command.Command;


public class SpinUpShooter extends Command {
	private double rpm;
	
	public SpinUpShooter(double rpm) {
		this.rpm = rpm;
	}


	
	@Override
	protected void end() { }

	@Override
	protected void execute() { }

	@Override
	protected void initialize() {
		System.out.println("Shooter P:" + ConstantsIO.kP_Shooter);
		RobotMap.shooter.setTargetSpeed(rpm);
	}

	@Override
	protected void interrupted() { }

	@Override
	protected boolean isFinished() {
		return true;
	}
}
