package io.pivotal.gemfireServer.listeners;


import java.util.List;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.cache.asyncqueue.AsyncEvent;

/**
 * Pivotal Data Engineering
 */

class MyAsyncEventListener implements AsyncEventListener {
  public boolean processEvents(List<AsyncEvent> events) {
    // Process each AsyncEvent
    for(AsyncEvent event: events) {
      // Write the event to a database
    }
    return true;
  }

  @Override
  public void close() {

  }
}