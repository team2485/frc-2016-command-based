package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.commands.IntakeArmSetManual;
import org.usfirst.frc.team2485.robot.commands.RollersOn;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootLowGoal extends CommandGroup {
	public ShootLowGoal(){
		addSequential(new SetBoulderStager(StagerPosition.INTAKE));
		addSequential(new RollersOn(0, -1));
		addSequential(new IntakeArmSetManual(.4));
		addSequential(new CommandTimeout(1));
	}
}
