package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.team2410.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

public class PigeonNav implements PIDSource
{
	private PigeonIMU gyro;
	private double[] ypr;
	private int offset;
	
	//TODO: Make PID functions switch between rate and displacement instead of forcing displacement

	public PigeonNav() {
		this.gyro = new PigeonIMU(new TalonSRX(RobotMap.PIGEON_IMU_SRX));
		this.ypr = new double[3];
		this.resetHeading(0);
	}
	
	@Override
	public double pidGet() {
		return this.getHeading();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {}

	public double getHeading() {
		double angle = (((this.gyro.getFusedHeading()-offset)%360.0)+360)%360; // Wraps angle between -360:360, changes negative values to equivalent postive values (ex. -90 -> 270 degrees) (changing the range to 0:360)
		return angle <= 180 ? angle : angle - 360;  // Changes >180 Degrees to Neg Equivalent (ex. 270 -> -90) (changing the range to -180:180) and returns it 
	}
	
	public double getAngularRate() {
		this.gyro.getRawGyro(this.ypr);
		return ypr[0];
	}

	public void resetHeading(int head) {
		this.gyro.setFusedHeading(0, 20);
		this.offset = head;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
}
