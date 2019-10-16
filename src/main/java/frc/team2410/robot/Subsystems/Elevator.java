package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.Encoder;
import frc.team2410.robot.Robot;
import frc.team2410.robot.TalonPair;

import static frc.team2410.robot.RobotMap.*;

public class Elevator {
	public TalonPair winchMotor;
	private Encoder heightEncoder;
	public Intake intake;
	private boolean open = true;
	
	private double targetHeight;
	public double targetWrist;
	private double offset;
	private boolean checkStartReleased = false;
	
	public Elevator() {
		intake = new Intake();
		winchMotor = new TalonPair(ELEVATOR_A, ELEVATOR_B, true, false);
		heightEncoder = new Encoder(ELEVATOR_ENCODER_A, ELEVATOR_ENCODER_B);
		heightEncoder.setDistancePerPulse(WINCH_DIST_PER_PULSE);
		heightEncoder.reset();
		targetWrist = getWristAngle();
	}
	
	public void moveTo(double height) {
		targetHeight = height;
	}
	
	public void setSpeed(double speed) {winchMotor.set(speed); }
	
	public void moveWristTo(double angle) {
		targetWrist = angle;
	}
	
	public double getPosition() {
		return heightEncoder.getDistance() + offset;
	}
	
	public double getTarget() { return targetHeight; }
	
	public double getWristAngle() { return intake.getWrist(); }
	
	public boolean getHatchStatus() { return open; }
	
	public void reset(double height) {
		heightEncoder.reset();
		offset = height;
		targetHeight = height;
	}
	
	public void loop() {
		double elevatorStick = Robot.oi.getAnalogStick(true, true);
		double wristStick = Robot.oi.getAnalogStick(false, true);
		if(Robot.oi.startPressed()) {
			winchMotor.set(0.2);
			checkStartReleased = true;
		} else if(checkStartReleased) {
			winchMotor.set(0);
			reset(0);
			checkStartReleased = false;
		} else if(elevatorStick == 0 && !Robot.semiAuto.lift) {
			double speed = -((targetHeight-getPosition())/4.50);
			if(speed > 0 && !Robot.semiAuto.ceng) speed /= 10.0;
			if(speed < -1) speed = -1;
			if(speed > 1) speed = 1;
			winchMotor.set(speed);
		} else if(!Robot.semiAuto.lift && !(getPosition() < 0.5 && elevatorStick > 0) && !(getPosition() > 60 && elevatorStick < 0)) {
			winchMotor.set(elevatorStick);
			targetHeight = getPosition();
		} if(wristStick == 0) {
			double speed = ((targetWrist-intake.getWrist())/25);
			if(Math.abs(targetWrist-intake.getWrist()) < 1) speed = 0;
			if(speed < -WRIST_MAX_SPEED) speed = -WRIST_MAX_SPEED;
			if(speed > WRIST_MAX_SPEED) speed = WRIST_MAX_SPEED;
			intake.setWrist(speed);
		} else if(!(intake.getWrist() > 75 && wristStick < 0)) {
			intake.setWrist(-wristStick);
			targetWrist = intake.getWrist();
		} else {
			intake.setWrist(0);
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
	
	public double getWristVoltage() {
		return intake.GetWristCurrent();
	}
}
