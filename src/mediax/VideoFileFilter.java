package mediax;

import java.io.File;
import java.io.FileFilter;

public class VideoFileFilter implements FileFilter {

	@Override
	public boolean accept(final File file) {
		final String name = file.getName();
		if (name.endsWith(".mp4") || name.endsWith(".avi")) {
			return true;
		}
		return false;
	}

}
