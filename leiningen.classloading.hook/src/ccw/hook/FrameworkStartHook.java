package ccw.hook;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Properties;

import org.eclipse.osgi.baseadaptor.BaseAdaptor;
import org.eclipse.osgi.baseadaptor.hooks.AdaptorHook;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public class FrameworkStartHook implements AdaptorHook {

	public FrameworkStartHook() {
	}
	
	private void initRequire() {
		Var require;
		
		long start = System.currentTimeMillis();
		require = RT.var("clojure.core", "require");
	    ((Var)require).invoke(Symbol.intern("ccw.hook.leiningenclassloading"));
		long stop = System.currentTimeMillis();
		System.out.println("FrameworkStartHook started in " + (stop - start) + "ms.");
		
	}

	@Override
	public void initialize(BaseAdaptor adaptor) {
	}

	/*
	 *  Principe : frameworkStart met ˆ jour pour le bundle le fichier ccw-additional-classpath.txt
	 *             Ensuite le ClassLoadingHook ajoute les elements trouves dans le classpath au demarrage du bundle
	 *  Optimisation :
	 *             frameworkStart copie le fichier project.clj apres avoir cree ccw-additional-classpath.txt
	 *             si le fichier project.clj a change, ou s'il n'existe pas, ou si ccw-additional-classpath.txt n'existe pas
	 *             alors frameworkStart invoque leiningen pour faire le boulot
	 */
	@Override
	public void frameworkStart(BundleContext context) throws BundleException {
		System.out.println("+*+*+*+*+*+*+*+* frameworkStart *+*+*+*+*+*+*+*+*+");

		for (final Bundle b: context.getBundles()) {
			if (b.getHeaders().get("Leiningen-Aware") != null) {
				prepareBundleClassLoader(b);
			}
		}
	}
	
	private void log(String level, Bundle b, String s) {
		System.out.println("Leiningen-Aware bundle " + b.getSymbolicName() + " " + level + ": " + s);
	}
	
	private void logError(Bundle b, String s) {	log("error", b, s); }
	private void logInfo(Bundle b, String s) { log("info", b, s); }
	private void logDebug(Bundle b, String s) { log("debug", b, s); }
	
	private void prepareBundleClassLoader(Bundle b) {
		logDebug(b, "*.*.*.*.*.*. bundle state: " + b.getState() + " .*.*.*.*.*.*");
		logDebug(b, "+/+/+/+/+/+/+ project.clj entry: " + b.getEntry("/project.clj") + "+/+/+/+/+/+/+/+");

		URL bundleProjectCljUrl = b.getEntry("/project.clj");
		
		if (bundleProjectCljUrl == null) {
			logError(b, "has no file project.clj");
			return;
		}
		
	    String bundleProjectClj = null;
		try {
			bundleProjectClj = Utils.readFile(bundleProjectCljUrl.openConnection().getInputStream());
		} catch (IOException e) {
			logError(b, "error while trying to read content of project.clj");
			e.printStackTrace();
		}
		
	    if (bundleProjectClj == null) {
	    	logError(b, "unable to open project.clj");
	    	return;
	    }
	    
	    File dataFile = b.getDataFile("project.clj");
	    if (dataFile == null || !dataFile.exists()) {
	    	logInfo(b, "cached project.clj file missing. Will recompute dependencies.");
	    	computeDeps(b, bundleProjectClj);
	    	return;
	    }
	    
	    String cachedProjectClj = Utils.readFile(dataFile);
	    
	    if (!cachedProjectClj.equals(bundleProjectClj)) {
	    	logInfo(b, "cached project.clj is stale");
	    	logDebug(b, "cached content: " + cachedProjectClj);
	    	logDebug(b, "expected content: " + bundleProjectClj);
	    	computeDeps(b, bundleProjectClj);
	    	return;
	    } 
	  
	    File depsFile = b.getDataFile(LeiningenClassLoadingHook.DEPS_FILE_NAME);
	    
	    if (depsFile == null) {
	    	logInfo(b, "dependencies file " + LeiningenClassLoadingHook.DEPS_FILE_NAME + " not found in bundle data directory. Will recompute dependencies.");
	    	computeDeps(b, bundleProjectClj);
	    	return;
	    } else if (!depsFile.exists()) {
	    	logInfo(b, "dependencies file " + depsFile.getAbsolutePath() + " not found. Will recompute dependencies.");
	    	computeDeps(b, bundleProjectClj);
	    	return;
	    }
	    
	    // TODO do we check dependencies ?
	    // TODO if some dependencies checks fail, do we recompute ??
	}

	private void computeDeps(Bundle b, String projectClj) {
		
	    initRequire();
	    
	    Var findAndLoadDeps = RT.var("ccw.hook.leiningenclassloading", "find+download-deps");
	    Var spitDeps = RT.var("ccw.hook.leiningenclassloading", "spit-deps");
	    Var spitProjectClj = RT.var("ccw.hook.leiningenclassloading", "spit-project-clj");
	    
		@SuppressWarnings("unchecked")
		Collection<String> depsStrings =  (Collection<String>) findAndLoadDeps.invoke(projectClj);

		spitProjectClj.invoke(projectClj, b.getDataFile("project.clj"));
		spitDeps.invoke(depsStrings, b.getDataFile(LeiningenClassLoadingHook.DEPS_FILE_NAME));
	}
	
	@Override
	public void frameworkStop(BundleContext context) throws BundleException {
	}

	@Override
	public void frameworkStopping(BundleContext context) {
	}

	@Override
	public void addProperties(Properties properties) {
	}

	@Override
	public URLConnection mapLocationToURLConnection(String location)
			throws IOException {
		return null;
	}

	@Override
	public void handleRuntimeError(Throwable error) {
	}

	@Override
	public FrameworkLog createFrameworkLog() {
		return null;
	}

}
