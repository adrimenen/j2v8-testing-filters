package odatesting.j2v8;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class Activator implements BundleActivator {

	private Thread t;

	@Override
	public void start(BundleContext bundleContext) {
		t = new Thread(new MyRunnable());
		t.start();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		t.interrupt();
	}

	public class MyRunnable implements Runnable {
		MyRunnable() {

		}

		public void run() {
			FilterTemperatureV4 v4 = new FilterTemperatureV4();
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
					v4.main(null);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
