package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * @author Sahana Kumar
 *
 */

public class SetBoulderStager extends Command {
	StagerPosition value;
	public SetBoulderStager(StagerPosition setValue) {
		requires(RobotMap.boulderStager);
		
		value = setValue;
	}
	@Override
	protected void end() {

	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected void initialize() {
		RobotMap.boulderStager.setPosition(value);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
