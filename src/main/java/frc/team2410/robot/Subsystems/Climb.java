package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import static frc.team2410.robot.RobotMap.*;

public class Climb {
	private DoubleSolenoid pistonLeft;
	private DoubleSolenoid pistonRight;
	boolean out = false;
	
	public Climb() {
		pistonLeft = new DoubleSolenoid(PCM, CLIMB_PISTON_LEFT_FORWARD, CLIMB_PISTON_LEFT_REVERSE);
		pistonRight = new DoubleSolenoid(PCM, CLIMB_PISTON_RIGHT_FORWARD, CLIMB_PISTON_RIGHT_REVERSE);
	}
	
	public void set(boolean out) {
		this.out = out;
		pistonLeft.set(out ? kForward : kReverse);
		pistonRight.set(out ? kForward : kReverse);
	}
	public void toggle() {
		out = !out;
		pistonLeft.set(out ? kForward : kReverse);
		pistonRight.set(out ? kForward : kReverse);
	}
}
