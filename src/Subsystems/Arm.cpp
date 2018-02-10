#include "Arm.h"

Arm::Arm() {
    this->armMotor = new WPI_TalonSRX(ARM_CIM);
	this->armMotor->ConfigSelectedFeedbackSensor(FeedbackDevice::Analog, 0, 0);
	this->armMotor->ConfigSetParameter(ParamEnum::eFeedbackNotContinuous, 1, 0x00, 0x00, 0x00);
	this->intake = new Intake();
    this->Open();
    this->seqStage = 0;
    this->intakeSeq = new bool[0];
    this->seqPos = new int[0];
	this->seqLen = 0;
	this->armEncoder = new AnalogInput(ARM_ENCODER);
}

void Arm::Loop() {
    if(seqStage < seqLen) {
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

void Arm::SetSequence(bool * intakeSeq, int * seqPos, int len) {
    this->seqStage = 0;
    this->intakeSeq = intakeSeq;
    this->seqPos = seqPos;
	this->seqLen = len;
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
    return fmod(this->armEncoder->GetVoltage() - ARM_OFFSET + 5, 5) * (360.0/5.0);
}
