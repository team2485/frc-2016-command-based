package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class SetUpperHood extends Command {
	private boolean extended;
	
	public SetUpperHood(boolean extended) {
		requires(RobotMap.shooter);
		this.extended = extended;
	}
	@Override
	protected void end() { }

	@Override
	protected void execute() { }

	@Override
	protected void initialize() {
		RobotMap.upperShooterHoodSolenoid.set(extended);
	}

	@Override
	protected void interrupted() { }

	@Override
	protected boolean isFinished() {
		return true;
	}

}
