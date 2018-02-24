#include "Vision.h"
#include "../RobotMap.h"
#include <vector>
#include <networktables/NetworkTableInstance.h>

Vision::Vision() {
	cam0 = new cs::UsbCamera("cam0", 0);
	cam0->SetResolution(320, 240);
	cam0->SetBrightness(10);
	cam1 = new cs::UsbCamera("cam1", 1);
	cam1->SetResolution(320, 240);
	cam1->SetBrightness(10);
	cvsink0 = new cs::CvSink("cam0cv");
	cvsink0->SetSource(*cam0);
	cvsink0->SetEnabled(true);
	cvsink1 = new cs::CvSink("cam1cv");
	cvsink1->SetSource(*cam1);
	cvsink1->SetEnabled(true);
	CameraServer::GetInstance()->StartAutomaticCapture(*cam0);
	CameraServer::GetInstance()->StartAutomaticCapture(*cam1);
	server = CameraServer::GetInstance()->GetServer();
	SetCamera(0);
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

void Vision::SetCamera(int camera) {
	if(camera == 0) {
		server.SetSource(*cam0);
	} else {
		server.SetSource(*cam1);
	}
}