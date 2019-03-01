package frc.team2410.robot;

public class RobotMap
{
	public static final boolean COMPETITION_BOT = false;
	
	//PID
	public static final float SWERVE_MODULE_P = 5;
	public static final float SWERVE_MODULE_I = 0;
	public static final float SWERVE_MODULE_D = 3;
	public static final double GYRO_P = .03;
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
	public static final int INTAKE_MOTOR = 11;
	public static final int WRIST_MOTOR = 12;
	public static final int PCM = 13;
	public static final int CLIMB_ELEVATOR = 14;
	
	//Analog In
	public static final int BL_STEER_ENCODER = 0;
	public static final int BR_STEER_ENCODER = 1;
	public static final int FL_STEER_ENCODER = 2;
	public static final int FR_STEER_ENCODER = 3;
	public static final int WRIST_ENCODER = 4;
	
	//DIO
	public static final int ELEVATOR_ENCODER_A_COMP = 9;
	public static final int ELEVATOR_ENCODER_B_COMP = 8;
	public static final int DRIVE_CIMCODER_A_COMP = 0;
	public static final int DRIVE_CIMCODER_B_COMP = 1;
	public static final int CLIMB_ELEVATOR_A_COMP = 2; //Tentative
	public static final int CLIMB_ELEVATOR_B_COMP = 3; //Tentative
	public static final int ELEVATOR_ENCODER_A_PRAC = 0;
	public static final int ELEVATOR_ENCODER_B_PRAC = 1;
	public static final int DRIVE_CIMCODER_A_PRAC = 2;
	public static final int DRIVE_CIMCODER_B_PRAC = 3;
	public static final int CLIMB_ELEVATOR_A_PRAC = 6;
	public static final int CLIMB_ELEVATOR_B_PRAC = 9;
	public static final int ELEVATOR_ENCODER_A = COMPETITION_BOT ? ELEVATOR_ENCODER_A_COMP : ELEVATOR_ENCODER_A_PRAC;
	public static final int ELEVATOR_ENCODER_B = COMPETITION_BOT ? ELEVATOR_ENCODER_B_COMP : ELEVATOR_ENCODER_B_PRAC;
	public static final int DRIVE_CIMCODER_A = COMPETITION_BOT ? DRIVE_CIMCODER_A_COMP : DRIVE_CIMCODER_A_PRAC;
	public static final int DRIVE_CIMCODER_B = COMPETITION_BOT ? DRIVE_CIMCODER_B_COMP : DRIVE_CIMCODER_B_PRAC;
	public static final int CLIMB_ELEVATOR_A = COMPETITION_BOT ? CLIMB_ELEVATOR_A_COMP : CLIMB_ELEVATOR_A_PRAC;
	public static final int CLIMB_ELEVATOR_B = COMPETITION_BOT ? CLIMB_ELEVATOR_B_COMP : CLIMB_ELEVATOR_B_PRAC;
	
	//PCM
	public static final int HATCH_INTAKE_FORWARD = 1;
	public static final int HATCH_INTAKE_REVERSE = 0;
	public static final int CLIMB_PISTON_FORWARD = 7;
	public static final int CLIMB_PISTON_REVERSE = 6;
	
	//Offsets
	public static final float BR_OFFSET_PRAC = 2.369384523f;
	public static final float BL_OFFSET_PRAC = 0.731201097f;
	public static final float FR_OFFSET_PRAC = 3.286132476f;
	public static final float FL_OFFSET_PRAC = 1.716308418f;
	public static final float WRIST_OFFSET_PRAC = 2.7514645620000002f;
	public static final float BR_OFFSET_COMP = 2.4316403760000003f;
	public static final float BL_OFFSET_COMP = 1.8701169960000001f;
	public static final float FR_OFFSET_COMP = 3.355712547f;
	public static final float FL_OFFSET_COMP = 3.702392199f;
	public static final float WRIST_OFFSET_COMP = 3.0639645300000002f;
	public static final float BR_OFFSET = COMPETITION_BOT ? BR_OFFSET_COMP : BR_OFFSET_PRAC;
	public static final float BL_OFFSET = COMPETITION_BOT ? BL_OFFSET_COMP : BL_OFFSET_PRAC;
	public static final float FR_OFFSET = COMPETITION_BOT ? FR_OFFSET_COMP : FR_OFFSET_PRAC;
	public static final float FL_OFFSET = COMPETITION_BOT ? FL_OFFSET_COMP : FL_OFFSET_PRAC;
	public static final float WRIST_OFFSET = COMPETITION_BOT ? WRIST_OFFSET_COMP : WRIST_OFFSET_PRAC;
	
	//Elevator Heights
	public static final double TRAVEL_HEIGHT = 2;
	public static final double INTAKE_HEIGHT = 5;
	public static final double[] PLACE_HEIGHT = {9, 38, 60};
	public static final double[] CLIMB_HEIGHT = {7, 20};
	public static final int CLIMB_OFFSET = 3; //VERY TENTATIVE
	
	//Wrist Angles- ALL TENTATIVE
	public static final double CARGO_WRIST_ANGLE = 55;
	public static final double HATCH_WRIST_ANGLE = 5;
	public static final double HATCH_LEVEL_THREE_WRIST = 14;
	public static final double[] CLIMB_WRIST_ANGLE = {0, -30};
	public static final double WRIST_UP = 85;
	public static final double TRAVEL_ANGLE = 50;
	
	//Field Angles
	public static final double CARGO_SHIP_FRONT = 0;
	public static final double ROCKET_LEFT_FRONT = -90;
	public static final double ROCKET_LEFT_LEFT = -90.0 + 61.25;
	public static final double ROCKET_LEFT_RIGHT = -90 - 61.25;
	public static final double ROCKET_RIGHT_FRONT = 90;
	public static final double ROCKET_RIGHT_LEFT = 90.0 + 61.25;
	public static final double ROCKET_RIGHT_RIGHT = 90 - 61.25;
	public static final double INTAKE = 180;
	
	//Field Distances
	public static final double CARGO_DISTANCE = 1;
	public static final double HATCH_DISTANCE = 4;
	
	//Encoder Conversions
	public static final double DRIVE_DIST_PER_PULSE = 3.0*Math.PI/100.0;
	public static final double WINCH_DIST_PER_PULSE = 1.91 * Math.PI * 2 / 39 / 3;
	public static final double WINCH_CLIMB_DIST_PER_PULSE = 2.00 * Math.PI / 216.66 / 3;
	
	//Vision
	public static final int CAMERA_WIDTH = 320;
	public static final int CAMERA_HEIGHT = 240;
	public static final double MULTIPLIER_WIDTH = 1d / 12;
	public static final double MULTIPLIER_HEIGHT = 3d / 50;
	public static final double MIN_XSPEED = 0.14;
	public static final double MIN_YSPEED = 0.16;
	public static final double SONAR_VOLTS_PER_INCH = 0.012446;
	
	//public static final int PI = 3.1416;
	//public static final double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679; //memz
}
