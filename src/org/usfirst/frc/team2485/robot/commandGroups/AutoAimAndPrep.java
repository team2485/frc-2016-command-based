package org.usfirst.frc.team2485.robot.commandGroups;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commands.AutoAim;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;
import org.usfirst.frc.team2485.util.CommandTimeout;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

public class AutoAimAndPrep extends CommandGroup {

	public AutoAimAndPrep() {

		Command prepHood = new CommandTimeout(new Command() {

			@Override
			protected boolean isFinished() {
				return false;
			}

			@Override
			protected void interrupted() {
				end();
			}

			@Override
			protected void initialize() {
				
				if (RobotMap.shooter.getHoodPosition() == HoodPosition.STOWED) {
					Scheduler.getInstance().add(new SetHoodPosition(HoodPosition.HIGH_ANGLE));
				}
			}

			@Override
			protected void execute() {
			}

			@Override
			protected void end() {
			}
		}, 0.5);
		
		addSequential(prepHood);
		addSequential(new AutoAim());
	}
}
