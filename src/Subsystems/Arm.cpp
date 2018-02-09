#include "Arm.h"

Arm::Arm() {
    this->armMotor = new WPI_TalonSRX(ARM_SRX);
    this->intake = new Intake();
    this->Open();
    this->encoder = new AnalogInput(ARM_ENCODER);
    this->seqStage = 0;
    this->intakeSeq = {};
    this->seqPos = {};
}

void Arm::Loop() {
    if(seqStage < (int)(sizeof(seqPos)/sizeof(int))) {
        if(intakeSeq[seqStage]) {
            if(this->intake->TurnTo(seqPos[seqStage])) {
                seqStage++;
            }
        } else {
            if(this->TurnTo(seqPos[seqStage])) {
                seqStage++;
            }
        }
    }
}

void Arm::SetSequence(bool intakeSeq[], int seqPos[]) {
    this->seqStage = 0;
    this->intakeSeq = intakeSeq;
    this->seqPos = seqPos;
}

bool Arm::TurnTo(double degrees) {
    if(fmod(fabs(this->GetAngle() - degrees), 360) < 8) {
        this->armMotor->Set(0);
        return true;
    } else if((this->GetAngle() - degrees) > 0) {
        this->armMotor->Set(-.28);
        return false;
    } else {
        this->armMotor->Set(.28);
        return false;
    }
}

void Arm::Toggle() {
    intakeClosed = intake->SetState(!intakeClosed);
}

void Arm::Close() {
    intakeClosed = true;
    intake->SetState(true);
}

void Arm::Open() {
    intakeClosed = false;
    intake->SetState(false);
}

double Arm::GetAngle() {
    return fmod(this->encoder->GetVoltage() - ARM_OFFSET + 5, 5) * 72.f;
}