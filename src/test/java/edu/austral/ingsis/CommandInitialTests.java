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

  @Test
  public void createDirectoryTest(){
    Assertions.assertEquals(executor.inputCommand("mkdir saludos"), "'saludos' directory created");
    executor.inputCommand("touch hola.txt");
    Assertions.assertEquals(executor.inputCommand("ls --ord=asc"), "hola.txt saludos/");
    Assertions.assertEquals(executor.inputCommand("ls --ord=desc"), "saludos/ hola.txt");
    Assertions.assertEquals(executor.inputCommand("cd saludos"), "Moved to directory: 'saludos'");
    Assertions.assertEquals(executor.inputCommand("ls"), "");
    Assertions.assertEquals(executor.inputCommand("cd .."), "Moved to directory: '/'");
  }

  @Test
  public void removeTest(){
    executor.inputCommand("touch hola.txt");
    executor.inputCommand("mkdir saludos");
    executor.inputCommand("mkdir saludo");
    executor.inputCommand("cd saludo");
    executor.inputCommand("touch aaa.txt");
    Assertions.assertEquals(executor.inputCommand("rm aa.txt"), "File: 'aa.txt' does not exist in this directory");
    Assertions.assertEquals(executor.inputCommand("rm aaa.txt"), "'aaa.txt' removed");
    Assertions.assertEquals(executor.inputCommand("ls"), "");
    executor.inputCommand("cd ..");
    Assertions.assertEquals(executor.inputCommand("rm --recursive saludoss"), "Directory: 'saludoss' does not exist in this directory");
    Assertions.assertEquals(executor.inputCommand("rm --recursive saludo"), "'saludo' removed");
  }

  @Test
  public void pwdTest(){
    executor.inputCommand("mkdir a1");
    executor.inputCommand("mkdir a2");
    executor.inputCommand("mkdir a3");
    executor.inputCommand("cd a2");
    executor.inputCommand("mkdir b1");
    executor.inputCommand("mkdir b2");
    executor.inputCommand("mkdir b3");
    executor.inputCommand("cd b1");
    executor.inputCommand("mkdir c1");
    executor.inputCommand("mkdir c2");
    executor.inputCommand("mkdir c3");
    executor.inputCommand("cd c3");
    Assertions.assertEquals(executor.inputCommand("pwd"), "/a2/b1/c3");
  }
}
