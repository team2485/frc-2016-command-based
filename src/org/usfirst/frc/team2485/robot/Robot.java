
package org.usfirst.frc.team2485.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2485.robot.BlockingCommandFactory.AutoType;
import org.usfirst.frc.team2485.robot.commands.AutoAim;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public void robotInit() {
		ConstantsIO.init();
		RobotMap.init();
		OI.init();
		RobotMap.updateConstants();
		
		SmartDashboard.putNumber("Batter Shot Target", AutoAim.BATTER_SHOT_PIXEL_TARGET);
		SmartDashboard.putNumber("Long Shot Target", AutoAim.LONG_SHOT_PIXEL_TARGET);
	}

	public void disabledInit() {
		RobotMap.driveTrain.reset();
		RobotMap.shooter.reset();
		RobotMap.intakeArm.reset();
		RobotMap.boulderStager.reset(); 
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
		updateSmartDashboard();
	}

	public void autonomousInit() {
		ConstantsIO.init();
		RobotMap.updateConstants();

		RobotMap.gyro.reset();
		RobotMap.driveTrain.reset();

		
		new Thread(new Runnable() {

			boolean cameraFound = false;

			@Override
			public void run() {

				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						cameraFound = true;
					}
				}, 60 * 1000);

//				while (!cameraFound) {
//					if (!((Object) CameraServer.getInstance()).isAutoCaptureStarted()) {
//						CameraServer.getInstance().startAutomaticCapture("cam0", 0);
//						cameraFound = true;
//					}
//				}
			}
		}).start();

		BlockingCommandFactory.runAuto(AutoType.LOW_BAR_AUTO, 1);
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		updateSmartDashboard();

	}

	public void teleopInit() {
		ConstantsIO.init();
		RobotMap.updateConstants();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		updateSmartDashboard();
	}

	public void testInit() {
		ConstantsIO.init();
		RobotMap.updateConstants();
	}

	public void testPeriodic() {

		if (RobotMap.pressureSwitch.get()) {
			RobotMap.compressorSpike.set(Relay.Value.kOff);
		} else {
			RobotMap.compressorSpike.set(Relay.Value.kForward);
		}
		
		updateSmartDashboard();

	}

	public void updateSmartDashboard() {

		SmartDashboard.putNumber("Left enc vel", RobotMap.leftDriveEnc.getRate());

		SmartDashboard.putNumber("Right enc vel", RobotMap.rightDriveEnc.getRate());

		SmartDashboard.putNumber("Graphable RPM", RobotMap.shooter.getRate());

		SmartDashboard.putString("RPM",
				(int) (RobotMap.shooter.getRate() * 60) + "," + (int) RobotMap.shooter.getSetpoint() * 60);

		SmartDashboard.putNumber("Current Error", RobotMap.shooter.getError());

		SmartDashboard.putNumber("Throttle", RobotMap.shooter.getCurrentPower());

		SmartDashboard.putBoolean("Boulder Detector", RobotMap.boulderDetector.hasBoulder());

		/*
		 * Expects values separated by a comma: Current Angle,Encoder Reading
		 * for Floor,Reading for Intake,Reading for Full Up
		 */
		SmartDashboard.putString("Intake Arm Angle", RobotMap.intakeArm.getCurrPos() + "," + IntakeArm.FLOOR_POSITION
				+ "," + IntakeArm.INTAKE_POSITION + "," + IntakeArm.FULL_UP_POSITION);

	}
}
