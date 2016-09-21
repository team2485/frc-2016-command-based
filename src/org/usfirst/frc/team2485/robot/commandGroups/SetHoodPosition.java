package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.SetLowerHood;
import org.usfirst.frc.team2485.robot.commands.SetUpperHood;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;
import org.usfirst.frc.team2485.util.CommandTimeout;

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
	
	private class SetHoodPositionCommandGroup extends CommandGroup {
		private SetHoodPositionCommandGroup(HoodPosition desiredHoodPosition) {

			setInterruptible(false);

			HoodPosition currHoodPosition = RobotMap.shooter.getHoodPosition();

			if (desiredHoodPosition == HoodPosition.LOW_ANGLE) {
				if (currHoodPosition == HoodPosition.HIGH_ANGLE) {
					addSequential(new SetUpperHood(true));
				} else if (currHoodPosition == HoodPosition.STOWED) {
					addSequential(new SetLowerHood(false));
					addSequential(new CommandTimeout(0.25));
					addSequential(new SetUpperHood(true));
				}
			} else if (desiredHoodPosition == HoodPosition.HIGH_ANGLE) {
				if (currHoodPosition == HoodPosition.LOW_ANGLE) {
					addSequential(new SetUpperHood(false));

				} else if (currHoodPosition == HoodPosition.STOWED) {
					addSequential(new SetLowerHood(false));
				}
			} else { // setting to stowed

				if (currHoodPosition == HoodPosition.LOW_ANGLE) {

					addSequential(new SetUpperHood(false));
					addSequential(new CommandTimeout(0.25));
					addSequential(new SetLowerHood(true));

				} else if (currHoodPosition == HoodPosition.HIGH_ANGLE) {
					addSequential(new SetLowerHood(true));
				}
			}		
		}
	}

}
