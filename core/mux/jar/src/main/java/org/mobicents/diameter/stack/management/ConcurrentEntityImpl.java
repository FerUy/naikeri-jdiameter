package org.mobicents.diameter.stack.management;

public class ConcurrentEntityImpl implements ConcurrentEntity {

  private static final long serialVersionUID = 1L;

  private String name;
  private String description;
  private Integer size;

  private ConcurrentEntityImpl(ConcurrentEntityNames name, String description, Integer size) {
    this.name = name.name();
    this.description = description;
    this.size = size;
  }

  public static ConcurrentEntityImpl createEntity(ConcurrentEntityNames name, String description, Integer size) {
    return new ConcurrentEntityImpl(name, description, size);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Integer getSize() {
    return size;
  }
}
