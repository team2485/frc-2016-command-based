package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

public class SetHoodPosition extends Command {
	private HoodPosition desiredHoodPosition;
	private CommandGroup commandGroup;
	
	
	public SetHoodPosition(HoodPosition hoodPosition) {
		
		desiredHoodPosition = hoodPosition;
		
	}
	
	 @Override
	protected void initialize() {
		 
		 commandGroup = new SetHoodPositionCommandGroup(desiredHoodPosition);
		 Scheduler.getInstance().add(commandGroup);
		 
	}

	@Override
	protected void execute() { }

	@Override
	protected boolean isFinished() {
		return !commandGroup.isRunning();
	}

	@Override
	protected void end() {
		RobotMap.shooter.setHoodPosition(desiredHoodPosition);		
	}

	@Override
	protected void interrupted() { }

}
