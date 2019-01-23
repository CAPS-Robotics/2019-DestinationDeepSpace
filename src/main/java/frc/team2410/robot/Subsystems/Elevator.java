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
	private double wristAngle;
	private double offset;
	
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
	
	public void moveWristTo(double angle) {
		wristAngle = angle;
	}
	
	public double getPosition() {
		return heightEncoder.getDistance() + offset;
	}
	
	public void reset(double height) {
		heightEncoder.reset();
		offset = height;
	}
	
	public void resetWrist(double angle) {
		intake.resetWrist(angle);
	}
	
	public void loop() {
		if(Robot.oi.getAnalogStick(true, true) == 0) {
			double speed = ((targetHeight-getPosition())/10);
			if(Math.abs(targetHeight-getPosition()) < 1) speed = 0;
			if(speed < -1) speed = -1;
			if(speed > 1) speed = 1;
			winchMotor.set(speed);
		} else {
			winchMotor.set(Robot.oi.getAnalogStick(true, true));
			targetHeight = getPosition();
		}
		if(Robot.oi.getAnalogStick(false, true) == 0) {
			double speed = ((wristAngle-intake.getWrist())/10);
			if(Math.abs(wristAngle-intake.getWrist()) < 1) speed = 0;
			if(speed < -1) speed = -1;
			if(speed > 1) speed = 1;
			intake.setWrist(speed);
		} else {
			intake.setWrist(Robot.oi.getAnalogStick(false, true));
			wristAngle = intake.getWrist();
		}
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
