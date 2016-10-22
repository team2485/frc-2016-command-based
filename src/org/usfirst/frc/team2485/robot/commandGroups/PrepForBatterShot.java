package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2485.robot.subsystems.Shooter;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PrepForBatterShot extends CommandGroup {

	public PrepForBatterShot(){
		addSequential(new SetHoodPosition(HoodPosition.HIGH_ANGLE));
		addSequential(new SpinUpShooter(Shooter.RPS_BATTER_SHOT));
	}
}

