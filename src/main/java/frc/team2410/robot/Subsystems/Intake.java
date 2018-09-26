package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team2410.robot.RobotMap;

public class Intake
{
	private DoubleSolenoid grip;
	private DoubleSolenoid kick;

	public Intake() {
		grip = new DoubleSolenoid(RobotMap.PCM, RobotMap.INTAKE_FORWARD, RobotMap.INTAKE_BACKWARD);
		kick = new DoubleSolenoid(RobotMap.PCM, RobotMap.INTAKE_KICK_FORWARD, RobotMap.INTAKE_KICK_BACKWARD);
	}

	boolean setState(boolean closed) {
		if (closed) {
			grip.set(DoubleSolenoid.Value.kReverse);
		}
		else {
			grip.set(DoubleSolenoid.Value.kForward);
		}
		return closed;
	}

	boolean setKicked(boolean forward) {
		if (forward) {
			kick.set(DoubleSolenoid.Value.kReverse);
		}
		else {
			kick.set(DoubleSolenoid.Value.kForward);
		}
		return forward;
	}
}
