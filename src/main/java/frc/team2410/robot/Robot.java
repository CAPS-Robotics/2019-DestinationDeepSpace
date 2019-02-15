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
	public static LED led;
	
	private float smp;
	private float smi;
	private float smd;
	private double gp;
	private double gi;
	private double gd;
	static boolean fieldOriented = true;
	private int pState = -1;

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
		led = new LED();
		
		led.setColor(0, 0, 255);
		
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
		SmartDashboard.putNumber("Wrist Target", elevator.targetWrist);
		SmartDashboard.putNumber("Elevator height", elevator.getPosition());
		SmartDashboard.putNumber("Elevator target", elevator.getTarget());
		SmartDashboard.putNumber("Place State", semiAuto.placeState);
		SmartDashboard.putNumber("R", led.r);
		SmartDashboard.putNumber("G", led.g);
		SmartDashboard.putNumber("B", led.b);
	}
	
	@Override
	public void disabledInit() {
		led.setColor(255, 0, 0);
	}
	
	@Override
	public void disabledPeriodic() {
		led.fade(3);
	}
	
	@Override
	public void autonomousInit() {
		drivetrain.startTravel();
		elevator.reset(0);
		led.setColor(0, 0, 255);
		pState = -1;
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
		SmartDashboard.putBoolean("Line", vision.getCentralValue()[0] != 0);
		if(semiAuto.engaged) {
			led.status(255, 0, 0, 255, 7, 7, 10+(int)(10*Math.sqrt(oi.getX()*oi.getX()+oi.getY()*oi.getY())*oi.getSlider()), fieldOriented);
		} else if(vision.getCentralValue()[0] != 0) {
			led.status(0, 255, 0, 15, 255, 15, 10+(int)(10*Math.sqrt(oi.getX()*oi.getX()+oi.getY()*oi.getY())*oi.getSlider()), fieldOriented);
		} else {
			led.status(0, 0, 255, 31, 31, 255, 10+(int)(10*Math.sqrt(oi.getX()*oi.getX()+oi.getY()*oi.getY())*oi.getSlider()), fieldOriented);
		}
		
		SmartDashboard.putNumber("LED Speed", 10+(int)(10*Math.sqrt(oi.getX()*oi.getX()+oi.getY()*oi.getY())*oi.getSlider()));
		
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