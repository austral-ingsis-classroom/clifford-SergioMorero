package edu.austral.ingsis.clifford;

import java.util.*;

public class Directory {

  private final String name;
  private final Directory parent;
  private List<Directory> directories = new ArrayList<Directory>();
  private List<File> files = new ArrayList<File>();
  private List<String> all = new ArrayList<>();

  public Directory(String name, Directory parent) {
    this.name = name;
    this.parent = parent;
  }

  public String ls(String[] parsedCommand) {
    List<String> result = new ArrayList<>();
    // Collections.reverse(result);
    for (Directory dir : this.directories) {
      result.add(dir.getName());
    }
    for (File file : this.files) {
      result.add(file.getName());
    }
    if (parsedCommand.length > 1) {
      switch (parsedCommand[1]) {
        case "--ord=asc" -> result.sort(Comparator.naturalOrder());
        case "--ord=desc" -> result.sort(Comparator.reverseOrder());
        default -> {
          return "Unknown flag";
        }
      }
    }
    return (!result.isEmpty()) ? String.join(" ", result) : "";
  }

  public String touch(String fileName) {
    // Reviso si el nombre es válido
    for (int i = 0; i < fileName.length(); i++) {
      if (fileName.charAt(i) == '/') {
        return fileName + " is an invalid file name";
      }
    }
    this.files.add(new File(fileName));
    this.all.add(fileName);
    return "'" + fileName + "' file created";
  }

  public String mkdir(String dirName) {
    // Reviso si el nombre es válido
    for (int i = 0; i < dirName.length(); i++) {
      if (dirName.charAt(i) == '/') {
        return dirName + " is an invalid directory name";
      }
    }
    this.directories.add(new Directory(dirName, this));
    this.all.add(dirName);
    return "'" + dirName + "' directory created";
  }

  public String rm(String[] parsedCommand) {
    if (parsedCommand[1].equals("--recursive")) {
      if (parsedCommand.length > 2) {
        String name = parsedCommand[2];
        for (Directory dir : this.directories) {
          if (dir.getName().equals(name)) {
            this.directories.remove(dir);
            this.all.remove(name);
            return "'" + name + "' removed";
          }
        }
        return "Directory: '" + name + "' does not exist in this directory";
      } else {
        return "No directoy name provided";
      }
    } else {
      String name = parsedCommand[1];
      for (File file : this.files) {
        if (file.getName().equals(name)) {
          this.files.remove(file);
          this.all.remove(name);
          return "'" + name + "' removed";
        }
      }
      return "cannot remove '" + name + "', is a directory";
    }
  }

  public String getName() {
    return this.name;
  }

  public Directory getParent() {
    return this.parent;
  }

  public List<Directory> getDirectories() {
    return this.directories;
  }

  public List<File> getFiles() {
    return this.files;
  }
}
