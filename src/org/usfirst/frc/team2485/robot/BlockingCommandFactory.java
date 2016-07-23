package org.usfirst.frc.team2485.robot;

import org.usfirst.frc.team2485.robot.commands.DriveTo;
import org.usfirst.frc.team2485.util.BlockingCommand;
import org.usfirst.frc.team2485.util.CommandTimout;

import edu.wpi.first.wpilibj.command.Scheduler;

public class BlockingCommandFactory {
	
	public static void advanceTo30InchReallyBadlyPlzNeverUseThis() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (RobotMap.lidar.getDistance() > 36) {
					
					System.out.println("Advancing!!!");
					
					Scheduler.getInstance().add(new BlockingCommand(new CommandTimout(new DriveTo(12, 0.5, 0, 0), 3)));
					
					System.out.println("Looping around!");
					
				}
			}
		}).start();
		
	}
}
