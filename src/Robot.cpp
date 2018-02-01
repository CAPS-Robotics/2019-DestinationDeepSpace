#include <cstdlib>
#include "OI.h"
#include "Robot.h"
#include "RobotMap.h"
#include "WPILib.h"

std::shared_ptr<Drivetrain> Robot::drivetrain;
std::shared_ptr<PigeonNav> Robot::gyro;
std::shared_ptr<OI> Robot::oi;
std::shared_ptr<Arm> Robot::arm;

Robot::Robot() {

}

void Robot::RobotInit() {
    Robot::gyro.reset(new PigeonNav());
    Robot::drivetrain.reset(new Drivetrain());
    Robot::arm.reset(new Arm());
    Robot::oi.reset(new OI());
	cs::UsbCamera * vidyo = new cs::UsbCamera("Vidyo", 0);
	vidyo->SetResolution(320, 240);
	vidyo->SetBrightness(10);
    CameraServer::GetInstance()->StartAutomaticCapture(*vidyo);
    smp = SWERVE_MODULE_P;
    smi = SWERVE_MODULE_I;
    smd = SWERVE_MODULE_D;
    /*gp = GYRO_P;
    gi = GYRO_I;
    gd = GYRO_D;*/
    SmartDashboard::PutNumber("swerve p", smp);
    SmartDashboard::PutNumber("swerve i", smi);
    SmartDashboard::PutNumber("swerve d", smd);
    /*SmartDashboard::PutNumber("gyro p", gp);
    SmartDashboard::PutNumber("gyro i", gi);
    SmartDashboard::PutNumber("gyro d", gd);*/
}

void Robot::DisabledInit() {

}


void Robot::DisabledPeriodic() {
    while(IsDisabled()) {}
}

void Robot::AutonomousInit() {

}

void Robot::AutonomousPeriodic() {
    SmartDashboard::PutNumber("FL Angle", Robot::drivetrain->fl->GetAngle());
    SmartDashboard::PutNumber("FR Angle", Robot::drivetrain->fr->GetAngle());
    SmartDashboard::PutNumber("BL Angle", Robot::drivetrain->bl->GetAngle());
    SmartDashboard::PutNumber("BR Angle", Robot::drivetrain->br->GetAngle());
    SmartDashboard::PutNumber("Distance Away", Robot::drivetrain->GetDistanceAway());
    SmartDashboard::PutNumber("Heading", Robot::gyro->GetHeading());
}

void Robot::TeleopInit() {

}

void Robot::TeleopPeriodic() {
    SmartDashboard::PutNumber("FL Voltage", Robot::drivetrain->fl->positionEncoder->GetVoltage());
    SmartDashboard::PutNumber("FR Voltage", Robot::drivetrain->fr->positionEncoder->GetVoltage());
    SmartDashboard::PutNumber("BL Voltage", Robot::drivetrain->bl->positionEncoder->GetVoltage());
    SmartDashboard::PutNumber("BR Voltage", Robot::drivetrain->br->positionEncoder->GetVoltage());
    SmartDashboard::PutNumber("FL Angle", Robot::drivetrain->fl->GetAngle());
    SmartDashboard::PutNumber("FR Angle", Robot::drivetrain->fr->GetAngle());
    SmartDashboard::PutNumber("BL Angle", Robot::drivetrain->bl->GetAngle());
    SmartDashboard::PutNumber("BR Angle", Robot::drivetrain->br->GetAngle());
    SmartDashboard::PutNumber("Distance Away", Robot::drivetrain->GetDistanceAway());
    SmartDashboard::PutNumber("Heading", Robot::gyro->GetHeading());
    SmartDashboard::PutNumber("Desired Heading", /*Drivetrain::wrap(*/Robot::drivetrain->desiredHeading/*+180.0, -180.0, 180.0)*/);
    smp = (float)SmartDashboard::GetNumber("swerve p", 0.0);
    smi = (float)SmartDashboard::GetNumber("swerve i", 0.0);
    smd = (float)SmartDashboard::GetNumber("swerve d", 0.0);
    /*gp = (float)SmartDashboard::GetNumber("gyro p", 0.0);
    gi = (float)SmartDashboard::GetNumber("gyro i", 0.0);
    gd = (float)SmartDashboard::GetNumber("gyro d", 0.0);*/
    Robot::oi->pollButtons();
    Robot::drivetrain->JoystickDrive();
    /*Robot::drivetrain->SetPID(gp, gi, gd);
    Robot::drivetrain->fl->setPID(smp, smi, smd);
    Robot::drivetrain->fr->setPID(smp, smi, smd);
    Robot::drivetrain->bl->setPID(smp, smi, smd);
    Robot::drivetrain->br->setPID(smp, smi, smd);*/
}

void Robot::TestPeriodic() {}

START_ROBOT_CLASS(Robot)
