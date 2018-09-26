package frc.team2410.robot.Subsystems;

import frc.team2410.robot.Robot;
import frc.team2410.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;

public class Arm
{

	public WPI_TalonSRX armMotor;
	private Intake intake;
	public Encoder cimcoder;
	private double position;
	private boolean intakeClosed;
	private boolean intakeKicked;
	public double targetPos;

	public Arm()
	{
		this.armMotor = new WPI_TalonSRX(RobotMap.ARM_CIM);
		this.intake = new Intake();
		this.intakeClosed = true;
		this.intakeKicked = false;
		this.toggleIntake();
		this.cimcoder = new Encoder(RobotMap.WINCH_CIMCODER_A, RobotMap.WINCH_CIMCODER_B);
		this.cimcoder.setDistancePerPulse(RobotMap.WINCH_DIST_PER_PULSE);
		this.cimcoder.reset();
		this.targetPos = cimcoder.getDistance();
	}

	public void loop()
	{
		if (!Robot.oi.joy1.getRawButton(5) && !Robot.oi.joy1.getRawButton(3) && Math.abs(this.getCurrent()) < 30)
		{
			if (Math.abs(this.cimcoder.getDistance() - this.targetPos) < .5)
			{
				this.armMotor.set(0);
			}
			else if (this.targetPos - this.cimcoder.getDistance() > 0)
			{
				this.armMotor.set(1);
			}
			else
			{
				this.armMotor.set(-1);
			}
		}

		if (Math.abs(this.getCurrent()) > 30)
		{
			this.armMotor.set(0);
		}
	}

	public void moveTo(double pos)
	{
		this.targetPos = pos;
	}

	public void toggleIntake()
	{
		this.intakeClosed = this.intake.setState(!intakeClosed);
	}

	public void toggleKick()
	{
		this.intakeKicked = this.intake.setKicked(!intakeKicked);
	}

	public void close()
	{
		this.intakeClosed = this.intake.setState(true);
	}

	public void open()
	{
		this.intakeClosed = this.intake.setState(false);
	}

	public void kickDown()
	{
		this.intakeKicked = this.intake.setKicked(true);
	}

	public void kickUp()
	{
		this.intakeKicked = this.intake.setKicked(false);
	}

	public double getCurrent()
	{
		return this.armMotor.getOutputCurrent();
	}
}
