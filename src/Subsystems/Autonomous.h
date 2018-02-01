#ifndef FRC2018_AUTONOMOUS_H
#define FRC2018_AUTONOMOUS_H

#include "string"

class Autonomous {
private:
    int autoNum;
    bool left;
public:
    Autonomous();
    void Init(int station, std::string data);
    void Loop();
	void CrossField(bool left);
	void StraightAhead(bool left);
	void HalfWay(bool left);
	void GoAround(bool left);
};

#endif //FRC2018_AUTONOMOUS_H