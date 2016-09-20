package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.SetLowerHood;
import org.usfirst.frc.team2485.robot.commands.SetUpperHood;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetHoodPositionCommandGroup extends CommandGroup {
	public SetHoodPositionCommandGroup(HoodPosition desiredHoodPosition) {
		
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
