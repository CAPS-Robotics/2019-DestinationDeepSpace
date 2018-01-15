#ifndef OI_H
#define OI_H

#include "WPILib.h"

class OI
{
public:
    OI();
    bool canPress[12];
    Joystick * joy1;
    void pollButtons();
    double GetX();
    double GetY();
    double GetTwist();
    double GetSlider();
    double applyDeadzone(double val, double deadzone);
};

#endif
