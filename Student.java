/* Copyright (c) 2018 Michael Edie / @c0demech
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 */
package studentdbmgr;

import java.util.HashMap;

/*
 * File: Student.java
 * Author: O. Michael Edie
 * Date: October 10, 2018
 * Purpose: Defines a student record consisting of name
 * major and computed GPA. This record is stored in
 * a hashmap and referenced by the student Id defined elsewhere.
 *
 */
public class Student {
  private String name;
  private String major;
  // Next two variables are used to compute GPA
  private int completedcredits;
  private int qualitypoints;
  private double gpa = 4.0;

  Student(String name, String major){
    this.name = name;
    this.major = major;
    this.completedcredits = 0;
    this.qualitypoints = 0;
  }

  public String getName(){
    return this.name;
  }

  public String getMajor(){
    return this.major;
  }

  public double getGPA(){
    return gpa;
  }
  // Updates class grade and credit hours and then computes the new GPA.
  protected void courseCompleted(String coursegrade, int credithours){
    HashMap<String, Integer> grade = new HashMap<>();
    grade.put("A", 4);
    grade.put("B", 3);
    grade.put("C", 2);
    grade.put("D", 1);
    grade.put("F", 0);
    this.qualitypoints += grade.get(coursegrade) * credithours;
    this.completedcredits += credithours;
    this.gpa = (double) qualitypoints / completedcredits;
  }

  @Override
  public String toString() {
    return name + " "+ major + " "+ String.valueOf(gpa);

  }
}
