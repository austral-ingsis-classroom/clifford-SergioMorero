package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {

  private final FileSystem fileSystem = new FileSystem();

  public List<String> executeCommands(List<String> commands) {
    List<String> results = new ArrayList<>();
    for (String command : commands) {
      String result = inputCommand(command);
      results.add(result);
    }
    return results;
  }

  public String inputCommand(String input) {
    if (!input.isEmpty()) {
      String[] parsed = input.split(" ");
      String command = parsed[0];
      return switch (command) {
        case "ls" -> fileSystem.ls(parsed);
        case "cd" -> fileSystem.cd(parsed);
        case "touch" -> fileSystem.touch(parsed);
        case "mkdir" -> fileSystem.mkdir(parsed);
        case "rm" -> fileSystem.rm(parsed);
        case "pwd" -> fileSystem.pwd();
        default -> "Command '" + command + "' does not exist";
      };

    } else {
      return "Empty input";
    }
  }
}
