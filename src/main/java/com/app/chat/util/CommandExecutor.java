package com.app.chat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Pritesh Soni
 *
 */
@Component
public class CommandExecutor {

  private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

  public int executeShellCommand(String command) {
    ProcessBuilder processBuilder = new ProcessBuilder();
//    processBuilder.command("bash", "-c", "ls /home/mkyong/");
    processBuilder.command(command);
    int exitVal = 0;

    try {
      Process process = processBuilder.start();
      StringBuilder output = new StringBuilder();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }
      exitVal = process.waitFor();
      if (exitVal == 0) {
        logger.info("Success!");
        logger.info("Output: " + output.toString());
        System.exit(0);
      } else {
        // abnormal
        logger.error("Failure!");
      }

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return exitVal;
  }

}