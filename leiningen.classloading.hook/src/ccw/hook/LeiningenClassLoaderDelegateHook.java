package ccw.hook;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.osgi.framework.adaptor.BundleClassLoader;
import org.eclipse.osgi.framework.adaptor.BundleData;
import org.eclipse.osgi.framework.adaptor.ClassLoaderDelegateHook;

public class LeiningenClassLoaderDelegateHook implements
		ClassLoaderDelegateHook {

	@Override
	public Class<?> preFindClass(String name, BundleClassLoader classLoader,
			BundleData data) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> postFindClass(String name, BundleClassLoader classLoader,
			BundleData data) throws ClassNotFoundException {
		if (data.getSymbolicName().equals("ccw.core"))
			System.out.println("======postFindClass(name=" + name +", data.getSymbolicName()=" + data.getSymbolicName() + ")");
		return null;
	}

	@Override
	public URL preFindResource(String name, BundleClassLoader classLoader,
			BundleData data) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL postFindResource(String name, BundleClassLoader classLoader,
			BundleData data) throws FileNotFoundException {
		if (data.getSymbolicName().equals("ccw.core"))
			System.out.println("======postFindResource(name=" + name +", data.getSymbolicName()=" + data.getSymbolicName() + ")");
		return null;
	}

	@Override
	public Enumeration<URL> preFindResources(String name,
			BundleClassLoader classLoader, BundleData data)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<URL> postFindResources(String name,
			BundleClassLoader classLoader, BundleData data)
			throws FileNotFoundException {
		if (data.getSymbolicName().equals("ccw.core"))
			System.out.println("======postFindResources(name=" + name +", data.getSymbolicName()=" + data.getSymbolicName() + ")");
		return null;
	}

	@Override
	public String preFindLibrary(String name, BundleClassLoader classLoader,
			BundleData data) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postFindLibrary(String name, BundleClassLoader classLoader,
			BundleData data) {
		if (data.getSymbolicName().equals("ccw.core"))
			System.out.println("======postFindLibrary(name=" + name +", data.getSymbolicName()=" + data.getSymbolicName() + ")");
		return null;
	}

}
