package io.pivotal.gemfire.client;

import com.gemstone.gemfire.cache.query.CqEvent;
import org.springframework.data.gemfire.listener.adapter.ContinuousQueryListenerAdapter;

/**
 * Created by mross on 10/13/16.
 */
public class DefaultMessageDelegate extends ContinuousQueryListenerAdapter {

    public void handleEvent(CqEvent event) {
            System.out.println(event);
    }
}
