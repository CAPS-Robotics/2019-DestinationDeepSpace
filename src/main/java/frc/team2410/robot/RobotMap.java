package frc.team2410.robot;

public class RobotMap
{
	//PID
	public static final float SWERVE_MODULE_P = 5;
	public static final float SWERVE_MODULE_I = 0;
	public static final float SWERVE_MODULE_D = 3;
	public static final double GYRO_P = 0.01;
	public static final double GYRO_I = 0;
	public static final double GYRO_D = 0;

	//CAN IDs
	public static final int FRONT_RIGHT_STEER = 1;
	public static final int FRONT_RIGHT_DRIVE = 2;
	public static final int FRONT_LEFT_DRIVE = 3;
	public static final int FRONT_LEFT_STEER = 4;
	public static final int BACK_LEFT_STEER = 5;
	public static final int PIGEON_IMU_SRX = 5;
	public static final int BACK_LEFT_DRIVE = 6;
	public static final int BACK_RIGHT_DRIVE = 8;
	public static final int BACK_RIGHT_STEER = 9;
	
	//Inputs
	public static final int FR_STEER_ENCODER = 0;
	public static final int FL_STEER_ENCODER = 1;
	public static final int BR_STEER_ENCODER = 2;
	public static final int BL_STEER_ENCODER = 3;
	public static final int RANGE_FINDER = 4;

	public static final int DRIVE_CIMCODER_A = 2;
	public static final int DRIVE_CIMCODER_B = 3;

	//Offsets
	public static final float FL_OFFSET = 2.406005613f;
	public static final float FR_OFFSET = 0.731201097f;
	public static final float BL_OFFSET = 3.286132476f;
	public static final float BR_OFFSET = 1.716308418f;
	
	//public static final int PI = 3.14159;
	public static final double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679; //memz
	public static final double DRIVE_DIST_PER_PULSE = 3.0*PI/100.0;
}
