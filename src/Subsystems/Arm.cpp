#include <Robot.h>
#include "Arm.h"

Arm::Arm() {
    this->armMotor = new WPI_TalonSRX(ARM_CIM);
    this->intake = new Intake();
    this->intakeClosed = true;
    this->intakeKicked = false;
    this->ToggleIntake();
    this->cimcoder = new Encoder(WINCH_CIMCODER_A, WINCH_CIMCODER_B);
    this->cimcoder->SetDistancePerPulse(WINCH_DIST_PER_PULSE);
    this->cimcoder->Reset();
    this->targetPos = cimcoder->GetDistance();
}

void Arm::Loop() {
    if(!Robot::oi->joy1->GetRawButton(5) && !Robot::oi->joy1->GetRawButton(3) && fabs(this->GetCurrent()) < 30) {
        if (fabs(this->cimcoder->GetDistance() - this->targetPos) < .5) {
            this->armMotor->Set(0);
        } else if (this->targetPos - this->cimcoder->GetDistance() > 0) {
            this->armMotor->Set(1);
        } else {
            this->armMotor->Set(-1);
        }
    }
    if(fabs(this->GetCurrent()) > 30) {
        this->armMotor->Set(0);
    }
}

void Arm::MoveTo(double position) {
    this->targetPos = position;
}

void Arm::ToggleIntake() {
    this->intakeClosed = this->intake->SetState(!intakeClosed);
}

void Arm::ToggleKick() {
    this->intakeKicked = this->intake->SetKicked(!intakeKicked);
}

void Arm::Close() {
    this->intakeClosed = this->intake->SetState(true);
}

void Arm::Open() {
    this->intakeClosed = this->intake->SetState(false);
}

void Arm::KickDown() {
    this->intakeKicked = this->intake->SetKicked(true);
}

void Arm::KickUp() {
    this->intakeKicked = this->intake->SetKicked(false);
}

double Arm::GetCurrent() {
    return this->armMotor->GetOutputCurrent();
}

