package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putNumber;
import static frc.team2410.robot.RobotMap.*;

public class SwerveModule
{
	private WPI_TalonSRX drive;
	private float offset;
	private float currentSpeed;
	private PIDController pid;
	public AnalogInput positionEncoder;
	private boolean zeroing;

	SwerveModule(int steerMotor, int driveMotor, int encoder, float offset, boolean isInverted) {
		//this->steer->ConfigNeutralMode(TalonSRX::NeutralMode::kNeutralMode_Brake);
		this.offset = offset;
		this.drive = new WPI_TalonSRX(driveMotor);
		this.drive.setInverted(isInverted);
		this.positionEncoder = new AnalogInput(encoder);
		this.pid = new PIDController(SWERVE_MODULE_P, SWERVE_MODULE_I, SWERVE_MODULE_D, this.positionEncoder, new WPI_TalonSRX(steerMotor), 0.002);
		this.pid.setPercentTolerance(1);
		this.pid.setInputRange(0.0, 5.0);
		this.pid.setOutputRange(-1.0, 1.0);
		this.pid.setContinuous(true);
		this.pid.enable();

		currentSpeed = 0;
		zeroing = false;
	}

	void setPID(float p, float i, float d) {
		this.pid.setPID(p, i, d);
	}

	void drive(double speed, double setpoint) {
		speed = Math.abs(speed) > 0.1 ? speed : 0; // Buffer for speed
		setpoint /= 72;
		double currentPos = (this.positionEncoder.getVoltage() - offset + 5) % 5;
		double dist = setpoint - currentPos;

		// Converts 90-270 degrees to negative equivalents
		if (Math.abs(dist) > 1.25 && Math.abs(dist) < 3.75) {
			setpoint = (setpoint + 2.5) % 5;
			speed *= -1;
		}

		if (speed == 0 || Math.abs(speed - currentSpeed) > 1.f) {
			currentSpeed = 0;
		} else if (currentSpeed > speed) {
			currentSpeed -= 0.04;
		} else if (currentSpeed < speed) {
			currentSpeed += 0.04;
		}

		putNumber("Distance", dist);

		// Buffer for zeroing
		if (this.getAngle() < 1 || this.getAngle() > 359) {
			zeroing = false;
		}

		if (!zeroing) {
			this.pid.setSetpoint((setpoint + offset) % 5); // Wraps output between 0V-5V
		}
		this.drive.set(currentSpeed);
	}

	void returnToZero() {
		this.pid.setSetpoint(offset);
		SmartDashboard.putNumber("Setpoint", this.pid.getSetpoint());
		zeroing = true;
	}

	public double getAngle() {
		return (this.positionEncoder.getVoltage() - offset + 5) % 5 * 72.f;
	} // Wraps value between 0-5 Volts and then converts it to Degrees
}