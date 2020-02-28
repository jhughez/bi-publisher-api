package com.example.demo.bip.exception;
import javax.mail.MessagingException;

public class NoFileContentFoundException extends MessagingException{

  public NoFileContentFoundException(String s) {
    super(s);
    this.initCause(null);
  }

}
