package org.usfirst.frc.team2485.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class CommandTimeout extends Command {

	private Command innerCommand;

	public CommandTimeout(double timeout) {
		this(null, timeout);
	}
	
	public CommandTimeout(Command innerCommand, double timeout) {
		this.innerCommand = innerCommand;
		this.setTimeout(timeout);
	}

	@Override
	protected void initialize() {
		System.out.println("Started timedCommand");
		if (innerCommand != null) {
			Scheduler.getInstance().add(innerCommand);
		}
	}

	@Override
	protected void execute() {
		if (isTimedOut() && innerCommand != null) {
			System.out.println("Timed out");
			innerCommand.cancel();
		}
	}

	@Override
	protected boolean isFinished() {
		if (innerCommand == null) {
			return isTimedOut();
		} else {
			return innerCommand.isCanceled();
		}
	}

	@Override
	protected void end() {
		if (innerCommand != null) {
			innerCommand.cancel();
		}
	}

	@Override
	protected void interrupted() {
		end();
	}
}
