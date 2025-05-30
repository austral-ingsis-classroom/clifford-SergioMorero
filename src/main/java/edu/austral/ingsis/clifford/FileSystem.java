package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSystem {

  private final Directory root = new Directory("/", null);
  private Directory current = root;

  public String ls(String[] parsedCommand) {
    return current.ls(parsedCommand);
  }

  public String cd(String[] parsedCommand) {
    if (parsedCommand.length > 1) {
      String parameter = parsedCommand[1];
      switch (parameter) {
        case ".":
          {
            break;
          }
        case "..":
          {
            if (this.current.getParent() != null) {
              this.current = this.current.getParent();
            }
            break;
          }
        default:
          {
            if (parameter.charAt(0) == '/') {
              this.current = this.root;
              String[] route = parameter.split("/");
              if (route.length == 0) {
                return "moved to directory '/'";
              }
              Directory result = recursiveCd(route, 0);
              if (result != null) {
                this.current = result;
                return "moved to directory '" + result.getName() + "'";
              } else {
                return "Route " + parameter + " does not exist";
              }
            } else {
              String[] route = parameter.split("/");
              if (route.length == 1) {
                Directory destination = null;
                // Busca si el directorio se encuentra dentro del directorio actual
                for (Directory dir : this.current.getDirectories()) {
                  if (dir.getName().equals(parameter)) {
                    destination = dir;
                  }
                }

                if (destination == null) {
                  // Busca si el nombre es un archivo en vez de un directorio
                  for (File file : this.current.getFiles()) {
                    if (file.getName().equals(parameter)) {
                      return "The name given is a file, not a directory";
                    }
                  }
                  return "'" + parameter + "' directory does not exist";
                } else {
                  this.current = destination;
                }
              } else {
                // Directorio con ruta
                Directory result = recursiveCd(route, 0);
                if (result != null) {
                  this.current = result;
                  return "moved to directory '" + result.getName() + "'";
                } else {
                  return "Route " + parameter + " does not exist";
                }
              }
            }
          }
      }
      return "moved to directory '" + this.current.getName() + "'";
    } else {
      return "You must specify the directory of destination";
    }
  }

  private Directory recursiveCd(String[] route, int index) {
    for (Directory dir : this.current.getDirectories()) {
      if (dir.getName().equals(route[index])) {
        if (index == route.length - 1) {
          return dir;
        } else {
          this.current = dir;
          return recursiveCd(route, index + 1);
        }
      }
    }
    return null;
  }

  public String touch(String[] parsedCommand) {
    if (parsedCommand.length > 1) {
      if (parsedCommand.length == 2) {
        return this.current.touch(parsedCommand[1]);
      } else {
        return "File name cannot include spaces";
      }
    } else {
      return "No file name provided";
    }
  }

  public String mkdir(String[] parsedCommand) {
    if (parsedCommand.length > 1) {
      if (parsedCommand.length == 2) {
        return this.current.mkdir(parsedCommand[1]);
      } else {
        return "Directory name cannot include spaces";
      }
    } else {
      return "No directory name provided";
    }
  }

  public String rm(String[] parsedCommand) {
    if (parsedCommand.length > 1) {
      return this.current.rm(parsedCommand);
    } else {
      return "No file name provided";
    }
  }

  public String pwd() {
    List<String> list = new ArrayList<>();
    List<String> path = recursivePwd(this.current, list);
    Collections.reverse(path);
    return "/" + String.join("/", path);
  }

  private List<String> recursivePwd(Directory current, List<String> result) {
    if (current.getName().equals("/")) {
      return result;
    } else {
      result.add(current.getName());
      return recursivePwd(current.getParent(), result);
    }
  }
}
