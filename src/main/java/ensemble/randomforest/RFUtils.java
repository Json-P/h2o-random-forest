package ensemble.randomforest;

import java.io.File;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import water.H2OApp;

/**
 * A collection of static helper methods for <code>RandomForestEstimator</code>.
 * 
 * @see ensemble.randomforest.RandomForestEstimator RandomForestEstimator
 * @author JP
 */
public class RFUtils {

	/**
	 * Takes command line arguments and parses them to an instance of
	 * <code>RFArguments</code>.
	 * 
	 * @param args
	 *            <code>String</code> array of arguments from the command line
	 * @return instance of <code>RFArguments</code>
	 */
	public static RFArguments parseRFArguments(String[] args) {
		RFArguments cliArgs = new RFArguments();

		try {
			JCommander jc = new JCommander(cliArgs, args);
			if (cliArgs.help) {
				jc.usage();
				return cliArgs;
			} else if (cliArgs.h2oHelp) {
				// H2O will exit upon displaying usage
				H2OApp.main(new String[] { "-h" });
			}
			
			// Create h2o instance parameters
			cliArgs.h2oParams = "-name DRF "   		// Default name for h2o instance
							  + "-ga-opt_out yes "	// opts out of using Google Analytics embedded in H2O
							  + "-log_dir " + cliArgs.outputDir + File.separatorChar + "log " // log directory
							  + (cliArgs.h2oQuiet ? "-quiet " : "") // Quiet mode for h2o console printing 
							  + cliArgs.h2oParams; 	// Rest of user submitted params
		} catch (ParameterException pe) {
			throw new ParameterException(pe);
		}
		return cliArgs;
	}
}