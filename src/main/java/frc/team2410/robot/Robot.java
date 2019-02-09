package frc.team2410.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.Subsystems.*;

import static frc.team2410.robot.RobotMap.*;

public class Robot extends TimedRobot
{
	public static Drivetrain drivetrain;
	public static PigeonNav gyro;
	public static OI oi;
	public static Vision vision;
	public static SemiAuto semiAuto;
	public static Elevator elevator;
	public static Climb climb;
	
	private float smp;
	private float smi;
	private float smd;
	private double gp;
	private double gi;
	private double gd;
	static boolean fieldOriented = true;

	public Robot() {}
	
	@Override
	public void robotInit() {
		//Create subsystems
		gyro = new PigeonNav();
		drivetrain = new Drivetrain();
		oi = new OI();
		vision = new Vision();
		semiAuto = new SemiAuto();
		elevator = new Elevator();
		climb = new Climb();
		
		//Put PID changers so we don't have to push code every tune
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
	
	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("FL Angle", drivetrain.fl.getAngle());
		SmartDashboard.putNumber("FR Angle", drivetrain.fr.getAngle());
		SmartDashboard.putNumber("BL Angle", drivetrain.bl.getAngle());
		SmartDashboard.putNumber("BR Angle", drivetrain.br.getAngle());
		SmartDashboard.putNumber("FL Voltage", drivetrain.fl.positionEncoder.getVoltage());
		SmartDashboard.putNumber("FR Voltage", drivetrain.fr.positionEncoder.getVoltage());
		SmartDashboard.putNumber("BL Voltage", drivetrain.bl.positionEncoder.getVoltage());
		SmartDashboard.putNumber("BR Voltage", drivetrain.br.positionEncoder.getVoltage());
		SmartDashboard.putNumber("CenterX", vision.getCentralValue()[0]);
		SmartDashboard.putNumber("CenterY", vision.getCentralValue()[1]);
		SmartDashboard.putNumber("Heading", gyro.getHeading());
		SmartDashboard.putNumber("Drivetrain Travel", drivetrain.getTravel());
		SmartDashboard.putNumber("Desired Heading", drivetrain.wrap(drivetrain.desiredHeading, -180.0, 180.0));
		SmartDashboard.putNumber("Wrist Angle", elevator.getWristAngle());
		SmartDashboard.putNumber("Wrist Rollover", elevator.intake.rollover);
		SmartDashboard.putNumber("Elevator height", elevator.getPosition());
		SmartDashboard.putNumber("Elevator target", elevator.getTarget());
		SmartDashboard.putNumber("Place State", semiAuto.placeState);
	}
	
	@Override
	public void disabledInit() {}
	
	@Override
	public void disabledPeriodic() {}
	
	@Override
	public void autonomousInit() {
		drivetrain.startTravel();
		elevator.reset(0);
	}
	
	@Override
	public void autonomousPeriodic() {
		this.teleopPeriodic(); //no difference.  sandstorm is dumb
	}
	
	@Override
	public void teleopInit() {} //doesn't matter
	
	@Override
	public void teleopPeriodic() {
		//Run subsystem loops
		oi.pollButtons();
		drivetrain.joystickDrive(fieldOriented);
		elevator.loop();
		SmartDashboard.putBoolean("White status LED", vision.getCentralValue()[0] != 0);
		
		//Set PIDs from dashboard (probably shouldn't be doing this but it doesn't really hurt anything)
		/*smp = (float)SmartDashboard.getNumber("swerve p", 0.0);
		smi = (float)SmartDashboard.getNumber("swerve i", 0.0);
		smd = (float)SmartDashboard.getNumber("swerve d", 0.0);
		gp = SmartDashboard.getNumber("gyro p", 0.0);
		gi = SmartDashboard.getNumber("gyro i", 0.0);
		gd = SmartDashboard.getNumber("gyro d", 0.0);
		drivetrain.setGyroPID(gp, gi, gd);
		drivetrain.setPID(smp, smi, smd);*/
	}
	
	@Override
	public void testPeriodic() {
		drivetrain.crabDrive(0, 1, 0, 1, false);
	}
}