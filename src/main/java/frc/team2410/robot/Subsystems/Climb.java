package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import frc.team2410.robot.Robot;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import static frc.team2410.robot.RobotMap.*;

public class Climb {
	/*private DoubleSolenoid piston;
	boolean out = false;
	
	public Climb() {
		piston = new DoubleSolenoid(PCM, CLIMB_PISTON_FORWARD, CLIMB_PISTON_REVERSE);
	}
	
	public void set(boolean out) {
		this.out = out;
		piston.set(out ? kForward : kReverse);
	}
	public void toggle() {
		out = !out;
		piston.set(out ? kForward : kReverse);
	}*/
	
	private WPI_TalonSRX winchMotor;
	private Encoder heightEncoder;
	
	private double targetHeight;
	private double offset;
	
	public Climb() {
		winchMotor = new WPI_TalonSRX(CLIMB_ELEVATOR);
		heightEncoder = new Encoder(CLIMB_ELEVATOR_A, CLIMB_ELEVATOR_B);
		heightEncoder.setDistancePerPulse(WINCH_CLIMB_DIST_PER_PULSE);
		heightEncoder.reset();
		targetHeight = heightEncoder.get();
	}
	
	public void moveTo(double height) { targetHeight = height; }
	
	public double getPosition() { return heightEncoder.getDistance() + offset; }
	
	public double getTarget() { return targetHeight; }
	
	public void reset(double height) {
		heightEncoder.reset();
		offset = height;
		targetHeight = height;
	}
	
	public void loop() {
		if(Robot.oi.getJoyPOV() != 0 && Robot.oi.getJoyPOV() != 180) {
			double speed = -(targetHeight-getPosition());
			if(speed > 0) speed /= 15;
			if(speed < -1) speed = -1;
			if(speed > 1) speed = 1;
			winchMotor.set(speed);
		} else {
			winchMotor.set(Robot.oi.getJoyPOV() == 0  ? Robot.oi.getSlider() : -Robot.oi.getSlider());
			targetHeight = getPosition();
		}
	}
	
	public double getVoltage() {
		return winchMotor.getOutputCurrent();
	}
}
