/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Fri Sep 27 16:01:19 GMT 2019
 */

package com.adapterj.ext.vertx;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class SimpleHttpParametersResolver_ESTest_scaffolding {

  @org.junit.Rule 
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass 
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "com.adapterj.ext.vertx.SimpleHttpParametersResolver"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before 
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("file.encoding", "UTF-8"); 
    java.lang.System.setProperty("java.awt.headless", "true"); 
    java.lang.System.setProperty("java.io.tmpdir", "/var/folders/l8/ycz270nx7wb4vp0kf54vjs7w0000gn/T/"); 
    java.lang.System.setProperty("user.country", "US"); 
    java.lang.System.setProperty("user.dir", "/Users/york/Documents/Komodo/AdapterJ"); 
    java.lang.System.setProperty("user.home", "/Users/york"); 
    java.lang.System.setProperty("user.language", "en"); 
    java.lang.System.setProperty("user.name", "york"); 
    java.lang.System.setProperty("user.timezone", "Asia/Shanghai"); 
    java.lang.System.setProperty("sun.arch.data.model", "64"); 
  }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(SimpleHttpParametersResolver_ESTest_scaffolding.class.getClassLoader() ,
      "io.netty.util.internal.SystemPropertyUtil$1",
      "io.netty.util.internal.Cleaner",
      "io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueL1Pad",
      "io.netty.util.IllegalReferenceCountException",
      "com.adapterj.serverside.ServerSideException",
      "io.netty.buffer.ByteBufUtil$1",
      "io.netty.util.internal.logging.LocationAwareSlf4JLogger",
      "io.netty.util.internal.PlatformDependent0",
      "io.netty.buffer.ByteBufUtil$2",
      "io.netty.buffer.UnpooledByteBufAllocator$InstrumentedUnpooledDirectByteBuf",
      "io.netty.buffer.CompositeByteBuf$Component",
      "io.netty.util.internal.ReflectionUtil",
      "io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$ExitCondition",
      "io.netty.buffer.AbstractPooledDerivedByteBuf$PooledNonRetainedDuplicateByteBuf",
      "io.netty.buffer.DuplicatedByteBuf",
      "io.netty.util.concurrent.FastThreadLocalThread",
      "io.netty.util.internal.ThreadLocalRandom",
      "io.netty.util.internal.shaded.org.jctools.queues.ConcurrentCircularArrayQueue",
      "io.netty.buffer.UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf",
      "io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue",
      "io.netty.util.internal.PlatformDependent0$3",
      "io.netty.util.internal.PlatformDependent0$4",
      "io.netty.util.internal.PlatformDependent0$5",
      "io.netty.buffer.PooledByteBufAllocator",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueueProducerNodeRef",
      "io.netty.util.internal.CleanerJava6",
      "io.netty.util.internal.PlatformDependent0$1",
      "io.netty.util.internal.PlatformDependent0$2",
      "com.google.gson.LongSerializationPolicy",
      "io.netty.util.ByteProcessor$1",
      "io.vertx.core.http.HttpServerResponse",
      "io.netty.util.internal.logging.InternalLogger",
      "io.netty.buffer.PoolThreadCache$MemoryRegionCache$Entry",
      "io.netty.buffer.AbstractReferenceCountedByteBuf",
      "io.netty.buffer.UnpooledHeapByteBuf",
      "io.netty.util.internal.PlatformDependent$1",
      "com.adapterj.ext.vertx.HttpParametersResolver",
      "io.netty.util.internal.PlatformDependent$2",
      "io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueueProducerNodeRef",
      "io.netty.util.ResourceLeakDetector$DefaultResourceLeak",
      "io.netty.util.internal.shaded.org.jctools.queues.LinkedQueueNode",
      "io.netty.buffer.AdvancedLeakAwareByteBuf",
      "io.netty.buffer.FixedCompositeByteBuf",
      "io.netty.util.internal.LongCounter",
      "io.netty.util.ByteProcessor$4",
      "io.netty.util.ByteProcessor$3",
      "io.netty.util.internal.ThreadLocalRandom$1",
      "io.netty.util.internal.StringUtil",
      "io.netty.util.ByteProcessor$2",
      "io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$Consumer",
      "io.netty.util.internal.logging.AbstractInternalLogger",
      "io.vertx.core.streams.StreamBase",
      "io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueConsumerIndexField",
      "io.netty.util.internal.LongAdderCounter",
      "io.netty.util.Recycler$3",
      "io.netty.buffer.FixedCompositeByteBuf$Component",
      "com.adapterj.registry.SimpleRegistry",
      "io.netty.buffer.SimpleLeakAwareByteBuf",
      "io.netty.buffer.PoolArena$SizeClass",
      "io.netty.util.Recycler$1",
      "io.netty.util.Recycler$2",
      "io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueL2Pad",
      "io.netty.util.internal.UnpaddedInternalThreadLocalMap",
      "io.vertx.core.buffer.impl.BufferFactoryImpl",
      "io.netty.buffer.PooledByteBuf",
      "io.netty.buffer.PoolThreadCache$MemoryRegionCache$1",
      "io.netty.util.ReferenceCounted",
      "io.netty.util.ResourceLeakDetectorFactory$DefaultResourceLeakDetectorFactory",
      "io.netty.buffer.PooledDuplicatedByteBuf$1",
      "io.netty.buffer.AbstractPooledDerivedByteBuf",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueueConsumerIndexField",
      "com.google.gson.JsonNull",
      "io.netty.util.ResourceLeakDetectorFactory",
      "io.netty.buffer.UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf",
      "io.vertx.core.http.WebSocketFrame",
      "com.google.gson.LongSerializationPolicy$1",
      "com.google.gson.LongSerializationPolicy$2",
      "io.netty.buffer.ByteBufUtil$ThreadLocalDirectByteBuf",
      "io.netty.util.HashingStrategy$1",
      "com.google.gson.JsonObject",
      "io.vertx.core.http.ServerWebSocket",
      "io.netty.buffer.ByteBufAllocatorMetricProvider",
      "io.netty.buffer.ByteBufAllocator",
      "io.netty.buffer.UnreleasableByteBuf",
      "io.vertx.core.shareddata.impl.ClusterSerializable",
      "io.netty.buffer.ByteBufUtil$ThreadLocalUnsafeDirectByteBuf$1",
      "io.vertx.core.MultiMap",
      "io.netty.buffer.PoolChunkList",
      "io.netty.buffer.PoolSubpage",
      "io.netty.buffer.SwappedByteBuf",
      "io.netty.buffer.Unpooled",
      "com.adapterj.widget.ParametersResolver",
      "io.netty.buffer.PooledByteBufAllocator$1",
      "io.netty.buffer.ReadOnlyByteBufferBuf",
      "io.netty.util.NettyRuntime$AvailableProcessorsHolder",
      "io.netty.util.internal.PlatformDependent",
      "io.vertx.core.http.HttpVersion",
      "io.netty.util.Recycler$Handle",
      "io.netty.buffer.AbstractByteBufAllocator",
      "io.netty.buffer.WrappedUnpooledUnsafeDirectByteBuf",
      "io.vertx.core.Future",
      "io.netty.buffer.PoolArenaMetric",
      "io.netty.buffer.PooledDirectByteBuf$1",
      "io.netty.buffer.UnpooledUnsafeNoCleanerDirectByteBuf",
      "io.netty.buffer.PoolThreadCache",
      "io.netty.buffer.PooledDuplicatedByteBuf",
      "io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueuePad2",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueueL3Pad",
      "io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueuePad0",
      "io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueuePad1",
      "io.vertx.core.net.NetSocket",
      "io.netty.util.AsciiString$2",
      "io.vertx.core.http.HttpServerRequest",
      "io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueProducerIndexField",
      "io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueL3Pad",
      "io.netty.util.internal.InternalThreadLocalMap",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueue",
      "io.netty.buffer.PooledByteBufAllocatorMetric",
      "io.netty.util.AsciiString$1",
      "io.netty.util.ResourceLeak",
      "com.google.gson.FieldNamingPolicy",
      "io.netty.buffer.PooledUnsafeDirectByteBuf",
      "io.netty.util.HashingStrategy",
      "io.netty.util.internal.MathUtil",
      "io.vertx.core.streams.WriteStream",
      "io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueProducerLimitField",
      "com.google.gson.JsonPrimitive",
      "io.netty.util.ResourceLeakTracker",
      "io.vertx.core.net.SocketAddress",
      "io.vertx.core.Promise",
      "io.netty.buffer.AbstractUnpooledSlicedByteBuf",
      "io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$Supplier",
      "io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators",
      "io.netty.util.internal.CleanerJava6$1",
      "io.netty.buffer.EmptyByteBuf",
      "io.netty.buffer.AbstractPooledDerivedByteBuf$PooledNonRetainedSlicedByteBuf",
      "io.vertx.core.spi.BufferFactory",
      "io.vertx.core.Handler",
      "io.netty.util.ByteProcessor$IndexNotOfProcessor",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueueConsumerNodeRef",
      "io.netty.buffer.ReadOnlyByteBuf",
      "io.netty.buffer.PooledSlicedByteBuf$1",
      "io.vertx.core.json.DecodeException",
      "io.vertx.core.spi.FutureFactory",
      "io.vertx.core.impl.SucceededFuture",
      "io.netty.util.AsciiString",
      "io.vertx.core.http.Http2Settings",
      "io.netty.util.ResourceLeakDetector$Record",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueueProducerIndexField",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueueL2Pad",
      "io.netty.util.ResourceLeakDetectorFactory$DefaultResourceLeakDetectorFactory$1",
      "io.vertx.core.http.HttpFrame",
      "com.google.gson.FieldNamingStrategy",
      "io.vertx.core.streams.ReadStream",
      "io.netty.util.CharsetUtil",
      "io.vertx.core.json.JsonObject",
      "com.adapterj.logging.Log",
      "io.netty.buffer.PooledDirectByteBuf",
      "io.netty.buffer.AbstractReferenceCountedByteBuf$1",
      "io.netty.buffer.AbstractUnsafeSwappedByteBuf",
      "io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueue",
      "io.netty.util.internal.logging.Slf4JLoggerFactory",
      "io.vertx.core.spi.WebSocketFrameFactory",
      "io.netty.util.internal.shaded.org.jctools.queues.SpscLinkedQueue",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueueMidPad",
      "io.netty.buffer.UnsafeDirectSwappedByteBuf",
      "io.netty.util.NettyRuntime",
      "io.netty.buffer.PooledSlicedByteBuf",
      "io.netty.buffer.UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeDirectByteBuf",
      "io.netty.util.Recycler",
      "com.google.gson.JsonArray",
      "io.netty.buffer.UnpooledDirectByteBuf",
      "io.netty.util.internal.PlatformDependent0$10",
      "io.netty.buffer.UnpooledByteBufAllocator$UnpooledByteBufAllocatorMetric",
      "io.netty.buffer.UnpooledByteBufAllocator",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.AtomicReferenceArrayQueue",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueuePad0",
      "io.netty.buffer.PooledByteBufAllocator$PoolThreadLocalCache",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueuePad1",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueue",
      "io.netty.util.ResourceLeakDetector$Level",
      "io.netty.buffer.UnpooledByteBufAllocator$InstrumentedUnpooledHeapByteBuf",
      "io.netty.util.Recycler$DefaultHandle",
      "io.netty.util.internal.ReferenceCountUpdater",
      "io.vertx.core.ServiceHelper",
      "io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueueMidPad",
      "io.netty.util.internal.EmptyArrays",
      "io.netty.buffer.PooledUnsafeDirectByteBuf$1",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.BaseLinkedAtomicQueuePad2",
      "io.vertx.core.http.HttpConnection",
      "io.vertx.core.AsyncResult",
      "io.netty.buffer.UnsafeHeapSwappedByteBuf",
      "io.netty.util.ByteProcessor$IndexOfProcessor",
      "io.vertx.core.http.StreamPriority",
      "io.netty.util.ByteProcessor",
      "io.netty.util.internal.shaded.org.jctools.queues.ConcurrentCircularArrayQueueL0Pad",
      "io.netty.buffer.ByteBufAllocatorMetric",
      "io.vertx.core.json.JsonArray",
      "com.adapterj.registry.RegistryException",
      "io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueueConsumerNodeRef",
      "io.netty.buffer.PoolArena$DirectArena",
      "io.netty.util.internal.PlatformDependent$ThreadLocalRandomProvider",
      "io.netty.buffer.ReadOnlyUnsafeDirectByteBuf",
      "io.netty.util.internal.logging.InternalLoggerFactory",
      "io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess",
      "io.vertx.core.http.Cookie",
      "io.vertx.core.http.impl.WebSocketFrameFactoryImpl",
      "io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil$IndexedQueue",
      "io.netty.buffer.AdvancedLeakAwareCompositeByteBuf",
      "io.netty.buffer.PoolSubpageMetric",
      "io.netty.buffer.WrappedCompositeByteBuf",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueueProducerLimitField",
      "io.netty.buffer.UnpooledUnsafeHeapByteBuf",
      "io.netty.util.Recycler$WeakOrderQueue",
      "io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue",
      "io.netty.util.internal.ObjectUtil",
      "io.vertx.core.impl.FutureFactoryImpl",
      "io.netty.buffer.AbstractByteBuf",
      "io.netty.buffer.UnpooledUnsafeDirectByteBuf",
      "io.netty.buffer.PoolArena",
      "io.netty.util.AsciiString$CharEqualityComparator",
      "io.netty.util.internal.logging.InternalLogLevel",
      "io.netty.util.ResourceLeakDetector",
      "io.netty.buffer.ByteBufUtil$ThreadLocalDirectByteBuf$1",
      "io.netty.buffer.CompositeByteBuf",
      "io.netty.buffer.UnpooledDuplicatedByteBuf",
      "com.adapterj.ext.vertx.SimpleHttpParametersResolver",
      "io.vertx.core.buffer.Buffer",
      "io.netty.buffer.AbstractDerivedByteBuf",
      "io.netty.buffer.PoolChunkMetric",
      "io.netty.buffer.PoolChunk",
      "com.google.gson.internal.LazilyParsedNumber",
      "io.netty.buffer.UnpooledSlicedByteBuf",
      "io.netty.util.Recycler$Stack",
      "io.vertx.core.shareddata.Shareable",
      "com.google.gson.JsonElement",
      "io.vertx.core.streams.Pipe",
      "io.netty.buffer.ByteBuf",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.LinkedQueueAtomicNode",
      "io.netty.util.internal.logging.Slf4JLogger",
      "com.adapterj.registry.Registry",
      "io.netty.util.concurrent.FastThreadLocal",
      "io.netty.buffer.SimpleLeakAwareCompositeByteBuf",
      "io.netty.util.internal.OutOfDirectMemoryError",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.SpscLinkedAtomicQueue",
      "io.netty.util.internal.IntegerHolder",
      "io.netty.buffer.PoolThreadCache$MemoryRegionCache",
      "io.netty.buffer.ByteBufUtil$ThreadLocalUnsafeDirectByteBuf",
      "io.netty.buffer.WrappedByteBuf",
      "io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$WaitStrategy",
      "io.netty.util.internal.SystemPropertyUtil",
      "com.google.gson.FieldNamingPolicy$4",
      "com.google.gson.FieldNamingPolicy$3",
      "io.netty.buffer.ByteBufUtil",
      "com.google.gson.FieldNamingPolicy$5",
      "io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueueL1Pad",
      "io.netty.buffer.PoolChunkListMetric",
      "io.netty.buffer.CompositeByteBuf$1",
      "io.vertx.core.http.WebSocketBase",
      "io.netty.buffer.CompositeByteBuf$ByteWrapper",
      "io.netty.buffer.CompositeByteBuf$2",
      "io.vertx.core.http.HttpMethod",
      "com.google.gson.FieldNamingPolicy$2",
      "com.google.gson.FieldNamingPolicy$1"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(SimpleHttpParametersResolver_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "com.adapterj.ext.vertx.SimpleHttpParametersResolver",
      "com.adapterj.logging.Log",
      "com.adapterj.registry.SimpleRegistry",
      "com.adapterj.serverside.ServerSideException",
      "com.adapterj.registry.RegistryException"
    );
  }
}
