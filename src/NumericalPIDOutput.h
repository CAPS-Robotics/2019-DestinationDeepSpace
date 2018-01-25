#ifndef FRC2018_NUMERICALPIDOUTPUT_H
#define FRC2018_NUMERICALPIDOUTPUT_H

#include "wpilib.h"

class NumericalPIDOutput : public PIDOutput {
public:
    double num;
    double Get();
    NumericalPIDOutput();
    void PIDWrite(double output);
};


#endif //FRC2018_NUMERICALPIDOUTPUT_H
