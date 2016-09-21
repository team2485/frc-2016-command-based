package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.commands.IntakeArmSetSetpoint;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class PrepForLowBar extends CommandGroup {
	
	public PrepForLowBar() {
		addParallel(new IntakeArmSetSetpoint(IntakeArm.LOW_NO_INTAKE_POSITION));
		addSequential(new SetHoodPosition(HoodPosition.STOWED));
	}

}
