package frc.team2410.robot;

public class RobotMap
{
	//PID
	public static final float SWERVE_MODULE_P = 5;
	public static final float SWERVE_MODULE_I = 0;
	public static final float SWERVE_MODULE_D = 3;
	public static final float GYRO_P = 3;
	public static final float GYRO_I = 0;
	public static final float GYRO_D = 2;

	//CAN IDs
	public static final int FRONT_RIGHT_STEER = 1;
	public static final int FRONT_RIGHT_DRIVE = 2;
	public static final int FRONT_LEFT_DRIVE = 3;
	public static final int FRONT_LEFT_STEER = 4;
	public static final int BACK_LEFT_STEER = 5;
	public static final int PIGEON_IMU_SRX = 5;
	public static final int BACK_LEFT_DRIVE = 6;
	public static final int ARM_CIM = 7;
	public static final int BACK_RIGHT_DRIVE = 8;
	public static final int BACK_RIGHT_STEER = 9;
	public static final int PCM = 10;

	//PCM Addresses
	public static final int INTAKE_FORWARD = 5;
	public static final int INTAKE_BACKWARD = 7;
	public static final int INTAKE_KICK_FORWARD = 0;
	public static final int INTAKE_KICK_BACKWARD = 3;
	public static final int SHIFT_FORWARD = 2;
	public static final int SHIFT_BACKWARD = 1;
	
	//Inputs
	public static final int FR_STEER_ENCODER = 0;
	public static final int FL_STEER_ENCODER = 1;
	public static final int BR_STEER_ENCODER = 2;
	public static final int BL_STEER_ENCODER = 3;
	public static final int RANGE_FINDER = 4;

	public static final int WINCH_CIMCODER_A = 0;
	public static final int WINCH_CIMCODER_B = 1;
	public static final int DRIVE_CIMCODER_A = 2;
	public static final int DRIVE_CIMCODER_B = 3;

	//Offsets
	public static final float FL_OFFSET = 2.406005613f;
	public static final float FR_OFFSET = 0.731201097f;
	public static final float BL_OFFSET = 3.286132476f;
	public static final float BR_OFFSET = 1.716308418f;
	public static final float SONAR_CENTER = 0;

	public static final int CAMERA_BRIGHTNESS = 0;
	public static final int CAMERA_EXPOSURE = 25;
	public static final double GR = 1.2;
	public static final float GEAR_DISTANCE = 9.3f;
	//public static final int PI = 3.14159;
	public static final double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679; //memz
	public static final double WINCH_DIST_PER_PULSE = 3.0*PI/932.0;
	public static final double DRIVE_DIST_PER_PULSE = 3.0*PI/100.0;
}
