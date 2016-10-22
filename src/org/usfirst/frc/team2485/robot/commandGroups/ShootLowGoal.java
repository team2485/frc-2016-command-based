package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.IntakeArmSetSetpoint;
import org.usfirst.frc.team2485.robot.commands.RollersOn;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootLowGoal extends CommandGroup {
	public ShootLowGoal() {
		requires(RobotMap.shooter);
		addParallel(new SetBoulderStager(StagerPosition.INTAKE));
		addParallel(new RollersOn(0, -1));
		addParallel(new IntakeArmSetSetpoint(IntakeArm.PORTCULLIS_POSITION));
		addSequential(new CommandTimeout(2));
		addParallel(new SetBoulderStager(StagerPosition.NEUTRAL));
		addSequential(new RollersOn(0, 0));
		
	}
}
