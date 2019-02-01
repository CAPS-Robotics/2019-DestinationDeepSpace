package frc.team2410.robot;

public class RobotMap
{
	//TODO: Tentative values
	
	//PID
	public static final float SWERVE_MODULE_P = 5;
	public static final float SWERVE_MODULE_I = 0;
	public static final float SWERVE_MODULE_D = 3;
	public static final double GYRO_P = 0.01;
	public static final double GYRO_I = 0;
	public static final double GYRO_D = 0;

	//CAN
	public static final int BACK_LEFT_STEER = 1;
	public static final int BACK_LEFT_DRIVE = 2;
	public static final int BACK_RIGHT_STEER = 3;
	public static final int BACK_RIGHT_DRIVE = 4;
	public static final int PIGEON_IMU_SRX = 5;
	public static final int FRONT_RIGHT_STEER = 5;
	public static final int FRONT_RIGHT_DRIVE = 6;
	public static final int FRONT_LEFT_STEER = 7;
	public static final int FRONT_LEFT_DRIVE = 8;
	public static final int ELEVATOR_A = 9;
	public static final int ELEVATOR_B = 10;
	public static final int INTAKE_MOTOR = 11;  //TODO change Ids in Phoenix tuner
	public static final int WRIST_MOTOR = 12;
	public static final int PCM = 13;
	
	//Analog In
	public static final int BL_STEER_ENCODER = 0;
	public static final int BR_STEER_ENCODER = 1;
	public static final int FL_STEER_ENCODER = 2;
	public static final int FR_STEER_ENCODER = 3;
	public static final int RANGE_FINDER = 5;
	public static final int WRIST_ENCODER = 4; //Tentative
	
	//DIO
	public static final int WINCH_ENCODER_A= 0;
	public static final int WINCH_ENCODER_B = 1;
	public static final int DRIVE_CIMCODER_A = 2;
	public static final int DRIVE_CIMCODER_B = 3;
	public static final int CAMERA_LIGHT = 4; //Tentative
	
	//PCM
	public static final int HATCH_INTAKE_FORWARD = 0; //Tentative
	public static final int HATCH_INTAKE_REVERSE = 1; //Tentative
	public static final int CLIMB_PISTON_LEFT_FORWARD = 2; //Tentative
	public static final int CLIMB_PISTON_LEFT_REVERSE = 3; //Tentative
	public static final int CLIMB_PISTON_RIGHT_FORWARD = 4; //Tentative
	public static final int CLIMB_PISTON_RIGHT_REVERSE = 5; //Tentative
	
	//Offsets
	public static final float BR_OFFSET = 2.406005613f;
	public static final float BL_OFFSET = 0.731201097f;
	public static final float FR_OFFSET = 3.286132476f;
	public static final float FL_OFFSET = 1.716308418f;
	public static final float WRIST_OFFSET = 0f; //Tentative
	
	//Elevator Heights- ALL TENTATIVE
	public static final double CARGO_INTAKE_HEIGHT = 0; //Tentative but close
	public static final double CARGO_SHIP_HEIGHT = 0; //Tentative
	public static final double HATCH_INTAKE_HEIGHT = 7;
	public static final double[] CARGO_HEIGHT = {0, 0, 0}; //Tentative
	public static final double[] HATCH_HEIGHT = {9, 37, 65};
	public static final double CLIMB_HEIGHT = 0; //Tentative
	
	//Wrist Angles- ALL TENTATIVE
	public static final double CARGO_WRIST_ANGLE = 0; //Tentative
	public static final double HATCH_WRIST_ANGLE = 0;
	public static final double CLIMB_WRIST_ANGLE = 0; //Tentative
	public static final double WRIST_UP = 90; //Tentative
	
	//Field Angles
	public static final double CARGO_SHIP_FRONT = 0;
	public static final double ROCKET_LEFT_FRONT = -90;
	public static final double ROCKET_LEFT_LEFT = -90.0 + 61.25;
	public static final double ROCKET_LEFT_RIGHT = -90 - 61.25;
	public static final double ROCKET_RIGHT_FRONT = 90;
	public static final double ROCKET_RIGHT_LEFT = 90.0 + 61.25;
	public static final double ROCKET_RIGHT_RIGHT = 90 - 61.25;
	
	//Field Distances
	public static final double CARGO_DISTANCE = 10; //Tentative
	public static final double HATCH_DISTANCE = 1; //Tentative
	public static final double STATION_DISTANCE = 18;
	public static final double HABITAT_DISTANCE = 10; //Tentative
	public static final double CLIMB_WALL_DISTANCE = 10; //Tentative
	public static final double WALL_DISTANCE = 5; //Tentative
	
	//Encoder Conversions
	public static final double DRIVE_DIST_PER_PULSE = 3.0*Math.PI/100.0;
	public static final double WINCH_DIST_PER_PULSE = 1.91 * Math.PI * 2 / 39 / 3; //Tentative
	public static final double WRIST_DEGREES_PER_PULSE = 0; //Tentative
	
	//Vision
	public static final int CAMERA_WIDTH = 360; // Tentative
	public static final double SONAR_VOLTS_PER_INCH = 0.012446;
	
	//public static final int PI = 3.1416;
	//public static final double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679; //memz
}
