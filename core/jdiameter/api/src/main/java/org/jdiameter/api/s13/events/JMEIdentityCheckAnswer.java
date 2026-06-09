package org.jdiameter.api.s13.events;

import org.jdiameter.api.app.AppAnswerEvent;

public interface JMEIdentityCheckAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "ECA";
  String _LONG_NAME = "ME-Identity-Check-Answer";

  int code = 324;

  boolean isEquipmentStatusAVPPresent();
  int getEquipmentStatus();
}
