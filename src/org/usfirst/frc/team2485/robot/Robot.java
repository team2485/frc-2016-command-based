
package org.usfirst.frc.team2485.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2485.robot.BlockingCommandFactory.AutoType;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {

	public void robotInit() {
		ConstantsIO.init();
		RobotMap.init();
		OI.init();
		RobotMap.updateConstants();
	}

	public void disabledInit(){
		RobotMap.driveTrain.reset();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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

				while (!cameraFound) {
					if (!CameraServer.getInstance().isAutoCaptureStarted()) {
						CameraServer.getInstance()
								.startAutomaticCapture("cam0");
						cameraFound = true;
					}
				}
			}
		}).start();
		
		BlockingCommandFactory.runAuto(AutoType.LOW_BAR_AUTO, 1);
	}


	public void autonomousPeriodic() {
		Scheduler.getInstance().run();	
		
	}

	public void teleopInit() {
		ConstantsIO.init();
		RobotMap.updateConstants();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testInit(){
		ConstantsIO.init();
		RobotMap.updateConstants();
	}

	public void testPeriodic() {
		
		if (RobotMap.pressureSwitch.get()) {
			RobotMap.compressorSpike.set(Relay.Value.kOff);
		} else {
			RobotMap.compressorSpike.set(Relay.Value.kForward);
		}
		
	}
}
