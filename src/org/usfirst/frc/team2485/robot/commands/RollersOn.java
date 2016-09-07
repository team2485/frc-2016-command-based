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
		lateralRollersc.set(lateralValue);
		intakeRollersc.set(intakeValue);

	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
