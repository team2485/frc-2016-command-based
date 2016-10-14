package org.usfirst.frc.team2485.robot.commands;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAim extends Command {

	private static final double FIELD_OF_VIEW = 47.0;
	private static final double ANGLE_TOLERANCE_BATTER = 2;
	private static final double ANGLE_TOLERANCE_LONG = 1;
	

	public static double BATTER_SHOT_PIXEL_TARGET = 165;
	public static double LONG_SHOT_PIXEL_TARGET = 156;
	private boolean finished = false;

	private CommandTimeout turnToCommand;

	@Override
	protected void initialize() {
		System.out.println("AutoAim:Starting");

		BATTER_SHOT_PIXEL_TARGET = SmartDashboard
				.getNumber("Batter Shot Target");
		LONG_SHOT_PIXEL_TARGET = SmartDashboard.getNumber("Long Shot Target");
	}

	@Override
	protected void execute() {

		if (!finished && (turnToCommand == null || turnToCommand.isFinished())) {

			NetworkTable table = NetworkTable.getTable("targetCenter");
			double targetX = table.getNumber("x", 320 / 2);
			double targetY = table.getNumber("y", 240 / 2);

			double alignmentGoal = BATTER_SHOT_PIXEL_TARGET;
			double tolerance = ANGLE_TOLERANCE_BATTER;
			
			if (targetY > 120) {
				alignmentGoal = LONG_SHOT_PIXEL_TARGET;
				tolerance = ANGLE_TOLERANCE_LONG;
			}

			double angle = FIELD_OF_VIEW * (targetX - alignmentGoal) / 320;

			if (Math.abs(angle) < tolerance) {
				finished = true;
			} else {
				turnToCommand = new CommandTimeout(new RotateTo(
						RobotMap.gyro.getYaw() + angle), 1);

				Scheduler.getInstance().add(turnToCommand);
			}
			
		}
	}

	@Override
	protected boolean isFinished() {
		return finished;
	}

	@Override
	protected void end() {
		if (turnToCommand != null && turnToCommand.isRunning()) {
			turnToCommand.cancel();
		}
	}

	@Override
	protected void interrupted() {
		end();
	}
}
