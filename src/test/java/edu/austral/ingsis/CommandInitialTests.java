package edu.austral.ingsis;

import edu.austral.ingsis.clifford.CommandExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandInitialTests {

  private CommandExecutor executor;

  @BeforeEach
  public void setup(){
    executor = new CommandExecutor();
  }

  @Test
  public void createFileTest(){
    Assertions.assertEquals(executor.inputCommand("touch hola.txt"), "'hola.txt' file created");
    Assertions.assertEquals(executor.inputCommand("touch"), "No file name provided");
    Assertions.assertEquals(executor.inputCommand("touch hola 2.txt"), "File name cannot include spaces");
    Assertions.assertEquals(executor.inputCommand("ls"), "hola.txt");
  }
}
