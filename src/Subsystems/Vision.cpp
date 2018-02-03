#include "Vision.h"
#include "../RobotMap.h"
#include <vector>
#include <networktables/NetworkTableInstance.h>

Vision::Vision() {
	table = nt::NetworkTableInstance::GetDefault().GetTable("GRIP/AllDemContours");
	this->Update();
}

void Vision::Update() {
	centerX = table->GetNumberArray("centerX", llvm::ArrayRef<double>());
	centerY = table->GetNumberArray("centerY", llvm::ArrayRef<double>());
	height = table->GetNumberArray("height", llvm::ArrayRef<double>());
	width = table->GetNumberArray("width", llvm::ArrayRef<double>());
}

double Vision::GetCentralValue() {
	this->Update();
	double theCenterX = 0;
	for(unsigned int i = 0; i < centerX.size(); i++) {
		theCenterX += centerX[i];
	}
	if(centerX.size() != 0) { theCenterX /= centerX.size(); }
	return theCenterX;
}

void Vision::InitDefaultCommand() {

}