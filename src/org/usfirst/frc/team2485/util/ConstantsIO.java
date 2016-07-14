package org.usfirst.frc.team2485.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Static class to interface IO between the RoboRio and the Driver Station. 
 * Used to save constants to a file rather than being hard coded. 
 * 
 * @author Ben Clark
 * @author Patrick Wamsley
 * @author Jeremy McCulloch
 */
public class ConstantsIO {

	public static final String ROBO_RIO_CONSTANTS_FILE_PATH = "/home/lvuser/Constants.txt", 
			DRIVER_STATION_CONSTANTS_FILE_PATH = "C:\\Users\\2485\\Documents\\frc-2016\\Constants.txt";

	
	public static HashMap<String, String> data;
	
	
	public static double kP_Shooter, kI_Shooter, kD_Shooter, kF_Shooter;
	public static double kP_DriveTo, kI_DriveTo, kD_DriveTo;
	public static double kP_DriveVelocity, kI_DriveVelocity, kD_DriveVelocity, kF_DriveVelocity;
	public static double kP_Rotate, kI_Rotate, kD_Rotate;
	public static double kP_IntakeArm, kI_IntakeArm, kD_IntakeArm;

	public static double kDriveVoltageRamp;
	public static double kDriveVelocityUpRamp, kDriveVelocityDownRamp;


	public static double kIntakeRollerSpeed, kLateralRollerSpeed;
	
	public static double kLongShotRPS, kBatterShotRPS;
	
	public static void init() {
		
		try {
			data = parseLoadFile(readLocalFile(ROBO_RIO_CONSTANTS_FILE_PATH));
		} catch (IOException e1) {
			e1.printStackTrace();
			
		}
		
		kLongShotRPS = Double.parseDouble(data.get("kLongShotRPS"));
		kBatterShotRPS = Double.parseDouble(data.get("kBatterShotRPS"));
	
		kP_Shooter = Double.parseDouble(data.get("kP_Shooter"));
		kI_Shooter = Double.parseDouble(data.get("kI_Shooter"));
		kD_Shooter = Double.parseDouble(data.get("kD_Shooter"));
		kF_Shooter = Double.parseDouble(data.get("kF_Shooter"));
		
		kP_DriveTo = Double.parseDouble(data.get("kP_DriveTo"));
		kI_DriveTo = Double.parseDouble(data.get("kI_DriveTo"));
		kD_DriveTo = Double.parseDouble(data.get("kD_DriveTo"));
		
		kP_Rotate = Double.parseDouble(data.get("kP_Rotate"));
		kI_Rotate = Double.parseDouble(data.get("kI_Rotate"));
		kD_Rotate = Double.parseDouble(data.get("kD_Rotate"));
		
		kP_IntakeArm = Double.parseDouble(data.get("kP_IntakeArm"));
		kI_IntakeArm = Double.parseDouble(data.get("kI_IntakeArm"));
		kD_IntakeArm = Double.parseDouble(data.get("kD_IntakeArm"));
				
		kP_DriveVelocity = Double.parseDouble(data.get("kP_DriveVelocity"));
		kI_DriveVelocity = Double.parseDouble(data.get("kI_DriveVelocity"));
		kD_DriveVelocity = Double.parseDouble(data.get("kD_DriveVelocity"));
		kF_DriveVelocity = Double.parseDouble(data.get("kF_DriveVelocity"));
		
		kDriveVoltageRamp = Double.parseDouble(data.get("kDriveVoltageRamp"));
		kDriveVelocityUpRamp = Double.parseDouble(data.get("kDriveVelocityUpRamp"));
		kDriveVelocityDownRamp = Double.parseDouble(data.get("kDriveVelocityDownRamp"));

		
		kIntakeRollerSpeed = Double.parseDouble(data.get("kIntakeRollerSpeed"));
		kLateralRollerSpeed = Double.parseDouble(data.get("kLateralRollerSpeed"));

	}

	/**
	 * Used to read a file locally. 
	 * @param filePath
	 */
	public static String readLocalFile(String filePath) throws IOException {
		File file = new File(filePath); 
		String fileString; 

		StringBuilder fileContents = new StringBuilder((int)file.length()); 
		Scanner scanner = new Scanner(file); 
		String lineSeperator = "\n"; 

		try {
			while (scanner.hasNextLine())
				fileContents.append(scanner.nextLine() + lineSeperator);
			fileString = fileContents.toString(); 
			//remove the added "\n" 
			fileString = fileString.substring(0, fileString.length() - 1); 
		} finally {
			scanner.close();
		}
		return fileString; 
	}

	/**
	 * @param loadFileContents
	 * @return HashMap containing constant names and their values as declared in the load file. 
	 */
	public static HashMap<String, String> parseLoadFile(String fileContents) {

		HashMap<String, String> constantsMap = new HashMap<String, String>(); 
		Scanner scanner = new Scanner(fileContents); 
		
		while (scanner.hasNextLine()) {

			String currLine = scanner.nextLine().trim(); 

			if (currLine.contains("=")) {

				String constantName	 = currLine.substring(0, currLine.indexOf("=")).trim(); 
				String constantValue = currLine.substring(currLine.indexOf("=") + 1).trim();  

				constantsMap.put(constantName, constantValue);
			}
		}
		scanner.close();
		return constantsMap; 
	}

	/**
	 * NEEDS TO BE WRITTEN AND DEPLOTED FROM ELSE WHERE: WIDGITS? 
	 */
	public static void writeConstantsToRoboRio(String loadFileContents) {

		PrintWriter printWriter = null; 

		try {
			printWriter = new PrintWriter(new FileOutputStream("ftp://roborio-2485-frc.local" + ROBO_RIO_CONSTANTS_FILE_PATH)); //definitely won't work 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 

		if (printWriter != null) {
			printWriter.write(loadFileContents); 
			printWriter.flush();
			printWriter.close();
		} else {
			System.err.println("PrintWriting failed to init, unable to write constants.");
		}

	}
	
}

