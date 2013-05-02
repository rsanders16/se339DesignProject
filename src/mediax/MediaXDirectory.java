package mediax;

import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * 
 * @author COM S 362 Group 7
 * 
 */
public class MediaXDirectory extends MediaXFile {

	/**
	 * Needed for proper inheritance from parent Class MediaXFile
	 */
	private static final long serialVersionUID = 6040146303193263835L;

	/**
	 * 
	 * @param pathname
	 * @throws InvalidAudioFrameException
	 * @throws ReadOnlyFileException
	 * @throws TagException
	 * @throws IOException
	 * @throws CannotReadException
	 * @throws CorruptedFileException
	 */
	public MediaXDirectory(final String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MediaXFile Documentation
	 */
	@Override
	public String getMetaData(final String tag) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean renameByMetaData() throws MediaXException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see MediaXFile Documentation
	 */
	@Override
	public boolean setMetaDataHelper(final String tag, final String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see MediaXFile Documentation
	 */
	@Override
	public String view() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see MediaXFile Documentation
	 */
	@Override
	public ArrayList<String> viewNullTags() {
		throw new UnsupportedOperationException();
	}

}
