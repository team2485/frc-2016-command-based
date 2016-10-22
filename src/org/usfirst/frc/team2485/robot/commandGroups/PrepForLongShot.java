package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.commands.SpinUpShooter;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PrepForLongShot extends CommandGroup {

	public PrepForLongShot(){
		addSequential(new SetHoodPosition(HoodPosition.LOW_ANGLE));
		addSequential(new SpinUpShooter(ConstantsIO.kLongShotRPS));
	}
}

