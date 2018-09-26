package frc.team2410.robot.Subsystems;

import edu.wpi.first.wpilibj.PIDSourceType;
import frc.team2410.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

public class PigeonNav
{
	private PigeonIMU gyro;
	private double[] ypr;

	public PigeonNav() {
		this.gyro = new PigeonIMU(new TalonSRX(RobotMap.PIGEON_IMU_SRX));
		this.ypr = new double[3];
		this.resetHeading();
	}

	public double getPID() {
		return this.getHeading();
	}

	//not sure how this was written; possibly unused
	public void setPIDSourceType(PIDSourceType pidSource) {
		if (pidSource == PIDSourceType.kDisplacement) {
			//m_pidSource = pidSource;
		}
	}

	public double getHeading() {
		double angle = (this.gyro.getFusedHeading() % 360.0 + 360.0) % 360.0;
		return angle <= 180 ? angle : angle - 360;
	}

	public double getAngularRate() {
		this.gyro.getRawGyro(this.ypr);
		return ypr[0];
	}

	public void resetHeading() {
		this.gyro.setFusedHeading(0, 20);
	}

	PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
}
