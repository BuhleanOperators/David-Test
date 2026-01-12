// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;

public class Robot extends TimedRobot {

    // ---------------- CAN IDs ----------------
    private static final int TURRET_ID = 5;
    //private static final int TOP_SHOOTER_ID = 10;
    //private static final int BOTTOM_SHOOTER_ID = 11;
    //private static final int FEEDER_ID = 12;

    // ---------------- Motors ----------------
    private  SparkMax turretMotor;
    //private  SparkMax topShooter;
    //private  SparkMax bottomShooter;
    //private  SparkMax feederMotor;

    // ---------------- Limelight ----------------
    private NetworkTable limelight;

    @Override
    public void robotInit() {

        turretMotor = new SparkMax(TURRET_ID, MotorType.kBrushless);
        //topShooter = new SparkMax(TOP_SHOOTER_ID, MotorType.kBrushless);
        //bottomShooter = new SparkMax(BOTTOM_SHOOTER_ID, MotorType.kBrushless);
        //feederMotor = new SparkMax(FEEDER_ID, MotorType.kBrushless);

        

        // Bottom wheel inverted so wheels spin opposite directions
        //bottomShooter.setInverted(true);

        limelight = NetworkTableInstance.getDefault().getTable("limelight");

        // ---------------- SmartDashboard ----------------
        SmartDashboard.putNumber("Turret kP", 0.02);
        SmartDashboard.putNumber("Turret Max Speed", 0.4);
        SmartDashboard.putNumber("Turret Deadband", 1.0);

        SmartDashboard.putNumber("Shooter Base RPM", 3000);
        SmartDashboard.putNumber("Shooter Angle Mult", 4.0);
        SmartDashboard.putNumber("Shooter Alt Angle Mult", 7.0);
        SmartDashboard.putNumber("Special Tag ID", 7);
        SmartDashboard.putNumber("Shooter RPM Tolerance", 100);

        SmartDashboard.putNumber("Feeder Speed", 0.5);
    }

    @Override
    public void teleopPeriodic() {

        // ---------------- LIMELIGHT DATA ----------------
        double tx = limelight.getEntry("tx").getDouble(0.0);
        double ty = limelight.getEntry("ty").getDouble(0.0);
        double tv = limelight.getEntry("tv").getDouble(0.0);
        double tagID = limelight.getEntry("tid").getDouble(-1);

        if (tv < 1.0) {
            turretMotor.stopMotor();
            //topShooter.stopMotor();
            //bottomShooter.stopMotor();
            //feederMotor.stopMotor();
            return;
        }

        // ---------------- TURRET AIMING ----------------
        double turretKP = SmartDashboard.getNumber("Turret kP", 0.02);
        double turretMaxSpeed = SmartDashboard.getNumber("Turret Max Speed", 0.4);
        double turretDeadband = SmartDashboard.getNumber("Turret Deadband", 1.0);

        boolean turretReady;

        if (Math.abs(tx) < turretDeadband) {
            turretMotor.stopMotor();
            turretReady = true;
        } else {
            double speed = MathUtil.clamp(tx * turretKP, -turretMaxSpeed, turretMaxSpeed);
            turretMotor.set(speed);
            turretReady = false;
        }

        // ---------------- DISTANCE CALC ----------------
        double cameraHeight = 24.0; // inches
        double tagHeight = 60.0;    // inches (same for all tags)
        double cameraAngle = 30.0;  // degrees

        double distance =
                (tagHeight - cameraHeight) /
                Math.tan(Math.toRadians(cameraAngle + ty));

        // ---------------- SHOOTER ANGLE SELECTION ----------------
        //double baseRPM = SmartDashboard.getNumber("Shooter Base RPM", 3000);
        double normalMult = SmartDashboard.getNumber("Shooter Angle Mult", 4.0);
        double altMult = SmartDashboard.getNumber("Shooter Alt Angle Mult", 7.0);
        double specialTag = SmartDashboard.getNumber("Special Tag ID", 7);

        double angleMultUsed =
                (tagID == specialTag) ? altMult : normalMult;

       // double rpmOffset = distance * angleMultUsed;

        //double bottomTargetRPM = baseRPM + rpmOffset;
        //double topTargetRPM = baseRPM - rpmOffset;

        //bottomShooter.set(Math.min(bottomTargetRPM / 5700.0, 1.0));
        //topShooter.set(Math.min(topTargetRPM / 5700.0, 1.0));

        // ---------------- SHOOTER READY ----------------
        //double rpmTolerance = SmartDashboard.getNumber("Shooter RPM Tolerance", 100);

        //double topRPM = topShooter.getEncoder().getVelocity();
        //double bottomRPM = bottomShooter.getEncoder().getVelocity();

        //boolean shooterReady =
                //Math.abs(topRPM - topTargetRPM) < rpmTolerance &&
                //Math.abs(bottomRPM - bottomTargetRPM) < rpmTolerance;

        // ---------------- AUTO FIRE ----------------
        //double feederSpeed = SmartDashboard.getNumber("Feeder Speed", 0.5);

       // if (turretReady && shooterReady) {
            //feederMotor.set(feederSpeed);
       // } else {
            //feederMotor.stopMotor();
       // }

        // ---------------- DASHBOARD DEBUG ----------------
        SmartDashboard.putNumber("Distance", distance);
        SmartDashboard.putNumber("AprilTag ID", tagID);
        SmartDashboard.putNumber("Angle Mult Used", angleMultUsed);
        SmartDashboard.putBoolean("Turret Ready", turretReady);
        //SmartDashboard.putBoolean("Shooter Ready", shooterReady);
    }
}
