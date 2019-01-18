package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import static frc.team2410.robot.RobotMap.*;

public class Climb {
	private DoubleSolenoid pistonLeft;
	private DoubleSolenoid pistonRight;
	
	public Climb() {
		pistonLeft = new DoubleSolenoid(CLIMB_PISTON_LEFT_FORWARD, CLIMB_PISTON_LEFT_REVERSE, PCM);
		pistonRight = new DoubleSolenoid(CLIMB_PISTON_RIGHT_FORWARD, CLIMB_PISTON_RIGHT_REVERSE, PCM);
	}
	
	public void set(boolean out) {
		pistonLeft.set(out ? kForward : kReverse);
		pistonRight.set(out ? kForward : kReverse);
	}
}
