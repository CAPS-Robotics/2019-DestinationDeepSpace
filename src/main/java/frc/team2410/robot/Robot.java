package frc.team2410.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.Subsystems.*;

import static frc.team2410.robot.RobotMap.GYRO_D;
import static frc.team2410.robot.RobotMap.GYRO_I;
import static frc.team2410.robot.RobotMap.GYRO_P;

public class Robot extends IterativeRobot
{
	public static Drivetrain drivetrain;
	public static PigeonNav gyro;
	public static OI oi;
	public float smp;
	public float smi;
	public float smd;
	public double gp;
	public double gi;
	public double gd;

	public Robot() {}

	public void robotInit() {
		gyro = new PigeonNav();
		drivetrain = new Drivetrain();
		oi = new OI();
		smp = RobotMap.SWERVE_MODULE_P;
		smi = RobotMap.SWERVE_MODULE_I;
		smd = RobotMap.SWERVE_MODULE_D;
		gp = GYRO_P;
		gi = GYRO_I;
		gd = GYRO_D;
		SmartDashboard.putNumber("swerve p", smp);
		SmartDashboard.putNumber("swerve i", smi);
		SmartDashboard.putNumber("swerve d", smd);
		SmartDashboard.putNumber("gyro p", gp);
		SmartDashboard.putNumber("gyro i", gi);
		SmartDashboard.putNumber("gyro d", gd);
	}

	public void disabledInit() {}

	public void disabledPeriodic() {
		SmartDashboard.putNumber("FL Voltage", drivetrain.fl.positionEncoder.getVoltage());
		SmartDashboard.putNumber("FR Voltage", drivetrain.fr.positionEncoder.getVoltage());
		SmartDashboard.putNumber("BL Voltage", drivetrain.bl.positionEncoder.getVoltage());
		SmartDashboard.putNumber("BR Voltage", drivetrain.br.positionEncoder.getVoltage());
		SmartDashboard.putNumber("FL Angle", drivetrain.fl.getAngle());
		SmartDashboard.putNumber("FR Angle", drivetrain.fr.getAngle());
		SmartDashboard.putNumber("BL Angle", drivetrain.bl.getAngle());
		SmartDashboard.putNumber("BR Angle", drivetrain.br.getAngle());
	}

	public void autonomousInit() {
	
	}

	public void autonomousPeriodic() {
		SmartDashboard.putNumber("FL Angle", drivetrain.fl.getAngle());
		SmartDashboard.putNumber("FR Angle", drivetrain.fr.getAngle());
		SmartDashboard.putNumber("BL Angle", drivetrain.bl.getAngle());
		SmartDashboard.putNumber("BR Angle", drivetrain.br.getAngle());
		SmartDashboard.putNumber("Distance Away", drivetrain.getDistanceAway());
		SmartDashboard.putNumber("Travel", drivetrain.getTravel());
		SmartDashboard.putNumber("Heading", gyro.getHeading());
	}

	public void teleopInit() {
		drivetrain.startTravel();
	}

	public void teleopPeriodic() {
		// Output Info to Smart Dashboard
		SmartDashboard.putNumber("FL Voltage", drivetrain.fl.positionEncoder.getVoltage());
		SmartDashboard.putNumber("FR Voltage", drivetrain.fr.positionEncoder.getVoltage());
		SmartDashboard.putNumber("BL Voltage", drivetrain.bl.positionEncoder.getVoltage());
		SmartDashboard.putNumber("BR Voltage", drivetrain.br.positionEncoder.getVoltage());
		SmartDashboard.putNumber("FL Angle", drivetrain.fl.getAngle());
		SmartDashboard.putNumber("FR Angle", drivetrain.fr.getAngle());
		SmartDashboard.putNumber("BL Angle", drivetrain.bl.getAngle());
		SmartDashboard.putNumber("BR Angle", drivetrain.br.getAngle());
		SmartDashboard.putNumber("Distance Away", drivetrain.getDistanceAway());
		SmartDashboard.putNumber("Heading", gyro.getHeading());
		SmartDashboard.putNumber("Drivetrain Travel", drivetrain.getTravel());
		SmartDashboard.putNumber("Desired Heading", drivetrain.wrap(drivetrain.desiredHeading, -180.0, 180.0));
		smp = (float)SmartDashboard.getNumber("swerve p", 0.0);
		smi = (float)SmartDashboard.getNumber("swerve i", 0.0);
		smd = (float)SmartDashboard.getNumber("swerve d", 0.0);
		gp = SmartDashboard.getNumber("gyro p", 0.0);
		gi = SmartDashboard.getNumber("gyro i", 0.0);
		gd = SmartDashboard.getNumber("gyro d", 0.0);

		oi.pollButtons();

		drivetrain.joystickDrive();
		drivetrain.setGyroPID(gp, gi, gd);
		drivetrain.fl.setPID(smp, smi, smd);
		drivetrain.fr.setPID(smp, smi, smd);
		drivetrain.bl.setPID(smp, smi, smd);
		drivetrain.br.setPID(smp, smi, smd);
	}

	public void testPeriodic() {}
}