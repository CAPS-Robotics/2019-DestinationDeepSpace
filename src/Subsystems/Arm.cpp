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
	this->armPos = this->GetAngle();
	this->intakePos = this->intake->GetAngle();
}

void Arm::SetSetpoint() {
	this->armPos = this->GetAngle();
	this->intakePos = this->intake->GetAngle();
}

void Arm::Loop() {
    if(seqStage < seqLen) {
        if(intakeSeq[seqStage]) {
	        this->TurnTo(armPos, true);
	        intakePos = seqPos[seqStage];
            if(this->intake->TurnTo(seqPos[seqStage], false)) {
                seqStage++;
            }
        } else {
	        this->intake->TurnTo(intakePos, true);
	        armPos = seqPos[seqStage];
            if(this->TurnTo(seqPos[seqStage], false)) {
                seqStage++;
            }
        }
    } else {
	    this->TurnTo(armPos, false);
	    this->intake->TurnTo(intakePos, false);
    }
}

void Arm::SetSequence(bool * intakeSeq, int * seqPos, int len) {
    this->seqStage = 0;
    this->intakeSeq = intakeSeq;
    this->seqPos = seqPos;
	this->seqLen = len;
}

bool Arm::TurnTo(double degrees, bool compensate) {
    if(fmod(fabs(this->GetAngle() - degrees), 360) < 8 || compensate) {
	    this->armMotor->Set(((this->GetAngle() - degrees) > 0) ? -.1*cos((this->GetAngle()*PI/180)) : -.3*cos((this->GetAngle()*PI/180)));
        return true;
    } else {
        this->armMotor->Set((this->GetAngle() < 30) ? (((this->GetAngle() - degrees) > 0) ? -.1*cos((this->GetAngle()*PI/180)) : -.85*cos((this->GetAngle()*PI/180))) : (((this->GetAngle() - degrees) > 0) ? .4*cos((this->GetAngle()*PI/180)) : -.1*cos((this->GetAngle()*PI/180))));
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
    return (this->armMotor->GetSensorCollection().GetAnalogIn() - ARM_OFFSET) * (360.0/(1024.0*GR));
}
