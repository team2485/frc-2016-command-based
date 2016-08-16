package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;
import org.usfirst.frc.team2485.util.CommandTimout;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * 
 * @author Sahana Kumar
 *
 */


public class ShakeBoulderStager extends CommandGroup {
	public ShakeBoulderStager() {
		addSequential(new CommandTimout(new SetBoulderStager(StagerPosition.INTAKE), .3));
		addSequential(new CommandTimout(new SetBoulderStager(StagerPosition.NEUTRAL), .3));
		addSequential(new CommandTimout(new SetBoulderStager(StagerPosition.NEUTRAL), .3));
	}
}
