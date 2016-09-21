package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2485.robot.commands.StopShooter;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class UnjamShooter extends CommandGroup {

	public UnjamShooter() {
		
		addSequential(new SpinUpShooter(-999));
		addSequential(new CommandTimeout(0.5));
		addSequential(new StopShooter());
		
	}
}
