package edu.austral.ingsis.clifford;

import java.util.List;

public class CommandExecutor{

  private final FileSystem fileSystem = new FileSystem();

  public List<String> executeCommands(List<String> commands){
    return null;
  }

  public String inputCommand(String input){
    if (!input.isEmpty()){
      String[] parsed = input.split(" ");
      String command = parsed[0];
      return switch (command){
        case "ls" -> fileSystem.ls(parsed);
        case "cd" -> fileSystem.cd(parsed);
        case "touch" -> fileSystem.touch(parsed);
        case "mkdir" -> fileSystem.mkdir(parsed);
        case "rm" ->null;
        case "pwd" ->null;
        default -> "Command '" + command + "' does not exist";
      };

    }
    else {
      return "Empty input";
    }
  }
}
