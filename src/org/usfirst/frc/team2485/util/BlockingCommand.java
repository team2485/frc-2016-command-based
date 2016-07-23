package org.usfirst.frc.team2485.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class BlockingCommand extends Command {
	
	private Command innerCommand;
	
	public BlockingCommand(Command innerCommand) {
		this.innerCommand = innerCommand;
		
		Scheduler.getInstance().add(innerCommand);
		
		while (!isFinished()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
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
		throw new YouReallyShouldntDoThatException("A Blocking Command was interupted!");
	}

	@SuppressWarnings("serial")
	private class YouReallyShouldntDoThatException extends RuntimeException {

		private YouReallyShouldntDoThatException(String message) {
			super(message);
		}
	}
}