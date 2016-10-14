package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.AutoAim;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;
import org.usfirst.frc.team2485.util.BlockingCommand;

public class AutoAimAndPrep extends BlockingCommand {

	@Override
	public void runBlockingCommand() {
		if (RobotMap.shooter.getHoodPosition() == HoodPosition.STOWED) {
			addBlockingCommand(new SetHoodPosition(HoodPosition.HIGH_ANGLE));
		}
		addBlockingCommand(new AutoAim());
	}
}
