package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team2410.robot.TalonPair;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import static frc.team2410.robot.RobotMap.*;

class Intake {
	TalonPair wheels;
	private DoubleSolenoid solenoid;
	private boolean open = false;
	private WPI_TalonSRX wrist;
	
	Intake() {
		wheels = new TalonPair(INTAKE_MOTOR_TOP, INTAKE_MOTOR_BOTTOM, false, false);
		solenoid = new DoubleSolenoid(PCM, HATCH_INTAKE_FORWARD, HATCH_INTAKE_REVERSE);
		wrist = new WPI_TalonSRX(WRIST_MOTOR);
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
}
