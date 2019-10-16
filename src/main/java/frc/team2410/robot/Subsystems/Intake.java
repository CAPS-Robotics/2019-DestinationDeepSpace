package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import frc.team2410.robot.Robot;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import static frc.team2410.robot.RobotMap.*;

public class Intake {
	WPI_TalonSRX wheels;
	private DoubleSolenoid solenoid;
	private WPI_TalonSRX wrist;
	private AnalogInput wristEncoder;
	private PIDController pid;
	private boolean open = true;
	
	public Intake() {
		wheels = new WPI_TalonSRX(INTAKE_MOTOR);
		solenoid = new DoubleSolenoid(PCM, HATCH_INTAKE_FORWARD, HATCH_INTAKE_REVERSE);
		wrist = new WPI_TalonSRX(WRIST_MOTOR);
		wheels.setInverted(true);
		wrist.setInverted(true);
		wristEncoder = new AnalogInput(WRIST_ENCODER);
		pid = new PIDController(WRIST_P, WRIST_I, WRIST_D, wristEncoder, wrist, 0.002);
		pid.setPercentTolerance(1);
		pid.setInputRange(0, 5);
		pid.setOutputRange(-1, 1);
		pid.setContinuous(true);
		pid.enable();
		
		pid.setSetpoint(getVoltage());
	}
	
	// intake section
	public void setIntake(boolean in) {
		wheels.set(in ? 1 : -1);
	}
	
	public void stopIntake() {
		wheels.set(0);
	}
	
	// hatch section
	public void setHatch(boolean open) { solenoid.set(!open ? kForward : kReverse); }
	
	public void toggleHatch() {
		open = !open;
		setHatch(open);
	}
	
	public boolean getHatchStatus() { return open; }
	
	// wrist section
	void setWrist(double speed) {
		wrist.set(speed);
	}
	
	public void moveWristTo(double angle) {
		pid.setSetpoint(getVoltage(angle));
	}
	
	public double getWristTarget() {
		return getAngle(pid.getSetpoint());
	}
	
	public double getAngle() {
		double angle = ((((wristEncoder.getVoltage() - WRIST_OFFSET)*(360.0/5)*(40.0/24))%360.0)+360)%360; // Wraps angle between -360:360, changes negative values to equivalent postive values (ex. -90 -> 270 degrees) (changing the range to 0:360)
		return angle <= 180 ? angle : angle - 360;  // Changes >180 Degrees to Neg Equivalent (ex. 270 -> -90) (changing the range to -180:180) and returns it
	}
	
	public double getAngle(double voltage) {
		double angle = ((((voltage - WRIST_OFFSET)*(360.0/5)*(40.0/24))%360.0)+360)%360; // Wraps angle between -360:360, changes negative values to equivalent postive values (ex. -90 -> 270 degrees) (changing the range to 0:360)
		return angle <= 180 ? angle : angle - 360;  // Changes >180 Degrees to Neg Equivalent (ex. 270 -> -90) (changing the range to -180:180) and returns it
	}
	
	public double getVoltage(double angle) {
		return angle / ((360.0/5)*(40.0/24)) + WRIST_OFFSET;
	}
	
	public double getVoltage() { return wristEncoder.getVoltage(); }
	
	public double getWristCurrent() { return wrist.getOutputCurrent(); }
	
	public void loop() {
		double wristStick = Robot.oi.getAnalogStick(false, true);
		if(wristStick != 0) {
			pid.disable();
			setWrist(-wristStick);
			pid.setSetpoint(getVoltage());
		} else {
			pid.enable();
		}
	}
}