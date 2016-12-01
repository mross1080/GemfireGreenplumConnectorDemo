  package io.pivotal.gemfireServer.functions;


import io.pivotal.gemfire.gpdb.service.GpdbService;
import org.apache.geode.LogWriter;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.FunctionAdapter;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.RegionFunctionContext;

import java.util.Properties;

import static io.pivotal.gemfire.gpdb.service.GpdbService.createOperation;

  /**
 * Pivotal Data Engineering
 */
public class ImportFromGPDBToGemfireFunction extends FunctionAdapter
    implements Declarable {

  private static final long serialVersionUID = 1L;

  public static final String ID = "myGemfireFunction";

  private transient Cache cache = CacheFactory.getAnyInstance();

  private transient LogWriter logger = CacheFactory.getAnyInstance()
      .getDistributedSystem().getLogWriter();

  public void execute(FunctionContext context) {
    if (!(context instanceof RegionFunctionContext)) {
      throw new FunctionException(
          "This is a data aware function, and has to be called using FunctionService.onRegion.");
    }

    try {


      Region<?,?> region = cache.getRegion("Customer");
      long numberOfResults = GpdbService.createOperation(region).importRegion();

      logger.info("Successfully imported this many records : " + numberOfResults);
      context.getResultSender().lastResult("Successfully Executed a Function");

    } catch (Exception e) {
      StringBuffer sb = new StringBuffer();
      if (e.getMessage() != null) {
        sb.append(e.getMessage() + "\n");
      }
      for (StackTraceElement ste : e.getStackTrace()) {
        sb.append(ste.toString());
        sb.append("\n");
      }
      logger.error(sb.toString());
      context.getResultSender().lastResult(sb.toString());
    }
    logger.info("I am in the " + this.getClass().getName() + " function");
    context.getResultSender().lastResult("");
  }



  public String getId() {
    return getClass().getSimpleName();
  }

  /*
   * (non-Javadoc)
   * @see com.gemstone.gemfire.cache.execute.Function#optimizeForWrite()
   */
  public boolean optimizeForWrite() {
    return false;
  }

  /*
   * (non-Javadoc)
   * @see com.gemstone.gemfire.cache.execute.Function#isHA()
   */
  public boolean isHA() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see com.gemstone.gemfire.cache.execute.Function#hasResult()
   */
  public boolean hasResult() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see com.gemstone.gemfire.cache.Declarable#init(java.util.Properties)
   */
  public void init(final Properties properties) {
  }
}
