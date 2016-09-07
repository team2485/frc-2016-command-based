package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootHighGoal extends CommandGroup {
	public ShootHighGoal(){
		requires(RobotMap.shooter);
		addSequential(new SetBoulderStager(StagerPosition.SHOOTING));
		addSequential(new CommandTimeout(1));
		addSequential(new SetBoulderStager(StagerPosition.NEUTRAL));
	}

}
