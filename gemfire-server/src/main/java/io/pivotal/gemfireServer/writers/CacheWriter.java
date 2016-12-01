package io.pivotal.gemfireServer.writers;


import org.apache.geode.LogWriter;
import org.apache.geode.cache.*;
import org.apache.geode.cache.util.CacheWriterAdapter;

import java.util.Properties;

/**
 * Pivotal Data Engineering
 */
public class CacheWriter extends CacheWriterAdapter<String, String>
    implements Declarable {

  private static LogWriter logger;

  static {
    logger = CacheFactory.getAnyInstance().getDistributedSystem().getLogWriter();
  }

  @Override
  public void beforeCreate(EntryEvent<String, String> entryEvent) {
    logger.info("key=" + entryEvent.getKey());
    logger.info("value=" + entryEvent.getNewValue());

  }

  @Override
  public void beforeUpdate(EntryEvent<String, String> entryEvent) {
    logger.info("key=" + entryEvent.getKey());
    logger.info("value=" + entryEvent.getNewValue());

  }

  @Override
  public void beforeDestroy(EntryEvent<String, String> event) throws CacheWriterException {
  }

  @Override
  public void beforeRegionDestroy(RegionEvent<String, String> event) throws CacheWriterException {
  }

  @Override
  public void beforeRegionClear(RegionEvent<String, String> event) throws CacheWriterException {
  }


  @Override
  public void init(Properties arg0) {
  }
}
