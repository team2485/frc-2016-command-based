package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.commands.IntakeArmSetManual;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Hat extends CommandGroup {

	public Hat() {
		
		addParallel(new SetBoulderStager(StagerPosition.SHOOTING));
		addParallel(new IntakeArmSetManual(0.2));
		
	}
	
	
}

