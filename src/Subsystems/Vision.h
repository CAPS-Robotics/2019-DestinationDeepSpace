#ifndef FRC2018_VISION_H
#define FRC2018_VISION_H

#include "WPILib.h"
#include "Commands/Subsystem.h"
#include <vector>

class Vision {
private:
	cs::UsbCamera * cam0;
	cs::UsbCamera * cam1;
	cs::CvSink * cvsink0;
	cs::CvSink * cvsink1;
	cs::VideoSink server;
	std::shared_ptr<NetworkTable> table;
	std::vector<double> centerX;
	std::vector<double> centerY;
	std::vector<double> height;
	std::vector<double> width;
public:
	Vision();
	void Update();
	double GetCentralValue();
	void SetCamera(int camera);
};


#endif