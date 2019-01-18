package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team2410.robot.TalonPair;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import static frc.team2410.robot.RobotMap.*;

class Intake {
	TalonPair talonPair;
	private DoubleSolenoid solenoid;
	private boolean open = false;
	
	Intake() {
		talonPair = new TalonPair(INTAKE_MOTOR_TOP, INTAKE_MOTOR_BOTTOM, false, false);
		solenoid = new DoubleSolenoid(HATCH_INTAKE_FORWARD, HATCH_INTAKE_REVERSE, PCM);
	}
	
	void setWheel(boolean in) {
		talonPair.set(open ? 1 : -1);
	}
	
	void stop() {
		talonPair.set(0);
	}
	
	void togglePiston() {
		open = !open;
		solenoid.set(!open ? kForward : kReverse);
	}
}
