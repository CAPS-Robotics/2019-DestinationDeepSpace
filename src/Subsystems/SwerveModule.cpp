#include <Robot.h>
#include "SwerveModule.h"
#include "../RobotMap.h"

SwerveModule::SwerveModule(int steerMotor, int driveMotor, int encoder, float offset, bool isInverted) : Subsystem("SwerveModule") {
    this->steer = new WPI_TalonSRX(steerMotor);
    //this->steer->ConfigNeutralMode(TalonSRX::NeutralMode::kNeutralMode_Brake);
    this->offset = offset;
    this->drive = new Talon(driveMotor);
    this->drive->SetInverted(isInverted);
    this->positionEncoder = new AnalogInput(encoder);
    this->pid = new PIDController(P, I, D, this->positionEncoder, this->steer, 0.002);
    this->pid->SetContinuous(true);
    this->pid->SetPercentTolerance(1);
    this->pid->SetInputRange(0.0, 5.0);
    this->pid->SetOutputRange(-1.0, 1.0);
    this->pid->Enable();
    currentSpeed = 0;
}

void SwerveModule::InitDefaultCommand() {

}

void SwerveModule::setPID(float p, float i, float d) {
    this->pid->SetPID(p, i, d);
}

void SwerveModule::Drive(double speed, double setpoint) {
    speed = fabs(speed) > 0.1 ? speed : 0;
    setpoint /= 72.f;
    double currentPos = fmod(this->positionEncoder->GetVoltage() - offset + 5, 5);

    double dist = setpoint - currentPos;

    if (fabs(dist) > 1.25 && fabs(dist) < 3.75) {
        setpoint = fmod(setpoint + 2.5, 5);
        speed *= -1;
    }

    if (speed == 0 || fabs(speed - currentSpeed) > 1.f) {
        currentSpeed = 0;
    } else if (currentSpeed > speed) {
        currentSpeed -= 0.04;
    } else if (currentSpeed < speed) {
        currentSpeed += 0.04;
    }

    SmartDashboard::PutNumber("Distance", dist);
    if(Robot::oi->GetTwist() != 0 || Robot::oi->GetX() != 0 || Robot::oi->GetY() != 0) {
        this->pid->SetSetpoint(fmod(setpoint + offset, 5));
    }
    this->drive->Set(currentSpeed);
}

void SwerveModule::ReturnToZero() {
    this->pid->SetSetpoint(offset);
    SmartDashboard::PutNumber("Setpoint", this->pid->GetSetpoint());
}

double SwerveModule::GetAngle() {
    return fmod(this->positionEncoder->GetVoltage() - offset + 5, 5) * 72.f;
}
