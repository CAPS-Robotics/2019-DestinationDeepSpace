package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.team2410.robot.RobotMap;

public class Intake {
	private WPI_TalonSRX intakeMotor;
	private boolean isIntakeIn = true;
	private boolean isIntakeActive = false;
	
	Intake(){
		intakeMotor = new WPI_TalonSRX(RobotMap.INTAKE_MOTOR);
	}
	
	public void loop(){
		if(isIntakeActive){
			if(isIntakeIn){
				intakeMotor.set(1);
			} else{
				intakeMotor.set(-1);
			}
		} else{
			intakeMotor.set(0);
		}
	}
	
	public void setIntakeIn(boolean intakeIn) {
		isIntakeIn = intakeIn;
	}

	public void setIntakeActive(boolean intakeActive) {
		isIntakeActive = intakeActive;
	}
}
