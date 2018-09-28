package frc.team2410.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.Subsystems.*;

public class Robot extends IterativeRobot
{
	public static Drivetrain drivetrain;
	public static PigeonNav gyro;
	public static OI oi;
	public static Arm arm;
	public static Vision vision;
	public static Autonomous autonomous;
	enum AutoStations {
		LEFT,
		CENTER,
		RIGHT
	}
	SendableChooser<AutoStations> autoPicker;
	public float smp;
	public float smi;
	public float smd;
	public float gp;
	public float gi;
	public float gd;

	public Robot() {
	}

	public void robotInit() {
		vision = new Vision();
		gyro = new PigeonNav();
		drivetrain = new Drivetrain();
		arm = new Arm();
		oi = new OI();
		autonomous = new Autonomous();
		this.autoPicker = new SendableChooser<>();
		this.autoPicker.addDefault("Middle Station Auton", AutoStations.CENTER);
		this.autoPicker.addObject("Left Station Auton", AutoStations.LEFT);
		this.autoPicker.addObject("Right Station Auton", AutoStations.RIGHT);
		SmartDashboard.putData("Auto Picker", this.autoPicker);
		smp = RobotMap.SWERVE_MODULE_P;
		smi = RobotMap.SWERVE_MODULE_I;
		smd = RobotMap.SWERVE_MODULE_D;
		/*gp = GYRO_P;
		gi = GYRO_I;
		gd = GYRO_D;*/
		SmartDashboard.putString("Auto Picked", ""+this.autoPicker.getSelected());
		SmartDashboard.putNumber("swerve p", smp);
		SmartDashboard.putNumber("swerve i", smi);
		SmartDashboard.putNumber("swerve d", smd);
		/*SmartDashboard.putNumber("gyro p", gp);
		SmartDashboard.putNumber("gyro i", gi);
		SmartDashboard.putNumber("gyro d", gd);*/
	}
	public void disabledInit() {}
	public void disabledPeriodic() {
		SmartDashboard.putData("Auto Picker", this.autoPicker);
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
		autonomous.init((int)SmartDashboard.getNumber("Auto Picked", 0), DriverStation.getInstance().getGameSpecificMessage());
	}
	
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("FL Angle", drivetrain.fl.getAngle());
		SmartDashboard.putNumber("FR Angle", drivetrain.fr.getAngle());
		SmartDashboard.putNumber("BL Angle", drivetrain.bl.getAngle());
		SmartDashboard.putNumber("BR Angle", drivetrain.br.getAngle());
		SmartDashboard.putNumber("Distance Away", drivetrain.getDistanceAway());
		SmartDashboard.putNumber("Heading", gyro.getHeading());
		SmartDashboard.putNumber("CenterX", vision.getCentralValue());
		autonomous.loop();
	}
	
	public void teleopInit() {
		arm.cimcoder.reset();
		drivetrain.startTravel();
	}
	
	public void teleopPeriodic() {
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
		SmartDashboard.putNumber("CenterX", vision.getCentralValue());
		SmartDashboard.putNumber("Elevator Height", arm.cimcoder.getDistance());
		SmartDashboard.putNumber("Target Height", arm.targetPos);
		SmartDashboard.putNumber("Arm Current", arm.getCurrent());
		//SmartDashboard.putNumber("Desired Heading", /*Drivetrain.wrap(*/drivetrain.desiredHeading/*+180.0, -180.0, 180.0)*/);
		smp = (float)SmartDashboard.getNumber("swerve p", 0.0);
		smi = (float)SmartDashboard.getNumber("swerve i", 0.0);
		smd = (float)SmartDashboard.getNumber("swerve d", 0.0);
		/*gp = (float)SmartDashboard.getNumber("gyro p", 0.0);
		gi = (float)SmartDashboard.getNumber("gyro i", 0.0);
		gd = (float)SmartDashboard.getNumber("gyro d", 0.0);*/
		
		oi.pollButtons();
		arm.loop();
		
		drivetrain.joystickDrive();
		/*drivetrain.SetPID(gp, gi, gd);*/
		drivetrain.fl.setPID(smp, smi, smd);
		drivetrain.fr.setPID(smp, smi, smd);
		drivetrain.bl.setPID(smp, smi, smd);
		drivetrain.br.setPID(smp, smi, smd);
	}
	
	public void testPeriodic() {}
}
