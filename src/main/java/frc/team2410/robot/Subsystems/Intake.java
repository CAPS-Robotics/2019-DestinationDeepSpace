package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import frc.team2410.robot.TalonPair;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import static frc.team2410.robot.RobotMap.*;

class Intake {
	TalonPair wheels;
	private DoubleSolenoid solenoid;
	private boolean open = false;
	private WPI_TalonSRX wrist;
	private Encoder wristEncoder;
	
	private double wristOffset;
	
	Intake() {
		wheels = new TalonPair(INTAKE_MOTOR_A, INTAKE_MOTOR_B, false, true);
		solenoid = new DoubleSolenoid(PCM, HATCH_INTAKE_FORWARD, HATCH_INTAKE_REVERSE);
		wrist = new WPI_TalonSRX(WRIST_MOTOR);
		wrist.setInverted(true);
		wristEncoder = new Encoder(WRIST_ENCODER_A, WRIST_ENCODER_B);
		wristEncoder.setDistancePerPulse(WRIST_DEGREES_PER_PULSE);
	}
	
	void setWheel(boolean in) {
		wheels.set(in ? 1 : -1);
	}
	
	void stop() {
		wheels.set(0);
	}
	
	void togglePiston() {
		open = !open;
		solenoid.set(!open ? kForward : kReverse);
	}
	
	void setWrist(double speed) {
		wrist.set(speed);
	}
	
	void resetWrist(double angle) {
		wristEncoder.reset();
		wristOffset = angle;
	}
	
	double getWrist() {
		return (((wristEncoder.get()-wristOffset)%360.0)+360)%360; // Wraps angle between -360:360, changes negative values to equivalent postive values (ex. -90 -> 270 degrees) (changing the range to 0:360)
	}
}
