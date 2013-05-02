package mediax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * An abstract version of a file that MediaX Supports. See java.io.File. Known
 * subclasses are AudioFile.
 * 
 * @author COM S 362 GROUP 7
 * 
 */
abstract class MediaXFile extends java.io.File {

	/**
	 * Needed for proper inheritance from parent class java.io.File
	 */
	private static final long serialVersionUID = 1622605674363717694L;

	/**
	 * Creates a file that is supported by MediaX.
	 * 
	 * @param pathname
	 *            - The physical path to the file.
	 */
	public MediaXFile(final String pathname) {
		super(pathname);
	}

	/**
	 * Deletes this MediaXFile from the FileSystem. Note this function simply
	 * overrides the parent function delete() inherited from File--This was
	 * mainly implemented for readability of the program.
	 * 
	 * @return True if the this MediaXFile was deleted successfully.
	 */
	@Override
	public boolean delete() {
		return super.delete();
	}

	@Override
	public boolean equals(final Object o) {
		if (!o.getClass().equals(this.getClass())) {
			return false;
		}
		if (!((MediaXFile) o).getAbsolutePath().equals(this.getAbsolutePath())) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the specified meta data
	 * 
	 * @param tag
	 *            tag to retrieve
	 * @return String of retrieved data or null if it does not exist
	 * @throws MediaXException
	 */
	public abstract String getMetaData(String tag) throws MediaXException;

	/**
	 * Locks the file from being modified by meta man
	 * 
	 * @return
	 */
	public boolean lock() {
		return this.setWritable(false);
	}

	/**
	 * Opens the file with the operating systems default program
	 * 
	 * @throws IOException
	 */
	public boolean open() {
		if (!this.canWrite()) {
			return false;
		}
		try {
			Runtime.getRuntime().exec(
					"rundll32 SHELL32.DLL,ShellExec_RunDLL " + this);
		} catch (final IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Renames the file File remains in its current directory.
	 * 
	 * @param newName
	 *            Name to give the file
	 * @return true if the file rename was successful
	 */
	public boolean rename(final String newName) {
		return this.renameTo(new File(newName));
	}

	protected abstract boolean renameByMetaData() throws MediaXException;

	public boolean setMetaData(final String tag, final String value)
			throws MediaXException {
		if (!this.canWrite()) {
			return false;
		}
		return this.setMetaDataHelper(tag, value);
	}

	/**
	 * Set meta data
	 * 
	 * @param tag
	 *            tag to set
	 * @param value
	 *            value to set to
	 * @return true if successful
	 * @throws MediaXException
	 */
	protected abstract boolean setMetaDataHelper(String tag, String value)
			throws MediaXException;

	/**
	 * @return
	 * 
	 */
	public boolean unlock() {
		return this.setWritable(true);
	}

	/**
	 * Gets a string view of the files metadata
	 * 
	 * @return string of metadata
	 * @throws MediaXException
	 */
	public abstract String view() throws MediaXException;

	/**
	 * Returns the null tags of the currently selected MediaXFile
	 * 
	 * @return a List MetaDataTags (in string form) which are empty within the
	 *         currently selected file.
	 * @throws MediaXException
	 */
	public abstract ArrayList<String> viewNullTags() throws MediaXException;
}
