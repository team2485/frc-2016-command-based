package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.commands.IntakeArmSetSetpoint;
import org.usfirst.frc.team2485.robot.commands.RollersOn;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeBall extends CommandGroup {
	
	public IntakeBall() {
		addParallel(new IntakeArmSetSetpoint(IntakeArm.INTAKE_POSITION));
		addParallel(new SetBoulderStager(StagerPosition.INTAKE));
		addSequential(new RollersOn(ConstantsIO.kLateralRollerSpeed, ConstantsIO.kIntakeRollerSpeed));
	}

}
