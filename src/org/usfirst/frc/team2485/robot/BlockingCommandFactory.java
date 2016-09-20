package org.usfirst.frc.team2485.robot;


import org.usfirst.frc.team2485.robot.commandGroups.PrepForLongShot;
import org.usfirst.frc.team2485.robot.commandGroups.SetHoodPosition;
import org.usfirst.frc.team2485.robot.commandGroups.ShakeBoulderStager;
import org.usfirst.frc.team2485.robot.commandGroups.ShootHighGoal;
import org.usfirst.frc.team2485.robot.commands.DriveThrough;
import org.usfirst.frc.team2485.robot.commands.DriveTo;
import org.usfirst.frc.team2485.robot.commands.IntakeArmSetSetpoint;
import org.usfirst.frc.team2485.robot.commands.ResetDriveTrain;
import org.usfirst.frc.team2485.robot.commands.RotateTo;
import org.usfirst.frc.team2485.robot.commands.ZeroDriveEncoders;
import org.usfirst.frc.team2485.robot.subsystems.IntakeArm;
import org.usfirst.frc.team2485.robot.subsystems.Shooter.HoodPosition;
import org.usfirst.frc.team2485.util.BlockingCommand;
import org.usfirst.frc.team2485.util.CommandTimeout;
import org.usfirst.frc.team2485.util.ConstantsIO;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

public class BlockingCommandFactory {

	public enum AutoType {
		LOW_BAR_AUTO, RAMPARTS_AUTO, ROUGH_TERRAIN_AUTO, MOAT_AUTO, ROCK_WALL_AUTO,
		PORTCULLIS_AUTO, CHEVAL_DE_FRISE_AUTO, REACH_AUTO, TWO_BALL_SPY_AUTO
	}
	
	public static void runAuto(AutoType autoType, int defenseLocation) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				
				switch (autoType) {
				case REACH_AUTO:
					addBlockingCommand(new DriveTo(30, 100, 0, 0));
					break;
				case LOW_BAR_AUTO:
					addBlockingCommand(new SetHoodPosition(HoodPosition.STOWED));
					addBlockingCommand(new IntakeArmSetSetpoint(IntakeArm.LOW_NO_INTAKE_POSITION));
					addBlockingCommand(new CommandTimeout(0.5));
					
					CommandGroup shakeAndDrive = new CommandGroup();
					shakeAndDrive.addParallel(new CommandTimeout(new DriveThrough(206, 100, 40, 0, 0), 10));
					shakeAndDrive.addParallel(new ShakeBoulderStager());
					addBlockingCommand(shakeAndDrive);

					addBlockingCommand(new CommandTimeout(new DriveTo(206, 100, 0, 0), 10));
					addBlockingCommand(new ResetDriveTrain());
							
					CommandGroup rotateAndRaiseHood = new CommandGroup();
					shakeAndDrive.addParallel(new CommandTimeout(new RotateTo(57), 2));
					shakeAndDrive.addParallel(new SetHoodPosition(HoodPosition.LOW_ANGLE));
					addBlockingCommand(rotateAndRaiseHood);
					
					addBlockingCommand(new ResetDriveTrain());
					addBlockingCommand(new ZeroDriveEncoders());
					addBlockingCommand(new ShakeBoulderStager());
					// AlignToTower
					addBlockingCommand(new ResetDriveTrain());
					addBlockingCommand(new PrepForLongShot());
					addBlockingCommand(new CommandTimeout(2));
					addBlockingCommand(new ShootHighGoal());
					break;
		
//				case RAMPARTS_AUTO:
//					if (defenseLocation == 4) {
//						degreesToTurn = -8;
//					}
//					return new Sequencer(new SequencedItem[] {
//							new SequencedMultipleItem(
//									new DriveTo(distPreTurn, 8, 0.6), // changed to 6 seconds from 4
//									new Hat(true),
//									new SetHoodPosition(HoodPosition.HIGH_ANGLE)
//								),
//							new ResetDriveTrain(),
//							new SetIntakeArm(Intake.INTAKE_POSITION),
//							new ShakeBoulderStager(),
//							new RotateTo(degreesToTurn, 3),
//							new ResetDriveTrain(),
//							new ZeroDriveEncoder(),
//							new DriveTo(distPostTurn, 3, 0.65), // Approaches batter, may be
//							// removed when long shot works
//							new ResetDriveTrain(),
////							new SequencedMultipleItem(
////									new SpinUpShooter(Shooter.RPS_BATTER_SHOT), 
////									new AlignToTower(3.0)
////								),
////							new ResetDriveTrain(), 
////							new ShootHighGoal(5) 
//						});
//				case ROUGH_TERRAIN_AUTO:
//				case MOAT_AUTO:
//				case ROCK_WALL_AUTO:
//
//		
//					return new Sequencer(new SequencedItem[] {
//							new SequencedMultipleItem(
//									new DriveTo(distPreTurn, 8, 75, 2), // changed to 4.5 seconds from 4
//									new Hat(true),
//									new SetHoodPosition(HoodPosition.LOW_ANGLE)
//								),
//							new ResetDriveTrain(),
//							new SetIntakeArm(Intake.INTAKE_POSITION),
//							new ShakeBoulderStager(),
//							new RotateTo(degreesToTurn),
//							new ShakeBoulderStager(),
//							new ResetDriveTrain(),
//							new ZeroDriveEncoder(),
//							new ShakeBoulderStager(),
//							new SequencedMultipleItem(
//									new DriveTo(distPostTurn, 3, 100),
//									new SequencedPause(1)),
//							new ResetDriveTrain(),
//
//							new AlignToTower(4.0),
//							new SpinUpShooter(Shooter.RPS_LONG_SHOT), 
//							new SequencedPause(1.5),
//							new ResetDriveTrain(), 
//							new ShootHighGoal(5) 
//					});
//		
//				case PORTCULLIS_AUTO:
//					
//					distPreTurn = 140;
//					
//					if (defenseLocation == 2) {
//						distPostTurn = 25;
//					}
//		
//					// Not zeroing drive encoders until after distPreTurn
//		
//					return new Sequencer(new SequencedItem[] {
//							new SetIntakeArm(Intake.LOW_NO_INTAKE_POSITION),
//							new SetHoodPosition(HoodPosition.STOWED),
//							new DriveTo(45, 3, 30),
//							new ResetDriveTrain(),
//							new SequencedMultipleItem(
//									new DriveThrough(distPreTurn, 3, 85, 50),
//									new SetIntakeArm(Intake.FLOOR_POSITION),
//									new ShakeBoulderStager()),
//							new SetIntakeArm(Intake.INTAKE_POSITION),
//							new DriveTo(distPreTurn, 2, 30),
//							
//							
//							new ResetDriveTrain(), 
//							new RotateTo(degreesToTurn),
//							new ResetDriveTrain(),
//							
//							new SetHoodPosition(HoodPosition.LOW_ANGLE),
//							new ShakeBoulderStager(),
//							
//							new ZeroDriveEncoder(),
//							new DriveTo(distPostTurn, 2, 40),
//							new ResetDriveTrain(),
//							
//							new SequencedMultipleItem(
//									new AlignToTower(),
//									new ShakeBoulderStager()),
//									
//							new ResetDriveTrain(),
//							
//							new SpinUpShooter(Shooter.RPS_LONG_SHOT),
//							new SequencedPause(1.5),
//							new ShootHighGoal(5) 
//					});
//		
//				case CHEVAL_DE_FRISE_AUTO:
//		
//					// Not zeroing drive encoders until after distPreTurn
//		
//					return new Sequencer(new SequencedItem[] {
//							new DriveTo(30),
//							new SetIntakeArm(Intake.FLOOR_POSITION),
//							new ResetDriveTrain(),
//							new DriveTo(40, 1),
//							new ResetDriveTrain(),
//							new SequencedMultipleItem(new DriveTo(90, 4, 0.5),
//									new SetIntakeArm(Intake.FULL_UP_POSITION)),
//							new SequencedMultipleItem(new SetIntakeArm(
//									Intake.INTAKE_POSITION), new SetHoodPosition(
//									HoodPosition.HIGH_ANGLE)),
//							new SpinUpShooter(Shooter.RPS_BATTER_SHOT),
//							new DriveTo(distPreTurn), new ResetDriveTrain(),
//							new RotateTo(degreesToTurn), new ResetDriveTrain(),
//							new ZeroDriveEncoder(), new DriveTo(distPostTurn),
//							new ResetDriveTrain(), 
////							new AlignToTower(),
////							new ResetDriveTrain(), 
////							new ShootHighGoal(5) 
//							});
//				case TWO_BALL_SPY_AUTO:
//					return new Sequencer(new SequencedItem[] {
//							new SequencedMultipleItem(
//									new SetHoodPosition(HoodPosition.LOW_ANGLE),
//									new ShakeBoulderStager(),
//									new SetIntakeArm(Intake.INTAKE_POSITION)
//								),
//							new SpinUpShooter(Shooter.RPS_LONG_SHOT),
//							new SequencedPause(1),
//							new ShootHighGoal(),
//							new SequencedPause(0.25),
//							new SetHoodPosition(HoodPosition.STOWED),
//							new SpinUpShooter(0),
//							new DriveTo(50, 3, 100, 0, 120),
//							new ResetDriveTrain(),
//							new RotateTo(90, 2),
//							new ResetDriveTrain(),
//							new ZeroDriveEncoder(),
//							new DriveThrough(190, 4, 100, 120, 90),
//							new DriveThrough(190, 4, 140, 40, 90), 
//							new SequencedMultipleItem(
//									new SetRollers(ConstantsIO.kLateralRollerSpeed,
//											ConstantsIO.kIntakeRollerSpeed),
//									new SetIntakeArm(Intake.INTAKE_POSITION),
//									new DriveTo(190, 4, 25, 90)
//							),
//							new WaitForBoulder(),
//							new SetRollers(0, 0),
//							new DriveTo(0, 6, 100, 90),
//							new ResetDriveTrain(),
//							new SetHoodPosition(HoodPosition.LOW_ANGLE),
//							new RotateTo(-45, 2.5),
//							new ResetDriveTrain(),
//							new AlignToTower(),
//							new SpinUpShooter(Shooter.RPS_LONG_SHOT),
//							new SequencedPause(1),
//							new ShootHighGoal(5)			
//					});
					default: 
						break;
				}
				
			}
		}).start();
	}
	
	public static void addBlockingCommand(Command command) {
		
		Scheduler.getInstance().add(command);
		
		while (command.isRunning()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

