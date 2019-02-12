package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2410.robot.TalonPair;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import static frc.team2410.robot.RobotMap.*;

public class Intake {
	WPI_TalonSRX wheels;
	private DoubleSolenoid solenoid;
	private WPI_TalonSRX wrist;
	private AnalogInput wristEncoder;
	/*private Counter hCounter;
	private Counter lCounter;*/
	private double pval = -1;
	public double rollover = 0;
	
	Intake() {
		wheels = new WPI_TalonSRX(INTAKE_MOTOR);
		solenoid = new DoubleSolenoid(PCM, HATCH_INTAKE_FORWARD, HATCH_INTAKE_REVERSE);
		wrist = new WPI_TalonSRX(WRIST_MOTOR);
		wheels.setInverted(true);
		wrist.setInverted(true);
		wristEncoder = new AnalogInput(WRIST_ENCODER);
		/*hCounter = new Counter(wristEncoder);
		lCounter = new Counter(wristEncoder);
		hCounter.setSemiPeriodMode(true);
		lCounter.setSemiPeriodMode(false);*/
	}
	
	void setWheel(boolean in) {
		wheels.set(in ? 1 : -1);
	}
	
	void stop() {
		wheels.set(0);
	}
	
	void setPiston(boolean open) {
		solenoid.set(!open ? kForward : kReverse);
	}
	
	void setWrist(double speed) {
		wrist.set(speed);
	}
	
	double getWrist() {
		/*double hPulse = hCounter.getPeriod();
		double lPulse = lCounter.getPeriod();
		double dutyCycle = hPulse/(hPulse+lPulse);
		if(pval > -1) {
			if(dutyCycle - pval >= 0.5) {
				rollover--;
			} else if(dutyCycle - pval <= -0.5) {
				rollover++;
			}
		}
		pval = dutyCycle;*/
		
		double angle = (((-(rollover+wristEncoder.getVoltage() - WRIST_OFFSET)*(360.0/5)*(40.0/24))%360.0)+360)%360; // Wraps angle between -360:360, changes negative values to equivalent postive values (ex. -90 -> 270 degrees) (changing the range to 0:360)
		return angle <= 180 ? angle : angle - 360;  // Changes >180 Degrees to Neg Equivalent (ex. 270 -> -90) (changing the range to -180:180) and returns it
	}
}
