#include <cstdlib>
#include "OI.h"
#include "Robot.h"
#include "RobotMap.h"
#include "WPILib.h"

std::shared_ptr<Drivetrain> Robot::drivetrain;
std::shared_ptr<PigeonNav> Robot::gyro;
std::shared_ptr<OI> Robot::oi;

Robot::Robot() {

}

void Robot::RobotInit() {
    Robot::drivetrain.reset(new Drivetrain());
    Robot::gyro.reset(new PigeonNav());
    Robot::oi.reset(new OI());
    p = P;
    i = I;
    d = D;
    SmartDashboard::PutNumber("p", p);
    SmartDashboard::PutNumber("i", i);
    SmartDashboard::PutNumber("d", d);
    /*this->autoPicker = new SendableChooser<Command *>();
    SmartDashboard::PutData("Auto Picker", this->autoPicker);*/
}

void Robot::DisabledInit() {
    autonomousCommand = nullptr;
}


void Robot::DisabledPeriodic() {
    frc::Scheduler::GetInstance()->Run();
}

void Robot::AutonomousInit() {
    autonomousCommand = autoPicker->GetSelected();
    autonomousCommand->Start();
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
    if (autonomousCommand != nullptr) {
        autonomousCommand->Cancel();
    }
    this->autoPicker = new SendableChooser<Command *>();
    SmartDashboard::PutData("Auto Picker", this->autoPicker);
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
    p = SmartDashboard::GetNumber("p", 0.0);
    i = SmartDashboard::GetNumber("i", 0.0);
    d = SmartDashboard::GetNumber("d", 0.0);
    Robot::oi->pollButtons();
    Robot::drivetrain->JoystickDrive();
    Robot::drivetrain->fl->setPID(p, i, d);
    Robot::drivetrain->fr->setPID(p, i, d);
    Robot::drivetrain->bl->setPID(p, i, d);
    Robot::drivetrain->br->setPID(p, i, d);
}

void Robot::TestPeriodic() {}

START_ROBOT_CLASS(Robot)
