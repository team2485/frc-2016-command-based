package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * @author Sahana Kumar
 */
public class ShakeBoulderStager extends CommandGroup {
	public ShakeBoulderStager() {
		addSequential(new SetBoulderStager(StagerPosition.SHOOTING));
		addSequential(new CommandTimeout(0.3));
		addSequential(new SetBoulderStager(StagerPosition.INTAKE));
		addSequential(new CommandTimeout(0.3));
		addSequential(new SetBoulderStager(StagerPosition.NEUTRAL));
	}
}
