package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.command.Command;

public class SetHoodPosition extends Command {
	private Shooter.HoodPosition desiredHoodPosition;
	
	public SetHoodPosition(Shooter.HoodPosition desiredHoodPosition) {
		this.desiredHoodPosition = desiredHoodPosition;
	}
	@Override
	protected void end() { }

	@Override
	protected void execute() { }

	@Override
	protected void initialize() {
		RobotMap.shooter.setHoodPosition(desiredHoodPosition);
	}

	@Override
	protected void interrupted() { }

	@Override
	protected boolean isFinished() {
		return true;
	}

}
