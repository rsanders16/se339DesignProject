package mediax;

import java.util.ArrayList;

public class VideoFile extends MediaXFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5899832089085175252L;

	public VideoFile(final String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMetaData(final String tag) throws MediaXException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean renameByMetaData() throws MediaXException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean setMetaDataHelper(final String tag, final String value)
			throws MediaXException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String view() throws MediaXException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ArrayList<String> viewNullTags() throws MediaXException {
		throw new UnsupportedOperationException();
	}

}
