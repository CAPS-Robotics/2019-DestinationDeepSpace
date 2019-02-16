package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.Encoder;
import frc.team2410.robot.Robot;
import frc.team2410.robot.TalonPair;

import static frc.team2410.robot.RobotMap.*;

public class Elevator {
	private TalonPair winchMotor;
	private Encoder heightEncoder;
	public Intake intake;
	private boolean open = false;
	
	private double targetHeight;
	public double targetWrist;
	private double offset;
	
	public Elevator() {
		intake = new Intake();
		winchMotor = new TalonPair(ELEVATOR_A, ELEVATOR_B, true, true);
		heightEncoder = new Encoder(ELEVATOR_ENCODER_A, ELEVATOR_ENCODER_B);
		heightEncoder.setDistancePerPulse(WINCH_DIST_PER_PULSE);
		heightEncoder.reset();
		targetWrist = getWristAngle();
	}
	
	public void moveTo(double height) {
		this.targetHeight = height;
	}
	
	public void moveWristTo(double angle) {
		targetWrist = angle;
	}
	
	public double getPosition() {
		return heightEncoder.getDistance() + offset;
	}
	
	public double getTarget() { return targetHeight; }
	
	public double getWristAngle() { return intake.getWrist(); }
	
	public void reset(double height) {
		heightEncoder.reset();
		offset = height;
		targetHeight = height;
	}
	
	public void loop() {
		if(Robot.oi.getAnalogStick(true, true) == 0) {
			double speed = -((targetHeight-getPosition())/15);
			if(Math.abs(targetHeight-getPosition()) < 1) speed = 0;
			if(speed < -1) speed = -1;
			if(speed > 1) speed = 1;
			winchMotor.set(speed);
		} else {
			winchMotor.set(Robot.oi.getAnalogStick(true, true));
			targetHeight = getPosition();
		} if(Robot.oi.getAnalogStick(false, true) == 0) {
			double speed = -((targetWrist-intake.getWrist())/40);
			if(Math.abs(targetWrist-intake.getWrist()) < 1) speed = 0;
			if(speed < -1) speed = -1;
			if(speed > 1) speed = 1;
			intake.setWrist(speed);
		} else {
			intake.setWrist(-Robot.oi.getAnalogStick(false, true));
			targetWrist = intake.getWrist();
		}
	}
	
	public void setIntake(boolean in) {
		intake.setWheel(in);
	}
	
	public void stopIntake() {
		intake.stop();
	}
	
	public void toggleHatch() {
		open = !open;
		intake.setPiston(open);
	}
	
	public void setHatch(boolean open) {
		intake.setPiston(open);
	}
}
