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

	//CAN
	public static final int FRONT_RIGHT_STEER = 1;
	public static final int FRONT_RIGHT_DRIVE = 2;
	public static final int FRONT_LEFT_STEER = 3;
	public static final int FRONT_LEFT_DRIVE = 4;
	public static final int PIGEON_IMU_SRX = 5;
	public static final int BACK_LEFT_STEER = 5;
	public static final int BACK_LEFT_DRIVE = 6;
	public static final int BACK_RIGHT_STEER = 7;
	public static final int BACK_RIGHT_DRIVE = 8;
	public static final int ELEVATOR_A = 9;
	public static final int ELEVATOR_B = 10;
	public static final int INTAKE_MOTOR_TOP = 11; //Tentative
	public static final int INTAKE_MOTOR_BOTTOM = 12; // Tentative
	public static final int WRIST_MOTOR = 14; // Tentative
	public static final int PCM = 15;
	
	//Analog In
	public static final int FR_STEER_ENCODER = 0;
	public static final int FL_STEER_ENCODER = 1;
	public static final int BR_STEER_ENCODER = 2;
	public static final int BL_STEER_ENCODER = 3;
	public static final int RANGE_FINDER = 4;

	//DIO
	public static final int WINCH_CIMCODER_A = 0; //Tentative
	public static final int WINCH_CIMCODER_B = 1; //Tentative
	public static final int DRIVE_CIMCODER_A = 2;
	public static final int DRIVE_CIMCODER_B = 3;
	public static final int WRIST_ENCODER_A = 4; //Tentative
	public static final int WRIST_ENCODER_B = 5; //Tentative
	public static final int CAMERA_LIGHT = 6; //Tentative
	
	//PCM
	public static final int HATCH_INTAKE_FORWARD = 0; //Tentative
	public static final int HATCH_INTAKE_REVERSE = 1; //Tentative
	public static final int CLIMB_PISTON_LEFT_FORWARD = 2; //Tentative
	public static final int CLIMB_PISTON_LEFT_REVERSE = 3; //Tentative
	public static final int CLIMB_PISTON_RIGHT_FORWARD = 4; //Tentative
	public static final int CLIMB_PISTON_RIGHT_REVERSE = 5; //Tentative
	
	//Offsets
	public static final float FL_OFFSET = 2.406005613f;
	public static final float FR_OFFSET = 0.731201097f;
	public static final float BL_OFFSET = 3.286132476f;
	public static final float BR_OFFSET = 1.716308418f;
	
	//Elevator Heights- ALL TENTATIVE
	public static final double BALL_INTAKE_HEIGHT = 0;
	public static final double HATCH_INTAKE_HEIGHT = 0;
	public static final double CARGOSHIP_BALL_HEIGHT = 0;
	public static final double CARGOSHIP_HATCH_HEIGHT = 0;
	public static final double ROCKET_BALL_LEVEL_ONE_HEIGHT = 0;
	public static final double ROCKET_HATCH_LEVEL_ONE_HEIGHT = 0;
	public static final double ROCKET_BALL_LEVEL_TWO_HEIGHT = 0;
	public static final double ROCKET_HATCH_LEVEL_TWO_HEIGHT = 0;
	
	//Wrist Angles- ALL TENTATIVE
	public static final double CARGO_WRIST_ANGLE = 0;
	public static final double HATCH_WRIST_ANGLE = 0;
	public static final double CLIMB_WRIST_ANGLE = 0;
	
	//public static final int PI = 3.1416;
	//public static final double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679; //memz
	public static final double DRIVE_DIST_PER_PULSE = 3.0*Math.PI/100.0;
	public static final double WINCH_DIST_PER_PULSE = 0; //Tentative
	public static final double WRIST_DEGREES_PER_PULSE = 0; //Tentative
}
