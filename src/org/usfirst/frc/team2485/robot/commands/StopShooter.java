package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.command.Command;


public class StopShooter extends Command {
	
	public StopShooter() {
		requires(RobotMap.shooter);
	}


	
	@Override
	protected void end() { }

	@Override
	protected void execute() { }

	@Override
	protected void initialize() {
		RobotMap.shooter.disableShooter();
	}

	@Override
	protected void interrupted() { }

	@Override
	protected boolean isFinished() {
		return true;
	}
}
