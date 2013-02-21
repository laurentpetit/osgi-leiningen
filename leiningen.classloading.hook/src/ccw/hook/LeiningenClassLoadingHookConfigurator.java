package ccw.hook;

import org.eclipse.osgi.baseadaptor.HookConfigurator;
import org.eclipse.osgi.baseadaptor.HookRegistry;

public class LeiningenClassLoadingHookConfigurator implements HookConfigurator {

	@Override
	public void addHooks(HookRegistry hookRegistry) {
		hookRegistry.addClassLoadingHook(new LeiningenClassLoadingHook());
		hookRegistry.addAdaptorHook(new FrameworkStartHook());
		hookRegistry.addClassLoaderDelegateHook(new LeiningenClassLoaderDelegateHook());
		System.out.println("%%%%%%%%%%%%%%%%%%%% %%%%%%%%%%%%%%%%% yeppee, I was asked to add hooks ! I'm sooo happy !");
	}

}
