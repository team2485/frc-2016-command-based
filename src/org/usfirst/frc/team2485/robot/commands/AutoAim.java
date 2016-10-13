package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAim extends Command {

	private static final double FIELD_OF_VIEW = 47.0;

	public static double BATTER_SHOT_PIXEL_TARGET = 165;
	public static double LONG_SHOT_PIXEL_TARGET = 156;

	private CommandTimeout turnToCommand;

	@Override
	protected void initialize() {
		System.out.println("Starting auto aim");

		BATTER_SHOT_PIXEL_TARGET = SmartDashboard
				.getNumber("Batter Shot Target");
		LONG_SHOT_PIXEL_TARGET = SmartDashboard.getNumber("Long Shot Target");
	}

	@Override
	protected void execute() {

		if (turnToCommand == null || turnToCommand.isFinished()) {

			NetworkTable table = NetworkTable.getTable("targetCenter");
			double targetX = table.getNumber("x", 320 / 2);
			double targetY = table.getNumber("y", 240 / 2);

			double alignmentGoal = BATTER_SHOT_PIXEL_TARGET;

			if (targetY > 120) {
				alignmentGoal = LONG_SHOT_PIXEL_TARGET;
			}

			double angle = FIELD_OF_VIEW * (targetX - alignmentGoal) / 320;

			turnToCommand = new CommandTimeout(new RotateTo(
					RobotMap.gyro.getYaw() + angle), 1);
			
			Scheduler.getInstance().add(turnToCommand);
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}
}
