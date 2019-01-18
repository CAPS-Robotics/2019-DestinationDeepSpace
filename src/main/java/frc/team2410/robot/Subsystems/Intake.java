package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import static frc.team2410.robot.RobotMap.*;

class Intake {
	private WPI_TalonSRX wheelMotorTop;
	private WPI_TalonSRX wheelMotorBottom;
	private DoubleSolenoid solenoid;
	private boolean open = false;
	
	Intake() {
		wheelMotorTop = new WPI_TalonSRX(INTAKE_MOTOR_BOTTOM);
		wheelMotorBottom = new WPI_TalonSRX(INTAKE_MOTOR_TOP);
		solenoid = new DoubleSolenoid(HATCH_INTAKE_FORWARD, HATCH_INTAKE_REVERSE, PCM);
	}
	
	void setWheel(boolean in) {
		int speed = open ? 1 : -1;
		wheelMotorTop.set(speed);
		wheelMotorBottom.set(-speed);
	}
	
	void stop() {
		wheelMotorTop.set(0);
		wheelMotorBottom.set(0);
	}
	
	void togglePiston() {
		open = !open;
		solenoid.set(!open ? kForward : kReverse);
	}
}
