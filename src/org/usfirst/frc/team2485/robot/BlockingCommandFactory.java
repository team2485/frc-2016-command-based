package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.robot.commands.DriveTo;
import org.usfirst.frc.team2485.robot.commands.TestCommand;
import org.usfirst.frc.team2485.util.BlockingCommand;
import org.usfirst.frc.team2485.util.CommandTimout;

import edu.wpi.first.wpilibj.command.Scheduler;

public class BlockingCommandFactory {

	public static void advanceTo100InchReallyBadlyPlzNeverUseThis() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (RobotMap.lidar.getDistance() > 100) {

					System.out.println("Confirm enter: " + RobotMap.lidar.getDistance());

					Scheduler.getInstance().add(new CommandTimout(new BlockingCommand(new TestCommand()), 2));

					System.out.println("Exit");
				}
			}
		}).start();
	}
}

