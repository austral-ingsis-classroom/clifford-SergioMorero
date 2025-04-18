package edu.austral.ingsis.clifford;

public class FileSystem {

  private final Directory root = new Directory("/", null);
  private Directory current = root;

  public String ls(String[] parsedCommand){
    return current.ls(parsedCommand);
  }

  public String cd(String[] parsedCommand){
    if (parsedCommand.length > 1){
      String parameter = parsedCommand[1];
      switch (parameter){
        case ".":{
          break;
        }
        case "..":{
          if (this.current.getParent() != null){
            this.current = this.current.getParent();
          }
          break;
        }
        default:{
          // Directorio sin ruta
          if (parameter.charAt(0) != '/'){
            Directory destination = null;
            // Busca si el directorio se encuentra dentro del directorio actual
            for (Directory dir: this.current.getDirectories()){
              if (dir.getName().equals(parameter)){
                destination = dir;
              }
            }

            if (destination == null){
              // Busca si el nombre es un archivo en vez de un directorio
              for (File file: this.current.getFiles()){
                if (file.getName().equals(parameter)){
                  return "The name given is a file, not a directory";
                }
              }
              return "Directory not found";
            }
            else {
              this.current = destination;
            }

          }
          // Directorio con ruta
          else {
            this.current = this.root;
            String[] route = parameter.split("/");
            Directory result = recursiveCd(route, 0);
            if (result != null){
              this.current = result;
              return "Moved to directory '" + result.getName() + "'";
            }
            else {
              return "Route " + parameter + " does not exist";
            }
          }

        }

      }
      return "Moved to directory: '" + this.current.getName() + "'";
    }
    else {
      return "You must specify the directory of destination";
    }
  }

  private Directory recursiveCd(String[] route, int index){
    for (Directory dir: this.current.getDirectories()){
      if (dir.getName().equals(route[index])){
        if (index == route.length - 1){
          return dir;
        }
        else {
          this.current = dir;
          recursiveCd(route, index + 1);
        }
      }
    }
    return null;
  }

  public String touch(String[] parsedCommand){
    if (parsedCommand.length > 1){
      return this.current.touch(parsedCommand[1]);
    }
    else {
      return "No file name provided";
    }
  }

  public String mkdir(String[] parsedCommand){
    if (parsedCommand.length > 1){
      return this.current.mkdir(parsedCommand[1]);
    }
    else {
      return "No directory name provided";
    }
  }

}
