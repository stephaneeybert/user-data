package com.thalasoft.user.data.exception;

import java.util.ArrayList;
import java.util.List;

public abstract class EnrichableException extends RuntimeException {

  protected final List<InfoItem> infoItems = new ArrayList<>();

  protected class InfoItem {
    private String errorMessage = null;
    private String errorCode = null;
    private String errorContext = null;

    public InfoItem(String errorContext, String errorCode, String errorMessage) {
      this.errorContext = errorContext;
      this.errorCode = errorCode;
      this.errorMessage = errorMessage;
    }

    public InfoItem(String errorContext, String errorCode) {
      this.errorContext = errorContext;
      this.errorCode = errorCode;
    }

    public InfoItem(String errorContext) {
      this.errorContext = errorContext;
    }
  }

  protected EnrichableException() {
  }

  protected EnrichableException(String message) {
    super(message);
    this.infoItems.add(new InfoItem(message));
  }

  protected EnrichableException(Throwable cause) {
    super(cause);
  }

  protected EnrichableException(String message, Throwable cause) {
    super(message, cause);
    this.infoItems.add(new InfoItem(message));
  }

  protected EnrichableException(String errorMessage, String errorCode) {
    super(errorMessage);
    this.infoItems.add(new InfoItem(errorMessage, errorCode, errorCode));
  }

  protected EnrichableException(String errorMessage, String errorCode, String errorContext) {
    super(errorMessage);
    this.infoItems.add(new InfoItem(errorMessage, errorCode, errorContext));
  }

  protected EnrichableException(String errorMessage, String errorCode, String errorContext, Throwable cause) {
    super(errorMessage, cause);
    this.infoItems.add(new InfoItem(errorMessage, errorCode, errorContext));
  }

  public EnrichableException addContext(String errorMessage, String errorCode, String errorContext) {
    this.infoItems.add(new InfoItem(errorMessage, errorCode, errorContext));
    return this;
  }

  public String getContext() {
    StringBuilder builder = new StringBuilder();

    for (int i = this.infoItems.size() - 1; i >= 0; i--) {
      InfoItem info = this.infoItems.get(i);
      builder.append('[')
      .append(info.errorContext)
      .append(':')
      .append(info.errorCode)
      .append(':')
      .append(info.errorMessage)
      .append(']');
    }

    return builder.toString();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append(getContext());
    builder.append('\n');

    for (int i = this.infoItems.size() - 1; i >= 0; i--) {
      InfoItem info = this.infoItems.get(i);
      builder.append('[')
      .append(info.errorContext)
      .append(':')
      .append(info.errorCode)
      .append(':')
      .append(info.errorMessage)
      .append(']');
      if (i > 0)
        builder.append('\n');
    }

    if (getMessage() != null) {
      builder.append('\n');
      if (getCause() == null || !getMessage().equals(getCause().toString())) {
        builder.append(getMessage());
      }
    }
    appendException(builder, getCause());

    return builder.toString();
  }

  private void appendException(StringBuilder builder, Throwable throwable) {
    if (throwable == null) {
      return;
    }
    appendException(builder, throwable.getCause());
    builder.append(throwable.toString());
    builder.append('\n');
  }

}
