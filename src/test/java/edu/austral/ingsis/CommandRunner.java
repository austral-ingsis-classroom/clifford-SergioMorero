package edu.austral.ingsis;

import edu.austral.ingsis.clifford.CommandExecutor;
import java.util.List;

public class CommandRunner implements FileSystemRunner {

  private CommandExecutor runner = new CommandExecutor();

  @Override
  public List<String> executeCommands(List<String> commands) {
    return runner.executeCommands(commands);
  }
}
