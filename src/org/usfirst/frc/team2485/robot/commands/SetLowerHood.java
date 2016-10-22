package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class SetLowerHood extends Command {
	private boolean extended;
	
	public SetLowerHood(boolean extended) {
		requires(RobotMap.shooter);
		this.extended = extended;
	}
	@Override
	protected void end() { }

	@Override
	protected void execute() { }

	@Override
	protected void initialize() {
		RobotMap.lowerShooterHoodSolenoid.set(extended);
	}

	@Override
	protected void interrupted() { }

	@Override
	protected boolean isFinished() {
		return true;
	}

}
