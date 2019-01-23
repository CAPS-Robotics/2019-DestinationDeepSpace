package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import frc.team2410.robot.Robot;
import frc.team2410.robot.TalonPair;

import static frc.team2410.robot.RobotMap.*;

public class Elevator {
	private TalonPair winchMotor;
	private Encoder heightEncoder;
	
	private Intake intake;
	
	private double targetHeight;
	
	public Elevator() {
		intake = new Intake();
		winchMotor = new TalonPair(ELEVATOR_A, ELEVATOR_B, false, true);
		heightEncoder = new Encoder(WINCH_CIMCODER_A, WINCH_CIMCODER_B);
		heightEncoder.setDistancePerPulse(WINCH_DIST_PER_PULSE);
		heightEncoder.reset();
	}
	
	public void moveTo(double height) {
		this.targetHeight = height;
	}
	
	public double getPosition() {
		return heightEncoder.getDistance();
	}
	
	public void loop() {
		if(Robot.oi.getAnalogStick(true, true) == 0) {
			double aspeed;
			aspeed = ((targetHeight-getPosition())/10);
			if(Math.abs(targetHeight-getPosition()) < 1) aspeed = 0;
			if(aspeed < -1) aspeed = -1;
			if(aspeed > 1) aspeed = 1;
			winchMotor.set(aspeed);
		} else {
			winchMotor.set(Robot.oi.getAnalogStick(true, true));
			targetHeight = getPosition();
		}
		intake.setWrist(Robot.oi.getAnalogStick(false, true));
	}
	
	public void setIntake(boolean in) {
		intake.setWheel(in);
	}
	public void stopIntake() {
		intake.stop();
	}
	public void toggleHatch() {
		intake.togglePiston();
	}
}
