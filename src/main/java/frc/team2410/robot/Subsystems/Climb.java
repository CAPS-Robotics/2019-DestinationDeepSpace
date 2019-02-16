package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import static frc.team2410.robot.RobotMap.*;

public class Climb {
	private DoubleSolenoid piston;
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
	}
}
