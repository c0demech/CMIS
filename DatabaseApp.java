/* Copyright (c) 2018 Michael Edie / @c0demech
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 */
package studentdbmgr;
// Swing
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
// AWT
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
/*
 * File: DatabaseApp.java
 * Author: O. Michael Edie
 * Date: October 10, 2018
 * Purpose: A GUI interface that allows a user to manage a student
 * database, with options to insert, delete, find, and update.
 * The fields are Student Id, Student Name, Major and GPA.
 */
class DatabaseApp {
  private JFrame frame;
  private JLabel studentid;
  private JLabel studentname;
  private JLabel studentmajor;
  private JLabel selection;
  private JTextField studentidTF;
  private JTextField studentnameTF;
  private JTextField studentmajorTF;
  private JComboBox<String> comboBox;
  private JButton processbutton;
  private static HashMap<Integer, Student> database;
  public static final String newline = System.getProperty("line.separator");
  private static final String[] dbactions = {"Insert", "Delete", "Find", "Update"};
 // Organize and display GUI
  private Container buildJLabelPanel(JLabel label){
    JPanel panel = new JPanel();
    panel.add(label);
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));

    return panel;
  }
  private Container buildJTextFPanel(JTextField textField){
    JPanel panel = new JPanel();
    panel.add(textField);

    return panel;

  }
  private Container buildJButtonPanel(JButton button){
    JPanel panel = new JPanel();
    button.setSize(new Dimension(15,10));
    panel.add(button);

    return panel;
  }

  private Container buildJComboPanel(JComboBox combo){
    JPanel panel = new JPanel();
    combo.setPreferredSize(new Dimension(150,25));
    panel.add(combo);

    return panel;
  }
  private static boolean isNullOrBlank(String s)
  {
    return (s==null || s.trim().equals(""));
  }
  private boolean checkData(String idfield, String namefield, String majorfield){
    // validate the student Id field
    if (idfield.equals("id")){
      try {
        Integer.parseInt(studentidTF.getText());
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(null,
                "Please use integers only for the Id field");
        return false;
      }
    }
    // validate the student name field
    if (namefield.equals("name")){
      if (studentnameTF.getText().matches(
              /* Match lastname followed by comma then firstname
               * followed by middle initial and no period. The lastname
               * can be optionally hyphenated. Middle initial is optional.
               */
              "[A-Za-z-]+,\\s+[A-Za-z]+\\s*[A-Za-z]*\\.*")) {
        //System.out.println("Got Good Student Name");
      } else {
        JOptionPane.showMessageDialog(null,
                "Please follow this name format:"+newline+" Last Name, First Name Middle Initial");
        return false;
      }
    }
    // validate the student major field
    if (majorfield.equals("major")) {
      if (studentmajorTF.getText().matches(
              "[A-Za-z-]+\\s*[A-Za-z]*")) {
        //System.out.println("Got a Good Major");
      } else {
        JOptionPane.showMessageDialog(null,
                "Please use one or two words for the major");
        return false;
      }
    }
    // If we get here it means all checks passed
    return true;
  }
  // Overloaded since we only need this functionality
  private boolean checkData(){
    // validate the student Id field
      try {
        Integer.parseInt(studentidTF.getText());
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(null,
                "Please use integers only for the Id field");
        return false;
      }
    // If we get here it means all checks passed
    return true;
  }

  private boolean checkDuplicateKey(int key){
    boolean b = false;
    if (database.containsKey(key)) {
      b = true;
    }
    return b;
  }
  private void insertData(){
    int id = Integer.parseInt(studentidTF.getText());
    if (checkDuplicateKey(id)) {
      JOptionPane.showMessageDialog(null,"Duplicate key entry attempted");
      return;
    }
    Student student = new Student(studentnameTF.getText(),
            studentmajorTF.getText());
    database.put(id, student);
    JOptionPane.showMessageDialog(null,"Insert Successful");
    // Debug Code
    //System.out.println(database.get(id).toString());
  }

  private void deleteData() {
    if (database.remove(Integer.parseInt(studentidTF.getText())) != null) {
      JOptionPane.showMessageDialog(null, "Delete Successful");
    } else {
      JOptionPane.showMessageDialog(null, "No Record Found!");
    }
  }

  private void findData() {
    int id = Integer.parseInt(studentidTF.getText());
    if (checkDuplicateKey(id)) {
      Student student = database.get(id);
     JOptionPane.showMessageDialog(null,
              "Student Id: " + String.valueOf(id) + newline +
                        "Student Name: "+ student.getName() + newline +
                        "Major: "+ student.getMajor() +       newline +
                        "Current GPA: " + String.format("%.2f",student.getGPA()));
    } else {
        JOptionPane.showMessageDialog(null,"No Record Found!");
    }
  }
  /* When the user selects the Update request this method displays two
   * JOptionPane windows in order to allow selection of the class grade
   * and the number of credits. The courseCompleted() method is
   * then called to process the information in the database.
   */
  private void updateData(){
    int id = Integer.parseInt(studentidTF.getText());
    String[] grades = {"A", "B","C","D","F"};
    String[] credits = {"3","6"};
    Student student = database.get(id);

    if (checkDuplicateKey(id)){
    // This is the JOptionPane for selecting the class grade received
    String gradeselected = (String) JOptionPane.showInputDialog(
    null,
    "Choose Grade: ",
    null,
    JOptionPane.QUESTION_MESSAGE,
    null,
    grades,
    grades[0]);
    // This is the JOptionPane for selecting credit hours
    String credithourselected = (String) JOptionPane.showInputDialog(
    null,
    "Choose Credits: ",
    null,
    JOptionPane.QUESTION_MESSAGE,
    null,
    credits,
    credits[0]);
    try {
      student.courseCompleted(gradeselected, Integer.parseInt(credithourselected));
      JOptionPane.showMessageDialog(null, "Update Successful");
      // Debug Messages
     // System.out.println(gradeselected);
      //System.out.println(credithourselected);
    } catch (NumberFormatException nfe){
        JOptionPane.showMessageDialog(null, "Update Cancelled: Credits not selected");
    } catch (NullPointerException npe){
        JOptionPane.showMessageDialog(null,"Update Cancelled: Grade not selected");
    }
    } else if (!checkDuplicateKey(id)){
        JOptionPane.showMessageDialog(null, "No Record Found!");
    }

  }

  private void buildGUI() {
    Container window = frame.getContentPane();
    JPanel panel = new JPanel(new GridLayout(0, 2));
    // Create all the panels
    panel.add(buildJLabelPanel(studentid));
    panel.add(buildJTextFPanel(studentidTF));
    panel.add(buildJLabelPanel(studentname));
    panel.add(buildJTextFPanel(studentnameTF));
    panel.add(buildJLabelPanel(studentmajor));
    panel.add(buildJTextFPanel(studentmajorTF));
    panel.add(buildJLabelPanel(selection));
    panel.add(buildJComboPanel(comboBox));
    panel.add(buildJButtonPanel(processbutton));

    frame.addWindowListener(new WindowAdapter()  {
      public void windowClosing(WindowEvent winevt) {
        System.exit(0);
      }
    });
    /* Controls database actions performed when the
     * user clicks the process request button
     */
    processbutton.addActionListener(e -> {
      switch(comboBox.getSelectedIndex()) {
        case 0:
          //System.out.println("Got Insert");
          if (checkData("id", "name", "major")){
            insertData();
          }
          break;
        case 1:
          //System.out.println("Got Delete");
          if (checkData()){
            deleteData();
          }
          break;
        case 2:
          //System.out.println("Got Find");
          if (checkData()){
            findData();
          }
          break;
        case 3:
          //System.out.println("Got Update");
          if (checkData()){
            updateData();
          }
          break;
      }
    });

    window.add(panel);

    frame.setResizable(true);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

  }
  // Initialize all our variables
  private DatabaseApp(){
    frame = new JFrame("Student Database App");
    studentid = new JLabel("Id: ");
    studentname = new JLabel("Name: ");
    studentmajor = new JLabel("Major: ");
    selection = new JLabel("Choose Selection: ");
    studentidTF = new JTextField(15);
    studentnameTF = new JTextField(15);
    studentmajorTF = new JTextField(15);
    comboBox = new JComboBox<>(dbactions);
    processbutton = new JButton("Process Request");
    database = new HashMap<>();
  }

  public static void main(String[] args){
    DatabaseApp app = new DatabaseApp();
    app.buildGUI();
  }
}
