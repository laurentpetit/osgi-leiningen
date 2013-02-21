package ccw.hook;

import java.io.File;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.osgi.baseadaptor.BaseData;
import org.eclipse.osgi.baseadaptor.bundlefile.BundleEntry;
import org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook;
import org.eclipse.osgi.baseadaptor.loader.BaseClassLoader;
import org.eclipse.osgi.baseadaptor.loader.ClasspathEntry;
import org.eclipse.osgi.baseadaptor.loader.ClasspathManager;
import org.eclipse.osgi.framework.adaptor.BundleProtectionDomain;
import org.eclipse.osgi.framework.adaptor.ClassLoaderDelegate;
import org.osgi.framework.BundleException;

public class LeiningenClassLoadingHook implements ClassLoadingHook {

	public static final String DEPS_FILE_NAME = "ccw-additional-classpath.txt";
	
	private Object require;
	
	public LeiningenClassLoadingHook() {
	}
	
	// TODO If Eclipse is moved around, the path to m2 local repo should remain valid ... 
	@Override
	public boolean addClassPathEntry(ArrayList<ClasspathEntry> cpEntries,
			String cp, ClasspathManager hostmanager, BaseData sourcedata,
			ProtectionDomain sourcedomain) {
		
		try {
			if (!cp.equals(".")) return false;
			
			if (sourcedata.getManifest().get("Leiningen-Aware") == null) return false;
			
			File f = sourcedata.getDataFile(DEPS_FILE_NAME);
			System.out.println("==**==**==**==**==**==** dataFile: " + f.getAbsolutePath());
			
			if (f == null) { 
				System.out.println("Where are you running Eclipse from? Your installation does not support bundle data files"); 
				return false; 
			}
			
			if (!f.exists()) return false;
			
			// A file is found, will load its dependencies
			List<String> paths = Utils.readFileLines(f);
			
			if (paths != null && paths.size() > 0) {
				for (String path: paths) {
			    	ClasspathEntry cpe = hostmanager.getExternalClassPath(path, sourcedata, sourcedomain);
			    	// TODO TESTER SI null => probleme !!! 
			    	System.out.println("adding ClasspathEntry " + cpe);
			    	cpEntries.add(cpe);
			    }
				return true;
			} else {
				return false;
			}
		} catch (BundleException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public byte[] processClass(String name, byte[] classbytes,
			ClasspathEntry classpathEntry, BundleEntry entry,
			ClasspathManager manager) {
		return null;
	}

	@Override
	public String findLibrary(BaseData data, String libName) { return null;	}

	@Override
	public ClassLoader getBundleClassLoaderParent() { return null; }

	@Override
	public BaseClassLoader createClassLoader(ClassLoader parent,
			ClassLoaderDelegate delegate, BundleProtectionDomain domain,
			BaseData data, String[] bundleclasspath) {
		return null;
	}

	@Override
	public void initializedClassLoader(BaseClassLoader baseClassLoader,	BaseData data) { }

}
