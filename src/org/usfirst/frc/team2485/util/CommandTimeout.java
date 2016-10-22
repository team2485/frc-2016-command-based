package org.usfirst.frc.team2485.util;

import edu.wpi.first.wpilibj.command.Command;

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
			innerCommand.start();
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
	public boolean isFinished() {
		if (innerCommand == null) {
			return isTimedOut();
		} else {
			if (innerCommand.timeSinceInitialized() > 0.050) {
				return !innerCommand.isRunning();
			} else {
				return false;
			}
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
	
	@Override
	public String toString() {
		return super.toString() + " -> " + innerCommand;
	}
}
