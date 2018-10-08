package frc.team2410.robot.Subsystems;

import frc.team2410.robot.Robot;
import frc.team2410.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;

public class Arm {
	
	public WPI_TalonSRX armMotor;
	private Intake intake;
	public Encoder cimcoder;
	private double position;
	private boolean intakeClosed;
	private boolean intakeKicked;
	public double targetPos;
	double offset;

	public Arm() {
		this.armMotor = new WPI_TalonSRX(RobotMap.ARM_CIM);
		this.intake = new Intake();
		this.close();
		this.kickUp();
		this.intakeClosed = true;
		this.intakeKicked = false;
		this.toggleIntake();
		this.cimcoder = new Encoder(RobotMap.WINCH_CIMCODER_A, RobotMap.WINCH_CIMCODER_B);
		this.cimcoder.setDistancePerPulse(RobotMap.WINCH_DIST_PER_PULSE);
		this.setPosition(0);
		this.targetPos = cimcoder.getDistance();
	}
	
	public void loop() {
		//keeps elevator within half an inch of target position
		if (Robot.oi.getStick() == 0) {
			double aspeed;
			aspeed = (this.targetPos - this.getPosition())/10;
			if(Math.abs(this.targetPos-this.getPosition()) < 1) aspeed = 0;
			if(aspeed < -1) aspeed = -1;
			if(aspeed > 1) aspeed = 1;
			this.armMotor.set(aspeed);
		}
	}
	
	public void autoLoop() {
		double aspeed;
		aspeed = (this.targetPos - this.getPosition())/10;
		if(Math.abs(this.targetPos-this.getPosition()) < 1) aspeed = 0;
		if(aspeed < -1) aspeed = -1;
		if(aspeed > 1) aspeed = 1;
		this.armMotor.set(aspeed);
	}

	public void moveTo(double pos) {
		this.targetPos = pos;
	}

	public void toggleIntake() {
		this.intakeClosed = this.intake.setState(!intakeClosed);
	}

	public void toggleKick() {
		this.intakeKicked = this.intake.setKicked(!intakeKicked);
	}

	public void close() {
		this.intakeClosed = this.intake.setState(true);
	}

	public void open() {
		this.intakeClosed = this.intake.setState(false);
	}

	public void kickDown() {
		this.intakeKicked = this.intake.setKicked(true);
	}

	public void kickUp() {
		this.intakeKicked = this.intake.setKicked(false);
	}

	public double getCurrent() {
		return this.armMotor.getOutputCurrent();
	}
	
	public double getPosition() {
		return cimcoder.getDistance() + offset;
	}
	
	public void setPosition(double position) {
		cimcoder.reset();
		offset = position;
		moveTo(position);
	}
}
