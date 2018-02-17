#ifndef FRC2018_ROBOTMAP_H
#define FRC2018_ROBOTMAP_H

//PID
const float SWERVE_MODULE_P = 5;
const float SWERVE_MODULE_I = 0;
const float SWERVE_MODULE_D = 3;
/*const float GYRO_P = 3;
const float GYRO_I = 0;
const float GYRO_D = 2;*/

//CAN IDs
const int FRONT_RIGHT_STEER = 1;
const int FRONT_RIGHT_DRIVE = 2;
const int PIGEON_IMU_SRX = 2;
const int FRONT_LEFT_DRIVE = 3;
const int FRONT_LEFT_STEER = 4;
const int BACK_LEFT_STEER = 5;
const int BACK_LEFT_DRIVE = 6;
const int ARM_CIM = 7;
const int INTAKE_SRX = 8;
const int BACK_RIGHT_DRIVE = 9;
const int BACK_RIGHT_STEER = 10;
const int CLIMB_CIM = 11;
const int PCM = 12;

//PCM Addresses
const int INTAKE_FORWARD = 7;
const int INTAKE_BACKWARD = 6;

//Inputs
const int FR_STEER_ENCODER = 0;
const int FL_STEER_ENCODER = 1;
const int BL_STEER_ENCODER = 2;
const int BR_STEER_ENCODER = 3;
const int INTAKE_ENCODER = 4;
const int ARM_ENCODER = 5;
const int RANGE_FINDER = 6;

const int CIMCODER_A = 0;
const int CIMCODER_B = 1;

//Offsets
const float ARM_OFFSET = 827; //SET BEFORE TRYING TO MOVE ARM PLEASE
const float INTAKE_OFFSET = 43; //SET BEFORE TRYING TO MOVE ARM PLEASE
const float FL_OFFSET = 1.7290;
const float FR_OFFSET = 2.1179;
const float BL_OFFSET = 3.3129;
const float BR_OFFSET = 3.4313;

const int CAMERA_BRIGHTNESS = 7;
const double GR = 1.2;
const float GEAR_DISTANCE = 9.3;
const double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679; //memz
const double DIST_PER_PULSE = 3.0*PI/100.0;

#endif //FRC2018_ROBOTMAP_H