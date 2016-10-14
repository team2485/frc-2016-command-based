package org.usfirst.frc.team2485.util;

import org.usfirst.frc.team2485.robot.BlockingCommandFactory;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public abstract class BlockingCommand extends Command {

	private Thread blockingCommandThread;
	private Command curCommand;
	
	public abstract void runBlockingCommand();
	
	@Override
	protected void initialize() {
		blockingCommandThread = new Thread(new Runnable() {
			@Override
			public void run() {
				runBlockingCommand();
			}
		});
		blockingCommandThread.start();

	}

	@Override
	protected void execute() { }

	@Override
	protected boolean isFinished() {
		return !blockingCommandThread.isAlive();
	}

	@Override
	protected void end() {
		blockingCommandThread.stop();
		if (curCommand != null && curCommand.isCanceled()) {
				curCommand.cancel();
		}
	}

	@Override
	protected void interrupted() {
		end();
	}
	
	public void addBlockingCommand(Command command) {
		
		curCommand = command;
		Scheduler.getInstance().add(command);
		
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (command.isRunning()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
