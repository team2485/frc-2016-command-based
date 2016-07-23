package org.usfirst.frc.team2485.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class CommandTimout extends Command {

	private Command innerCommand;

	public CommandTimout(Command innerCommand, double timeout) {
		this.innerCommand = innerCommand;
		this.setTimeout(timeout);

		Scheduler.getInstance().add(innerCommand);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (isTimedOut()) {
			innerCommand.cancel();
		}
	}

	@Override
	protected boolean isFinished() {
		return innerCommand.isCanceled();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		throw new YouReallyShouldntDoThatException("A Command Timeout was interupted!");
	}

	@SuppressWarnings("serial")
	private class YouReallyShouldntDoThatException extends RuntimeException {

		private YouReallyShouldntDoThatException(String message) {
			super(message);
		}
	}
}
