package org.usfirst.frc.team2485.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class CommandTimout extends Command {

	private Command innerCommand;

	public CommandTimout(Command innerCommand, double timeout) {
		this.innerCommand = innerCommand;
		this.setTimeout(timeout);

	}

	@Override
	protected void initialize() {
		System.out.println("Started timedCommand");
		Scheduler.getInstance().add(innerCommand);
	}

	@Override
	protected void execute() {
		if (isTimedOut()) {
			System.out.println("Timed out");
			innerCommand.cancel();
		}
	}

	@Override
	protected boolean isFinished() {
		return innerCommand.isCanceled();
	}

	@Override
	protected void end() {
		innerCommand.cancel();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
