#ifndef FRC2018_VISION_H
#define FRC2018_VISION_H

#include "WPILib.h"
#include "Commands/Subsystem.h"
#include <vector>

class Vision {
private:
	std::shared_ptr<NetworkTable> table;
	std::vector<double> centerX;
	std::vector<double> centerY;
	std::vector<double> height;
	std::vector<double> width;
public:
	Vision();
	void Update();
	double GetCentralValue();
	void InitDefaultCommand();
};


#endif