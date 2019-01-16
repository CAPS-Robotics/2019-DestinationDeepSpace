package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import static frc.team2410.robot.RobotMap.*;

public class Elevator {
	private WPI_TalonSRX winchMotor;
	private Encoder heightEncoder;
	
	private Intake intake;
	
	private double targetHeight;
	
	public Elevator(){
		intake = new Intake();
		winchMotor = new WPI_TalonSRX(WINCH_CIM);
		heightEncoder = new Encoder(WINCH_CIMCODER_A, WINCH_CIMCODER_B);
		heightEncoder.setDistancePerPulse(WINCH_DIST_PER_PULSE);
		heightEncoder.reset();
	}
	
	public void moveTo(double height){
		this.targetHeight = height;
	}
	
	public double getPosition() {
		return heightEncoder.getDistance();
	}
	
	public void loop(){
		double aspeed;
		aspeed = ((targetHeight - getPosition()) / 10);
		if(Math.abs(targetHeight - getPosition()) < 1)
			aspeed = 0;
		if(aspeed < -1)
			aspeed = -1;
		if(aspeed > 1)
			aspeed = 1;
		winchMotor.set(aspeed);
	}
	
	public void setIntake(boolean in) {
		intake.set(in);
	}
	public void stopIntake() {
		intake.stop();
	}
}
