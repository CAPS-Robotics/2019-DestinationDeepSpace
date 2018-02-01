#ifndef FRC2018_ROBOTMAP_H
#define FRC2018_ROBOTMAP_H

//PWMs
const int BR_DRIVE_TALON = 4;
const int FR_DRIVE_TALON = 1;
const int FL_DRIVE_TALON = 2;
const int BL_DRIVE_TALON = 3;

//PID
const float SWERVE_MODULE_P = 3;
const float SWERVE_MODULE_I = 0;
const float SWERVE_MODULE_D = 2;
/*const float GYRO_P = 3;
const float GYRO_I = 0;
const float GYRO_D = 2;*/

//CAN IDs
const int FL_TALON_SRX = 0;
const int BL_TALON_SRX = 1;
const int BR_TALON_SRX = 2;
const int PIGEON_IMU_SRX = 3;
const int ARM_SRX = 3;
const int FR_TALON_SRX = 4;
const int PCM = 6;

//PCM Addresses
const int INTAKE_FORWARD = 7;
const int INTAKE_BACKWARD = 6;

//Inputs
const int FR_STEER_ENCODER = 0;
const int FL_STEER_ENCODER = 1;
const int BL_STEER_ENCODER = 2;
const int BR_STEER_ENCODER = 3;

//Offsets
const float FL_OFFSET = 4.5690;
const float FR_OFFSET = 2.0739;
const float BL_OFFSET = 2.4523;
const float BR_OFFSET = 2.4340;

const int RANGE_FINDER = 4;

const double GR = 1.0 / 1.2;
const float GEAR_DISTANCE = 9.3;
const double PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679; //memz

#endif //FRC2018_ROBOTMAP_H