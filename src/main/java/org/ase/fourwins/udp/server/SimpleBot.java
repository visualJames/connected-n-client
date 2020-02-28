package org.ase.fourwins.udp.server;

import java.io.IOException;

import org.ase.fourwins.client.udphelper.MessageListener;
import org.ase.fourwins.client.udphelper.MessageSender;


public class SimpleBot implements MessageListener {

  private final MessageSender messageSender;

  public SimpleBot(String name, MessageSender messageSender) {
    this.messageSender = messageSender;
    System.out.println("Try to register " + name);
    tryToSend("REGISTER;" + name);
  }

  private void tryToSend(String message) {
    try {
      messageSender.send(message);
    } catch (IOException e) {
      System.err.println("Failed to send " + message + ": " + e.getMessage());
    }
  }

  @Override
  public void onMessage(String message) {
    String[] parts = message.split(";");
    if (message.startsWith("NEW SEASON;")) {
      tryToSend("JOIN;" + parts[1]);
    } else if (message.startsWith("YOURTURN;")) {
      tryToSend("INSERT;1;" + parts[1]);
    }
  }

}
