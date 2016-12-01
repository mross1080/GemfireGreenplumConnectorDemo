package io.pivotal.gemfireServer.functions;


import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.partition.PartitionRegionHelper;

import java.util.Iterator;
import java.util.Properties;

public class ClearRegionRemoveAllFunction implements Function, Declarable {

  public void execute(FunctionContext context) {
    System.out.println(Thread.currentThread().getName() + ": Executing " + getId());
    RegionFunctionContext rfc = (RegionFunctionContext) context;
    Region localRegion = PartitionRegionHelper.getLocalDataForContext(rfc);
    int numLocalEntries = localRegion.size();

    // Destroy each entry
    long start=0, end=0;
    start = System.currentTimeMillis();
    localRegion.removeAll(localRegion.keySet());
    end = System.currentTimeMillis();
    System.out.println(Thread.currentThread().getName() + ": Cleared " + numLocalEntries + " entries in " + (end-start) + " ms");
    context.getResultSender().lastResult(true);
  }

  public String getId() {
    return getClass().getSimpleName();
  }

  public boolean optimizeForWrite() {
    return true;
  }

  public boolean hasResult() {
    return true;
  }

  public boolean isHA() {
    return true;
  }

  public void init(Properties properties) {
  }
}
