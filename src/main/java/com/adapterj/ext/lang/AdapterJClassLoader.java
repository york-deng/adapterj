package com.adapterj.ext.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.adapterj.ext.io.AdapterJObjectInputStream;
import com.adapterj.logging.Debugger;
import com.adapterj.logging.Log;

@SuppressWarnings("unused")
public class AdapterJClassLoader extends java.lang.ClassLoader {
	
	private static final boolean DEBUG = Debugger.DEBUG;
	
    private static final String TAG = AdapterJClassLoader.class.getName();

    private static final Map<String, byte[]> classMap  = new ConcurrentHashMap<>();

	private static final Map<String, String> ignoreMap = new ConcurrentHashMap<>();

	private Kryo _kryo = null;
    
	private String _classPath = null;
	
    private AdapterJClassLoader() {
    	/*
    	_kryo = new Kryo();
    	_kryo.setClassLoader(this);
    	_kryo.setReferences(false);
    	_kryo.setRegistrationRequired(false);
    	_kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
    	*/
    }

    private static class SingletonHolder {
        public static final AdapterJClassLoader instance = new AdapterJClassLoader();
    }

    public static AdapterJClassLoader getInstance() {
        return SingletonHolder.instance;
    }

    public final Class<?> findClassByBytes(final String className, final byte[] classBytes) {
        return defineClass(className, classBytes, 0, classBytes.length);
    }

    public final Object copyObject(final Class<?> clazz, final byte[] objectBytes) throws IOException, ClassNotFoundException {
    	java.util.Date begin = new java.util.Date();
    	
		final ByteArrayInputStream in1 = new ByteArrayInputStream(objectBytes);
		final ObjectInputStream in2 = new AdapterJObjectInputStream(in1, this);
		final Object inObject = in2.readObject();
		in2.close();
		in1.close();
		
		java.util.Date end = new java.util.Date();
		final long cost = end.getTime() - begin.getTime();
		if (DEBUG) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String format = "(%s:%d) %s: doGet: cost is %d";
            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), cost));
		}
		
		return inObject;
    }
    
    /**
     * Deep copying serialized object in different ClassLoader.
     * 
     * @param clazz
     * @param original
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public final Object copyObject(final Class<?> clazz, final Serializable original) throws IOException, ClassNotFoundException {
    	java.util.Date begin = new java.util.Date();
    	
    	Object target  = null;
    	if (_kryo == null) {
			final ByteArrayOutputStream out1 = new ByteArrayOutputStream();
			final ObjectOutputStream out2 = new ObjectOutputStream(out1);
			out2.writeObject(original);
			final byte[] objectBytes = out1.toByteArray();
			out2.close();
			out1.close();
			
			final ByteArrayInputStream in1 = new ByteArrayInputStream(objectBytes);
			final ObjectInputStream in2 = new AdapterJObjectInputStream(in1, this);
			target = in2.readObject();
			in2.close();
			in1.close();
    	} else {
	        Registration registration = null;
	        try {
	        	registration = _kryo.getRegistration(clazz);
	        } catch(Throwable ignore) {
	        	// ignore
	        }
	        if (registration == null) {
	        	_kryo.register(clazz);
	        }
	        
	        final ByteArrayOutputStream out1 = new ByteArrayOutputStream();
	        final Output out2 = new Output(out1, 100000000);
	        _kryo.writeObject(out2, original);
			final byte[] objectBytes = out2.toBytes();
			out2.close();
			out1.close();
			
			final ByteArrayInputStream in1 = new ByteArrayInputStream(objectBytes);
			final Input in2 = new Input(in1);
			target = _kryo.readObject(in2, clazz);
			in2.close();
			in1.close();
    	}
		
		java.util.Date end = new java.util.Date();
		final long cost = end.getTime() - begin.getTime();
		if (DEBUG) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String format = "(%s:%d) %s: copyObject: cost is %d";
            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), cost));
		}
		
		return (target);
    }
    
    /**
     * Shallow copying object in different ClassLoader.
     * 
     * @param clazz
     * @param original
     * @return
     */
    public final Object copyObject(final Class<?> clazz, final Object original) {
        try {
            Object newInstance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = original.getClass().getDeclaredFields();
            for (Field oldInstanceField : fields) {
                String fieldName = oldInstanceField.getName();
                if (fieldName != null && !fieldName.equals("serialVersionUID")) {
	                oldInstanceField.setAccessible(true);
	                Field newInstanceField = newInstance.getClass().getDeclaredField(fieldName);
	                newInstanceField.setAccessible(true);
	                newInstanceField.set(newInstance, oldInstanceField.get(original));
                }
            }
            return newInstance;
        } catch (Throwable e) {
            Log.e(TAG, "catch a Throwable, return a null !!! ", e);
        }
        return (null);
    }

    public final void loadClasses(String classPath) {
        if (classPath.endsWith(File.separator)) {
            _classPath = classPath;
        } else {
        	_classPath = classPath + File.separator;
        }
        readClassFile();
        readJarFile();
    }

    // ClassLoader methods
    
    /**
     * 遵守双亲委托规则
     */
    @Override
    public Class<?> findClass(String name) {
        try {
            byte[] result = getClass(name);
            if (result == null) {
                throw new ClassNotFoundException();
            } else {
                return defineClass(name, result, 0, result.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // protected methods
    
    protected static final boolean addClass(String className, byte[] byteCode) {
        if (!classMap.containsKey(className)) {
            classMap.put(className, byteCode);
            return true;
        }
        return false;
    }

    /**
     * 这里仅仅卸载了myclassLoader的classMap中的class,虚拟机中的
     * Class的卸载是不可控的
     * 自定义类的卸载需要ClassLoader不存在引用等条件
     * 
     * @param className
     * @return
     */
    protected static final boolean removeClass(String className) {
        if (classMap.containsKey(className)) {
            classMap.remove(className);
            return true;
        }
        return false;
    }

    protected final byte[] getClass(String className) {
        if (classMap.containsKey(className)) {
            return classMap.get(className);
        } else {
            return null;
        }
    }

    protected final void readClassFile() {
        File[] files = new File(_classPath).listFiles();
        if (files != null) {
            for (File file : files) {
                scanClassFile(file);
            }
        }
    }

    protected final void scanClassFile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                try {
                    final String className = file.getAbsolutePath().replace(_classPath, "").replace(File.separator, ".").replace(".class", "");
                    //if (!SimpleListHTMLViewAccelerator1.class.getName().equals(className)) { // 仅用于测试，需要从属性文件中读取不自动加载的类列表，放入AdapterJClassLoader
                    	final byte[] byteCode = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                    	addClass(className, byteCode);
                    //}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    scanClassFile(f);
                }
            }
        }
    }

    protected final void readJarFile() {
        final File[] files = new File(_classPath).listFiles();
        if (files != null) {
            for (File file : files) {
                scanJarFile(file);
            }
        }
    }

    protected final void readJar(JarFile jar) throws IOException {
        final Enumeration<JarEntry> en = jar.entries();
        while (en.hasMoreElements()) {
            final JarEntry je = en.nextElement();
            je.getName();
            final String name = je.getName();
            if (name.endsWith(".class")) {
             // String className = name.replace(File.separator, ".").replace(".class", "");
                String className = name.replace("\\", ".").replace("/", ".").replace(".class", "");
                InputStream input = null;
                ByteArrayOutputStream baos = null;
                try {
                    input = jar.getInputStream(je);
                    baos = new ByteArrayOutputStream();
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int bytesNumRead = 0;
                    while ((bytesNumRead = input.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesNumRead);
                    }
                    addClass(className, baos.toByteArray());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (baos != null) {
                        baos.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                }
            }
        }
    }

    protected final void scanJarFile(final File file) {
        if (file.exists()) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try {
                    readJar(new JarFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    scanJarFile(f);
                }
            }
        }
    }

    protected final void addJar(final String jarPath) throws IOException {
    	final File file = new File(jarPath);
        if (file.exists()) {
            JarFile jar = new JarFile(file);
            readJar(jar);
        }
    }
}
