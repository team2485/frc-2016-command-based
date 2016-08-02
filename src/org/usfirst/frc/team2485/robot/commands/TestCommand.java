package org.usfirst.frc.team2485.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class TestCommand extends Command {

	@Override
	protected void initialize() {
		System.out.println("Init command");
		
	}

	@Override
	protected void execute() {
		System.out.println("Command running!");
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		System.out.println("End");
	}

	@Override
	protected void interrupted() {
		System.out.println("Interupt");
		end();
	}

}
