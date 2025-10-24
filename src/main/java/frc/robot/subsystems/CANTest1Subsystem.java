// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Test1Constants;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

public class CANTest1Subsystem extends SubsystemBase {

  //Test
  private final SparkMax test1Motor;
//Test Motor Current Limit
  public CANTest1Subsystem(){
  test1Motor = new SparkMax(Test1Constants.TEST1_MOTOR_ID, MotorType.kBrushless);

  test1Motor.setCANTimeout(250);

  SparkMaxConfig test1Config = new SparkMaxConfig();
  test1Config.voltageCompensation(Test1Constants.ROLLER_MOTOR_VOLTAGE_COMP);
  test1Config.smartCurrentLimit(Test1Constants.TEST1_ROLLER_MOTOR_CURRENT_LIMIT);
  //Test

  test1Motor.configure(test1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
}
@Override
  public void periodic() {
  }
  public void runTest1(double forward, double reverse) {
    test1Motor.set(forward - reverse);
  }
}