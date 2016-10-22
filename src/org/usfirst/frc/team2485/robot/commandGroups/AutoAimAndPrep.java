package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.AutoAim;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;

import edu.wpi.first.wpilibj.command.BlockingCommand;

public class AutoAimAndPrep extends BlockingCommand {

	public AutoAimAndPrep() {
		requires(RobotMap.shooter);
		requires(RobotMap.driveTrain);
	}
	
	@Override
	public void runBlockingCommand() {
		if (RobotMap.shooter.getHoodPosition() == HoodPosition.STOWED) {
			addBlockingCommand(new SetHoodPosition(HoodPosition.HIGH_ANGLE));
		}
		addBlockingCommand(new AutoAim());
	}
}
