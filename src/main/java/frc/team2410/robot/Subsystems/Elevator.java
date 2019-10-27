package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.Encoder;
import frc.team2410.robot.Robot;
import frc.team2410.robot.TalonPair;

import static frc.team2410.robot.RobotMap.*;

public class Elevator {
	public TalonPair winchMotor;
	private Encoder heightEncoder;
	
	private double targetHeight;
	public double targetWrist;
	private double offset;
	private boolean checkStartReleased = false;
	
	
	public Elevator() {
		winchMotor = new TalonPair(ELEVATOR_A, ELEVATOR_B, true, false);
		heightEncoder = new Encoder(ELEVATOR_ENCODER_A, ELEVATOR_ENCODER_B);
		heightEncoder.setDistancePerPulse(WINCH_DIST_PER_PULSE);
		heightEncoder.reset();
	}
	
	public void moveTo(double height) {
		targetHeight = height;
	}
	
	public void setSpeed(double speed) {winchMotor.set(speed); }
	
	public double getPosition() {
		return heightEncoder.getDistance() + offset;
	}
	
	public double getTarget() { return targetHeight; }
	
	public void reset(double height) {
		heightEncoder.reset();
		offset = height;
		targetHeight = height;
	}
	
	public void loop() {
		double elevatorStick = Robot.oi.getAnalogStick(true, true);
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
		}
	}

	public void autoLoop() {
		double speed = -((targetHeight-getPosition())/4.50);
		if(speed > 0) speed /= 10.0;
		if(speed < -1) speed = -1;
		if(speed > 1) speed = 1;
		winchMotor.set(speed);
	}
}
