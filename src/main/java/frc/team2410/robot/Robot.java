package frc.team2410.robot;

import frc.team2410.robot.OI;
import frc.team2410.robot.RobotMap;
import frc.team2410.robot.Subsystems.Arm;
import frc.team2410.robot.Subsystems.Drivetrain;
import frc.team2410.robot.Subsystems.PigeonNav;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot
{
	public static Drivetrain drivetrain;
	public static OI oi;
	public static Arm arm;
	public static PigeonNav gyro;

	public Robot()
	{
	}

	public void robotInit()
	{
		drivetrain = new Drivetrain();
		oi = new OI(); // always last subsystem??
	}
}