package frc.team2410.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putNumber;
import static frc.team2410.robot.RobotMap.SWERVE_MODULE_D;
import static frc.team2410.robot.RobotMap.SWERVE_MODULE_I;
import static frc.team2410.robot.RobotMap.SWERVE_MODULE_P;

public class SwerveModule
{
	WPI_TalonSRX drive;
	private float offset;
	private float currentSpeed;
	WPI_TalonSRX steer;
	PIDController pid;
	public AnalogInput positionEncoder;
	boolean zeroing;

	public SwerveModule(int steerMotor, int driveMotor, int encoder, float offset, boolean isInverted) {
		this.steer = new WPI_TalonSRX(steerMotor);
		//this->steer->ConfigNeutralMode(TalonSRX::NeutralMode::kNeutralMode_Brake);
		this.offset = offset;
		this.drive = new WPI_TalonSRX(driveMotor);
		this.drive.setInverted(isInverted);
		this.positionEncoder = new AnalogInput(encoder);
		this.pid = new PIDController(SWERVE_MODULE_P, SWERVE_MODULE_I, SWERVE_MODULE_D, this.positionEncoder, this.steer, 0.002);
		this.pid.setContinuous(true);
		this.pid.setPercentTolerance(1);
		this.pid.setInputRange(0.0, 5.0);
		this.pid.setOutputRange(-1.0, 1.0);
		this.pid.enable();

		currentSpeed = 0;
		zeroing = false;
	}

	public void initDefaultCommand() {

	}

	public void setPID(float p, float i, float d) {
		this.pid.setPID(p, i, d);
	}

	public void drive(double speed, double setpoint) {
		speed = Math.abs(speed) > 0.1 ? speed : 0; // Buffer for speed
		setpoint /= 72.f;
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
			this.pid.setSetpoint((setpoint + offset) % 5);
		}
		this.drive.set(currentSpeed);
	}

	void returnToZero() {
		this.pid.setSetpoint(offset);
		SmartDashboard.putNumber("Setpoint", this.pid.GetSetpoint());
		zeroing = true;
	}

	public double getAngle() {
		return (this.positionEncoder.getVoltage() - offset + 5) % 5 * 72.f;
	}
}