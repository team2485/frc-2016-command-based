package org.usfirst.frc.team2485.robot.subsystems;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2485.robot.RobotMap;
import org.usfirst.frc.team2485.robot.commandGroups.ShakeBoulderStager;
import org.usfirst.frc.team2485.robot.commands.RollersOn;
import org.usfirst.frc.team2485.robot.commands.SetBoulderStager;
import org.usfirst.frc.team2485.robot.subsystems.BoulderStager.StagerPosition;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BoulderDetector extends Subsystem {

	private Ultrasonic sonic;

	private boolean hasBoulder;

	private int numTimesBoulderDetected = 0, numTimesBoulderNotDetected = 0;
	private static final int MINIMUM_BOULDER_DETECTED_ITERATIONS = 5,
			MINIMUM_BOULDER_NOT_DETECTED_ITERATIONS = 5; // TODO tune value

	public BoulderDetector() {

		sonic = RobotMap.sonic;

		new TimingSystem().start();
	}

	private boolean boulderDetected() {
		return sonic.getRangeInches() < 10;
	}

	/**
	 * Determines whether we have a boulder based on sonic sensor, may be
	 * delayed by 100 ms
	 * 
	 * @return true if we have a boulder
	 */
	public boolean hasBoulder() {
		return hasBoulder;
	}

	private class TimingSystem extends Thread {

		@Override
		public void run() {

			while (true) {

				if (boulderDetected()) {
					numTimesBoulderDetected++;
					numTimesBoulderNotDetected = 0;
				} else {
					numTimesBoulderDetected = 0;
					numTimesBoulderNotDetected++;
				}

				if (numTimesBoulderDetected > MINIMUM_BOULDER_DETECTED_ITERATIONS
						&& !hasBoulder) {

					hasBoulder = true;

					// RobotMap.intake.setSetpoint(Intake.FULL_UP_POSITION,
					// false);
					Scheduler.getInstance().add(new RollersOn(0, 0));

					if (RobotMap.shooter.getSetpoint() == 0) {
						
						Scheduler.getInstance().add(new ShakeBoulderStager());
						
					} else {
						Scheduler.getInstance().add(new SetBoulderStager(StagerPosition.NEUTRAL));
					}

				} else if (numTimesBoulderNotDetected > MINIMUM_BOULDER_NOT_DETECTED_ITERATIONS
						&& hasBoulder) {
					hasBoulder = false;
				}

				try {
					Thread.sleep(20);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}