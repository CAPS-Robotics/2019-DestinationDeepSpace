package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import static frc.team2410.robot.RobotMap.*;

class Intake {
	private WPI_TalonSRX wheelMotor;
	private DoubleSolenoid solenoid;
	private boolean open = false;
	
	Intake() {
		wheelMotor = new WPI_TalonSRX(INTAKE_MOTOR);
		solenoid = new DoubleSolenoid(HATCH_INTAKE_FORWARD, HATCH_INTAKE_REVERSE, PCM);
	}
	
	void setWheel(boolean in) {
		wheelMotor.set(in ? 1 : -1);
	}
	
	void stop() {
		wheelMotor.set(0);
	}
	
	void togglePiston() {
		open = !open;
		solenoid.set(!open ? kForward : kReverse);
	}
}
