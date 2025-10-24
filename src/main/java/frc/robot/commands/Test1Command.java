// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANTest1Subsystem;
import java.util.function.DoubleSupplier;

// Command to run the Test1 with joystick inputs
public class Test1Command extends Command {
  private final DoubleSupplier forward;
  private final DoubleSupplier reverse;
  // private final CANTest1Subsystem Test1Subsystem;
  private final CANTest1Subsystem Test1Subsystem;

  public Test1Command(
      DoubleSupplier forward, DoubleSupplier reverse, CANTest1Subsystem rollerSubsystem) {
    this.forward = forward;
    this.reverse = reverse;
    this.Test1Subsystem = rollerSubsystem;

    addRequirements(this.Test1Subsystem);
  }

  @Override
  public void initialize() {
  }

  // Runs every cycle while the command is scheduled (~50 times per second)
  @Override
  public void execute() {
    // Run the roller motor at the desired speed
    Test1Subsystem.runTest1(forward.getAsDouble(), reverse.getAsDouble());
  }

  // Runs each time the command ends via isFinished or being interrupted.
  @Override
  public void end(boolean isInterrupted) {
  }

  // Runs every cycle while the command is scheduled to check if the command is
  // finished
  @Override
  public boolean isFinished() {
    // Return false to indicate that this command never ends. It can be interrupted
    // by another command needing the same subsystem.
    return false;
  }
}
