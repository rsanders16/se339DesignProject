package mediax;

import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagException;

/**
 * An AudioFile, extended from MediaXFile, file is any File that can fit through
 * AudioFileFilter
 * 
 * @see AudioFileFilter
 * @see MediaXFile
 * @author COM S 363 Group 7
 * 
 */
public class AudioFile extends MediaXFile {

	/**
	 * Needed for proper inheritance from parent class MediaXFile
	 */
	private static final long serialVersionUID = -5447397507042213309L;

	/**
	 * The meta data of 'this' audio file. NOTE: This is taken from an external
	 * library called jAudioTagger
	 * 
	 * @see http://www.jthink.net/jaudiotagger/
	 */
	private org.jaudiotagger.audio.AudioFile metaData;

	/**
	 * Constructs a new AudioFile
	 * 
	 * @param pathname
	 *            The file path of the audio file to be constructed
	 * @throws MediaXException
	 *             If the file could not be constructed properly.
	 */
	public AudioFile(final String pathname) throws MediaXException {
		super(pathname);
		try {
			this.metaData = AudioFileIO.read(this);
		} catch (final CannotReadException e) {
			throw new MediaXException("Could not read audio file: "
					+ this.getAbsolutePath());
		} catch (final IOException e) {
			throw new MediaXException("Could not open audio file: "
					+ this.getAbsolutePath());
		} catch (final TagException e) {
			throw new MediaXException(
					"Could not read metadata for audio file: "
							+ this.getAbsolutePath());
		} catch (final ReadOnlyFileException e) {
			throw new MediaXException("Could not open read-only file: "
					+ this.getAbsolutePath());
		} catch (final InvalidAudioFrameException e) {
			throw new MediaXException("Could not read corrupted audio-file: "
					+ this.getAbsolutePath()
					+ " - Please remove this file from the directory.");
		}
	}

	/**
	 * Decodes raw user input as FieldKeys for MetaData tags
	 * 
	 * @param key
	 *            The user input to decode. Examples include "TITLE", "ARTIST",
	 *            ext.
	 * @return The true Field key
	 * @throws MediaXException
	 */
	private FieldKey decodeFieldKey(final String tag) throws MediaXException {
		if (tag.toUpperCase().equals("ARTIST")) {
			return FieldKey.ARTIST;
		} else if (tag.toUpperCase().equals("TITLE")) {
			return FieldKey.TITLE;
		} else if (tag.toUpperCase().equals("ALBUM")) {
			return FieldKey.ALBUM;
		} else if (tag.toUpperCase().equals("TRACK")) {
			return FieldKey.TRACK;
		} else if (tag.toUpperCase().equals("YEAR")) {
			return FieldKey.YEAR;
		}
		throw new MediaXException("Key Not Found");
	}

	/**
	 * @throws MediaXException
	 * @see MediaXFile Documentation
	 */
	@Override
	public String getMetaData(final String tag) throws MediaXException {
		try {
			return this.metaData.getTag().getFirst(this.decodeFieldKey(tag));
		} catch (final KeyNotFoundException e) {
			return "N/A";
		}
	}

	@Override
	public boolean renameByMetaData() throws MediaXException {
		String trackNo = this.getMetaData("TRACK");
		String title = this.getMetaData("TITLE");
		String artist = this.getMetaData("ARTIST");
		try {
			Integer.parseInt(trackNo);
		} catch (final NumberFormatException e) {
			trackNo = "0";
		}
		if (title.equals("N/A")) {
			title = "UnknownTitle";
		}
		if (artist.equals("N/A")) {
			artist = "UnknownArtist";
		}
		this.rename(this.getParentFile() + "/" + trackNo + ". " + title + " - "
				+ artist
				+ this.getName().substring(this.getName().lastIndexOf('.')));
		return true;
	}

	/**
	 * @throws MediaXException
	 * @see MediaXFile Documentation
	 */
	@Override
	public boolean setMetaDataHelper(final String tag, final String newValue)
			throws MediaXException {
		try {
			this.metaData.getTag().setField(this.decodeFieldKey(tag), newValue);
			this.metaData.commit();
			return true;
		} catch (final KeyNotFoundException e) {
			return false;
		} catch (final FieldDataInvalidException e) {
			return false;
		} catch (final CannotWriteException e) {
			return false;
		}
	}

	/**
	 * @throws MediaXException
	 * @see MediaXFile Documentation
	 */
	@Override
	public String view() throws MediaXException {
		String retVal = "";
		final String title = this.getMetaData("TITLE");
		final String artist = this.getMetaData("ARTIST");
		final String track = this.getMetaData("TRACK");
		final String album = this.getMetaData("ALBUM");
		final String year = this.getMetaData("YEAR");
		// retVal += "*****************************\n";
		retVal += "TITLE: " + title + "\n";
		retVal += "ARTIST: " + artist + "\n";
		retVal += "FILENAME: " + this.getName() + "\n";
		retVal += "TRACK: " + track + "\n";
		retVal += "YEAR: " + year + "\n";
		retVal += "ALBUM: " + album + "\n";
		retVal += "LOCKED?: " + !this.canWrite() + "\n";
		// retVal += "*****************************\n";

		return retVal;
	}

	@Override
	public ArrayList<String> viewNullTags() throws MediaXException {

		final ArrayList<String> list = new ArrayList<String>();

		final String title = this.getMetaData("TITLE");
		final String artist = this.getMetaData("ARTIST");
		final String track = this.getMetaData("TRACK");
		final String album = this.getMetaData("ALBUM");
		final String year = this.getMetaData("YEAR");

		if (title.equals("N/A")) {
			list.add("Title");
		}
		if (artist.equals("N/A")) {
			list.add("Artist");
		}
		if (track.equals("N/A")) {
			list.add("Track");
		}
		if (album.equals("N/A")) {
			list.add("Album");
		}
		if (year.equals("N/A")) {
			list.add("Year");
		}
		return list;
	}

}
