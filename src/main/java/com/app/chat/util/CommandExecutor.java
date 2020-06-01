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

  public int executeShellCommand(String command, String username) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("bash", "-c", command);
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
      logger.info("System command received from user: " + username);
      if (exitVal == 0) {
        logger.info("Execution result -> Success");
        logger.info("Output: " + output.toString());
      } else {
        // error during execution
        logger.info("Execution result -> Failure");
      }
    } catch (IOException | InterruptedException e) {
      logger.error("Error occurred during execution", e.getMessage());
    }
    return exitVal;
  }

}
