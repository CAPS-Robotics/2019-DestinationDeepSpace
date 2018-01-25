#include "NumericalPIDOutput.h"

double NumericalPIDOutput::Get() {
    return num;
}

NumericalPIDOutput::NumericalPIDOutput() : PIDOutput() {

}

void NumericalPIDOutput::PIDWrite(double output) {
    this->num = output;
}
