package org.mobicents.diameter.stack.management;

import java.io.Serializable;
import java.util.HashMap;

public interface Parameters extends Serializable {

  boolean getAcceptUndefinedPeer();

  void setAcceptUndefinedPeer(boolean acceptUndefinedPeer);

  boolean getDuplicateProtection();

  void setDuplicateProtection(boolean duplicateProtection);

  long getDuplicateTimer();

  void setDuplicateTimer(long duplicateTimer);

  boolean getUseUriAsFqdn();

  void setUseUriAsFqdn(boolean useUriAsFqdn);

  boolean getSingleLocalPeer();

  void setSingleLocalPeer(boolean singleLocalPeer);

  int getQueueSize();

  void setQueueSize(int queueSize);

  long getMessageTimeout();

  void setMessageTimeout(long messageTimeout);

  long getStopTimeout();

  void setStopTimeout(long stopTimeout);

  long getCeaTimeout();

  void setCeaTimeout(long ceaTimeout);

  long getIacTimeout();

  void setIacTimeout(long iacTimeout);

  long getDwaTimeout();

  void setDwaTimeout(long dwaTimeout);

  long getDpaTimeout();

  void setDpaTimeout(long dpaTimeout);

  long getRecTimeout();

  void setRecTimeout(long recTimeout);

  /* Gone since merge with build-350
  public String getThreadPool_Priority();

  public void setThreadPool_Priority(String threadPoolPriority);

  public Integer getThreadPool_Size();

  public void setThreadPool_Size(Integer threadPoolSize);
   */

  HashMap<String, ConcurrentEntity> getConcurrentEntities();

  void setConcurrentEntity(ConcurrentEntity concurrentEntity);

  Long getStatisticLogger_Delay();

  void setStatisticLogger_Delay(Long statisticLoggerDelay);

  Long getStatisticLogger_Pause();

  void setStatisticLogger_Pause(Long statisticLoggerPause);
}
