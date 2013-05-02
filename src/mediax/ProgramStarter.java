package mediax;

import java.io.File;

/**
 * The class starts the program MediaX using a using a view.
 * 
 * @author COM S 362 Group 7
 * 
 */
public class ProgramStarter {

	/**
	 * The current user's home directory
	 */

	private static final String HOME_DIR = System.getProperty("user.home");

	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            - Unused
	 */
	public static void main(final String[] args) {

		try {
			final MediaXCommandLineView cmd = new MediaXCommandLineView(
					new File(ProgramStarter.HOME_DIR));
			cmd.startup();

		} catch (final MediaXException e) {
			System.out.println(e.getMessage());
			System.out.println();
			System.out.println("MediaX must now exit...");
			System.exit(0);
		} catch (final Exception e) {
			System.out.println();
			System.out
					.println("An unknown error has occured, MediaX must now exit...");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
