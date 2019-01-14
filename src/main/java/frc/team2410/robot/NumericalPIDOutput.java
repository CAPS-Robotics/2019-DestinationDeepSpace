package frc.team2410.robot;

import edu.wpi.first.wpilibj.PIDOutput;


public class NumericalPIDOutput implements PIDOutput
{
	private double num;
	public double get()
	{
		return num;
	}

	public void pidWrite(double output)
	{
		this.num = output;
	}
}
