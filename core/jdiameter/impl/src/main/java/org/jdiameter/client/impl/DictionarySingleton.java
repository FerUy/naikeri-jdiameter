package org.jdiameter.client.impl;

import java.io.InputStream;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.validation.Dictionary;
import org.jdiameter.api.validation.ValidatorLevel;
import org.jdiameter.common.impl.validation.DictionaryImpl;

/**
 * Util class. Makes it easier to access Dictionary instance as singleton.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @since 1.5.4.0-build404
 */
public class DictionarySingleton {

  private DictionarySingleton() {
    // defeat instantiation
  }

  public static Dictionary getDictionary() {
    return DictionaryImpl.getInstance((String) null);
  }

  public static Dictionary getDictionary(String confFile) {
    return DictionaryImpl.getInstance(confFile);
  }

  public static Dictionary getDictionary(InputStream is) {
    return DictionaryImpl.getInstance(is);
  }

  static void init(String clazz, boolean validatorEnabled, ValidatorLevel validatorSendLevel, ValidatorLevel validatorReceiveLevel) throws InternalException {
    try {
      Class.forName(clazz).getMethod("getInstance", String.class).invoke(null, new Object[] {null});
      DictionaryImpl.INSTANCE.setEnabled(validatorEnabled);
      DictionaryImpl.INSTANCE.setSendLevel(validatorSendLevel);
      DictionaryImpl.INSTANCE.setReceiveLevel(validatorReceiveLevel);
    }
    catch (Exception e) {
      throw new InternalException(e);
    }
  }
}
