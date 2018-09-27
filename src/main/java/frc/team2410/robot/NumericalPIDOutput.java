package frc.team2410.robot;

import edu.wpi.first.wpilibj.PIDOutput;

public class NumericalPIDOutput
{
	public double num;
	public double Get()
	{
		return num;
	}

	public void PIDWrite(double output)
	{
		this.num = output;
	}
}
