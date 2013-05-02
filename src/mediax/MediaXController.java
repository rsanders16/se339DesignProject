package mediax;

import java.io.File;
import java.util.List;

/**
 * A controller for the MediaX Object. All method calls to MediaX from any view
 * must go through this.
 * 
 * @author COM S 362 GROUP 7
 * 
 */
public class MediaXController {

	private final MediaX mediaX;

	/**
	 * Creates a MediaX Controller Object.
	 * 
	 * @param mediaX
	 *            - The MediaX Object that this controller will Manipulate.
	 * @throws MediaXException
	 */
	public MediaXController(final File startingDirectory)
			throws MediaXException {
		this.mediaX = new MediaX(startingDirectory);
	}

	/**
	 * Changes the directory that MetaData is currently in to 'newPath'
	 * 
	 * @param newPath
	 *            The path that MetaData's current working directory will be
	 *            changed to.
	 * @return True if the change was made successfully.
	 */
	public boolean changeDirectory(final String newPath) {
		return this.mediaX.changeDirectory(newPath);
	}

	/**
	 * Deletes the currently selected file.
	 * 
	 * @return True if the file is deleted
	 */
	public boolean deleteSelectedFile() {
		return this.mediaX.deleteSelectedFile();
	}

	public boolean exportAllCurrentDirectorysMetaDataToPDF()
			throws MediaXException {
		return this.mediaX.exportAllCurrentDirectorysMetaDataToPDF();
	}

	public List<String> getAllArtistsInCurrentDirectory()
			throws MediaXException {
		return this.mediaX.getAllArtistsInCurrentDirectory();
	}

	/**
	 * Bumps the current working directory up one level
	 * 
	 * @return True is the move up was successful.
	 */
	public boolean goUpOneDirectory() {
		return this.mediaX.goUpOneDirectory();
	}

	/**
	 * Lists all the files in the current directory.
	 * 
	 * @return - A list of files/directories in the current directory
	 * @throws MediaXException
	 */
	public List<MediaXFile> listing() throws MediaXException {
		return this.mediaX.listing();
	}

	/**
	 * List only the audio files
	 * 
	 * @return list of all supported audio files in the directory
	 * @throws MediaXException
	 */
	public List<MediaXFile> listingAudioOnly() throws MediaXException {
		return this.mediaX.listingAudioOnly();
	}

	/**
	 * List the directories in the current working directory
	 * 
	 * @return list of all the directories
	 */
	public List<MediaXFile> listingDirectoiesOnly() {
		return this.mediaX.listingDirectoriesOnly();
	}

	public List<MediaXFile> listingImagesOnly() throws MediaXException {
		return this.mediaX.listingImagesOnly();
	}

	public List<MediaXFile> listingVideosOnly() throws MediaXException {
		return this.mediaX.listingVideosOnly();
	}

	/**
	 * Returns the null tags of the currently selected MediaXFile
	 * 
	 * @return a List MetaDataTags (in string form) which are empty within the
	 *         currently selected file.
	 * @throws MediaXException
	 */
	public List<String> listNullMetaDataTagsOfSelectedFile()
			throws MediaXException {
		return this.mediaX.listsNullMetaDataTagsOfSelectedFile();
	}

	/**
	 * Locks the selected file
	 * 
	 * @return true if locked
	 */
	public boolean lockSelectedFile() {
		return this.mediaX.lockSelectedFile();

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
		return this.mediaX.modMetaDataOfSelectedFile(tag, newValue);
	}

	/**
	 * Opens the selected file
	 * 
	 * @return true if the file is opened
	 */
	public boolean openSelectedFile() {
		return this.mediaX.openSelectedFile();
	}

	/**
	 * Prints the current working directory's absolute path
	 * 
	 * @return the absolute path of the file
	 */
	public String printWorkingDirectory() {
		return this.mediaX.printWorkingDirectory();
	}

	/**
	 * Renames the selected file
	 * 
	 * @return True if the filename was changed
	 */
	public boolean renameSelectedFile(final String newName) {
		return this.mediaX.renameSelectedFile(newName);
	}

	public boolean renameSelectedFileToItsMetaData() throws MediaXException {
		return this.mediaX.renameSelectedFileToItsMetaData();

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
		return this.mediaX.setSelectedFile(index);
	}

	/**
	 * Unlocks the selected file
	 * 
	 * @return true if unlocked
	 */
	public boolean unlockSelectedFile() {
		return this.mediaX.unlockSelectedFile();
	}

	/**
	 * Gets a string representation of the meta data
	 * 
	 * @return the meta data of the file
	 * @throws MediaXException
	 */
	public String viewMetaDataOfSelectedFile() throws MediaXException {
		return this.mediaX.viewMetaDataOfSelectedFile();
	}
}