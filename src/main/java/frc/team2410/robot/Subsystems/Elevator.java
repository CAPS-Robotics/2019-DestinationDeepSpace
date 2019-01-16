package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import frc.team2410.robot.RobotMap;

public class Elevator {
	private WPI_TalonSRX winchMotor;
	private Encoder heightEncoder;
	
	private double targetHeight;
	
	Elevator(){
		winchMotor = new WPI_TalonSRX(RobotMap.WINCH_CIM);
		heightEncoder = new Encoder(RobotMap.WINCH_CIMCODER_A, RobotMap.WINCH_CIMCODER_B);
		heightEncoder.setDistancePerPulse(RobotMap.WINCH_DIST_PER_PULSE);
		heightEncoder.reset();
	}
	
	public void setTargetHeight(double height){
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
}
