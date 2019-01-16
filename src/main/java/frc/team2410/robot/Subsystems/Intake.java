package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import static frc.team2410.robot.RobotMap.*;

class Intake {
	private WPI_TalonSRX intakeMotor;
	
	Intake() {
		intakeMotor = new WPI_TalonSRX(INTAKE_MOTOR);
	}
	
	void set(boolean in) {
		intakeMotor.set(in ? 1 : -1);
	}
	
	void stop() {
		intakeMotor.set(0);
	}
}
