package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;

public class RollersOn extends Command {

	private double lateralValue, intakeValue;
	private SpeedController intakeRollersc, lateralRollersc;

	public RollersOn(double lateralsc, double intakesc) {

		requires(RobotMap.intakeRollers);
		this.lateralRollersc = RobotMap.intakeRollerLateralsc;
		this.intakeRollersc = RobotMap.intakeRollerIntakesc;

		lateralValue = lateralsc;
		intakeValue = intakesc;

	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		lateralRollersc.set(lateralValue);
		intakeRollersc.set(intakeValue);

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		lateralRollersc.set(0);
		intakeRollersc.set(0);
	}

	@Override
	protected void interrupted() {
		end();
	
	}

	
	
}
