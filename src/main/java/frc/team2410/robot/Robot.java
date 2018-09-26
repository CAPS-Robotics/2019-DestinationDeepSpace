package frc.team2410.robot;

import frc.team2410.robot.OI;
import frc.team2410.robot.RobotMap;
import frc.team2410.robot.Subsystems.*;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot
{
	public static Drivetrain drivetrain;
	public static PigeonNav gyro;
	public static OI oi;
	public static Arm arm;
	public static Vision vision;
	public static Autonomous autonomous;

	public Robot() {
	}

	public void robotInit() {
		drivetrain = new Drivetrain();
		oi = new OI(); // always last subsystem??
	}
}