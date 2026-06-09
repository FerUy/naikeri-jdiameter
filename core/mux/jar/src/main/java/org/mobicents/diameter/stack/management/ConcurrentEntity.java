package org.mobicents.diameter.stack.management;

import java.io.Serializable;

public interface ConcurrentEntity extends Serializable {

  enum ConcurrentEntityNames {
    ThreadGroup, ProcessingMessageTimer, DuplicationMessageTimer,
    RedirectMessageTimer, PeerOverloadTimer, ConnectionTimer, StatisticTimer, ApplicationSession;
  }

  String getName();

  String getDescription();

  Integer getSize();
}
