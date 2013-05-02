package mediax;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 
 * @author COM S 362 Group 7
 * @version 1.0
 * 
 */
public class MediaX {

	/**
	 * List of files resulting from the last ls command
	 */
	private List<MediaXFile> cache;

	/**
	 * A flag to determine if a listing method has been called. If not then no
	 * other methods can be called.
	 */
	private boolean listingMethodHasBeenCalled = false;

	/**
	 * the currently selected audio file
	 */
	private MediaXFile selectedFile;

	/**
	 * The current working directory
	 */
	private File workingDirectory;

	public MediaX(final File startingDirectory) throws MediaXException {
		this.workingDirectory = startingDirectory;
		if (!this.workingDirectory.exists()) {
			throw new MediaXException("The home directory could not be loaded");
		}
	}

	/**
	 * Change the directory to the one specified
	 * 
	 * @param dir
	 *            the target directory to change to
	 * @return true if changing was successful
	 */
	public boolean changeDirectory(final String dir) {
		File file;
		if (dir.equals("..") || dir.equals("../")) {
			this.goUpOneDirectory();
			return true;
		}
		if (dir.equals("../../") || dir.equals("../..")) {
			this.goUpOneDirectory();
			this.goUpOneDirectory();
			return true;
		}
		if ((file = new File(dir)).exists()) {
			this.workingDirectory = file;
			this.listingMethodHasBeenCalled = false;
			return true;
		} else if ((file = new File(this.workingDirectory + "\\" + dir))
				.exists()) {
			this.listingMethodHasBeenCalled = false;
			this.workingDirectory = file;
			return true;
		}
		return false;
	}

	/**
	 * Deletes the currently selected file.
	 * 
	 * @return True if the file is deleted
	 */
	public boolean deleteSelectedFile() {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile.delete();
	}

	/**
	 * Export Current Directory's Data.
	 * 
	 * @return All the Meta Data Of the Current Directory
	 * @throws MediaXException
	 */
	public boolean exportAllCurrentDirectorysMetaDataToPDF()
			throws MediaXException {
		// Print a Header
		String toPrint = "MediaX Version 1.0 Exportation Document\n";
		toPrint += "DIRECTORY: " + this.printWorkingDirectory() + "\n";
		final DateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		final Date date = new Date();
		toPrint += "TIMESTAMP OF PRINTOUT: " + dateFormat.format(date);
		toPrint += "\n";
		toPrint += ("\n");
		toPrint += ("                            Audio Files                                  \n");
		toPrint += ("                            -----------                                  \n");

		// Print all Audio Files
		int i = 1;
		for (final MediaXFile f : this.listingAudioOnly()) {
			toPrint += i + ".)\n";
			try {
				toPrint += (f.view() + "\n");
			} catch (final ClassCastException e) {

			}
			i++;
		}
		toPrint += ("\n");
		toPrint += ("                            Image Files                                  \n");
		toPrint += ("                            -----------                                  \n");

		// Print all Image Files
		i = 0;
		for (final MediaXFile f : this.listingImagesOnly()) {
			toPrint += i + ".)\n";
			try {
				toPrint += (f.view() + "\n");
			} catch (final ClassCastException e) {

			}
			i++;
		}

		// Print file to PDF
		try {
			final String text = toPrint;
			final Document document = new Document(PageSize.A4, 36, 72, 108,
					180);
			PdfWriter.getInstance(document, new FileOutputStream("temp.pdf"));
			document.open();
			document.add(new Paragraph(text));
			document.close();

			try {
				Runtime.getRuntime().exec(
						//
						String.format("cmd /C \"start %1s\"", "temp.pdf"),
						null, //
						null);
			} catch (final Exception e) {
				e.printStackTrace();
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public List<String> getAllArtistsInCurrentDirectory()
			throws MediaXException {
		final ArrayList<String> toReturn = new ArrayList<String>();
		for (final MediaXFile f : this.listingAudioOnly()) {
			final String artist = f.getMetaData("ARTIST");
			if (!artist.equals("NA")) {
				toReturn.add(artist);
			}
		}
		return toReturn;
	}

	/**
	 * Move to the parent directory
	 * 
	 * @return true if the move was successful
	 */
	public boolean goUpOneDirectory() {
		final String inCaseOfNull = this.workingDirectory.getAbsolutePath();
		this.workingDirectory = this.workingDirectory.getParentFile();
		if (this.workingDirectory == null) {
			this.workingDirectory = new File(inCaseOfNull);
			return false;
		}
		this.listingMethodHasBeenCalled = false;
		return true;
	}

	/**
	 * Lists the metaman files in the current directory
	 * 
	 * @return list of all of the compatible files
	 * @throws MediaXException
	 */
	public List<MediaXFile> listing() throws MediaXException {
		final ArrayList<MediaXFile> list = new ArrayList<MediaXFile>();
		list.addAll(this.listingDirectoriesOnly());
		list.addAll(this.listingAudioOnly());
		list.addAll(this.listingImagesOnly());
		this.cache = list;
		this.listingMethodHasBeenCalled = true;
		return list;
	}

	/**
	 * List only the audio files
	 * 
	 * @return list of all supported audio files in the directory
	 * @throws MediaXException
	 */
	public List<MediaXFile> listingAudioOnly() throws MediaXException {
		final List<MediaXFile> list = new ArrayList<MediaXFile>();
		for (final File f : this.workingDirectory
				.listFiles(new AudioFileFilter())) {
			list.add(new AudioFile(f.getAbsolutePath()));
		}
		this.cache = list;
		this.listingMethodHasBeenCalled = true;
		return list;
	}

	/**
	 * List the directories in the current working directory
	 * 
	 * @return list of all the directories
	 */
	public List<MediaXFile> listingDirectoriesOnly() {
		final ArrayList<MediaXFile> list = new ArrayList<MediaXFile>();
		for (final File f : this.workingDirectory.listFiles()) {
			if (this.workingDirectory.isDirectory()) {
				list.add(new MediaXDirectory(f.getAbsolutePath()));
			}
		}
		this.cache = list;
		this.listingMethodHasBeenCalled = true;
		return list;
	}

	public List<MediaXFile> listingImagesOnly() throws MediaXException {
		final List<MediaXFile> list = new ArrayList<MediaXFile>();
		for (final File f : this.workingDirectory
				.listFiles(new ImageFileFilter())) {
			list.add(new ImageFile(f.getAbsolutePath()));
		}
		this.cache = list;
		this.listingMethodHasBeenCalled = true;
		return list;
	}

	public List<MediaXFile> listingVideosOnly() throws MediaXException {
		final List<MediaXFile> list = new ArrayList<MediaXFile>();
		for (final File f : this.workingDirectory
				.listFiles(new VideoFileFilter())) {
			list.add(new VideoFile(f.getAbsolutePath()));
		}
		this.cache = list;
		this.listingMethodHasBeenCalled = true;
		return list;
	}

	/**
	 * Returns the null tags of the currently selected MediaXFile
	 * 
	 * @return a List MetaDataTags (in string form) which are empty within the
	 *         currently selected file.
	 * @throws MediaXException
	 */
	public List<String> listsNullMetaDataTagsOfSelectedFile()
			throws MediaXException {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile.viewNullTags();

	}

	/**
	 * Locks the selected file
	 * 
	 * @return true if locked
	 */
	public boolean lockSelectedFile() {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile.lock();
	}

	/**
	 * Modifies the metadata tag with the newValue
	 * 
	 * @param tag
	 *            The meta data tag to be changed
	 * @param newValue
	 *            The value the tag will be changed to
	 * @return
	 * @throws MediaXException
	 */
	public boolean modMetaDataOfSelectedFile(final String tag,
			final String newValue) throws MediaXException {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile.setMetaData(tag, newValue);
	}

	/**
	 * Opens the selected file
	 * 
	 * @return true if the file is opened
	 */
	public boolean openSelectedFile() {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile.open();
	}

	/**
	 * Prints the current working directory's absolute path
	 * 
	 * @return the absolute path of the file
	 */
	public String printWorkingDirectory() {
		return this.workingDirectory.getAbsolutePath();
	}

	/**
	 * Renames the selected file
	 * 
	 * @return True if the filename was changed
	 */
	public boolean renameSelectedFile(final String newName) {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile
				.rename(this.printWorkingDirectory().toString()
						+ "\\"
						+ newName.trim()
						+ this.selectedFile
								.getName()
								.substring(
										this.selectedFile.getName()
												.lastIndexOf('.')).trim());
	}

	public boolean renameSelectedFileToItsMetaData() throws MediaXException {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile.renameByMetaData();
	}

	/**
	 * Sets the selected audio file
	 * 
	 * @param index
	 *            position of the file in the cache(this is the index that is
	 *            printed in the veiw)
	 * @return true if the file could be selected
	 * @throws MediaXException
	 */
	public boolean setSelectedFile(final int index) throws MediaXException {
		if (!this.listingMethodHasBeenCalled) {
			throw new MediaXException(
					"Cannot preform action.  No listing method has been called.");
		}
		this.selectedFile = this.cache.get(index);
		return true;
	}

	/**
	 * Unlocks the selected file
	 * 
	 * @return true if unlocked
	 */
	public boolean unlockSelectedFile() {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile.unlock();
	}

	/**
	 * Gets a string representation of the meta data
	 * 
	 * @return the meta data of the file
	 * @throws MediaXException
	 */
	public String viewMetaDataOfSelectedFile() throws MediaXException {
		if (this.selectedFile == null) {
			throw new NullPointerException();
		}
		return this.selectedFile.view();
	}

}
