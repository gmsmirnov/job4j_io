package ru.gsmirnov.archiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Input params analyzer.
 *
 * @author Gregory Smirnov (gsmirnov <artress@ngs.ru>)
 * @version 0.1
 * @since 2020-04-30
 */
public class ArgZip {
    private static final Logger LOG = LogManager.getLogger(ArgZip.class);

    /**
     * Params quantity constant.
     */
    private static final int PARAMS_QNT = 6;

    /**
     * The directory param key.
     */
    private static final String PARAM_DIRECTORY = "-d";

    /**
     * The exclusion param key.
     */
    private static final String PARAM_EXCLUDE = "-e";

    /**
     * The output param key.
     */
    private static final String PARAM_OUTPUT = "-o";

    /**
     * Valid params flag. False if params quantity or type is not valid.
     */
    private boolean isValidParams;

    /**
     * Program input args.
     */
    private final Map<String, String> params = new HashMap<String, String>();

    /**
     * The constructor. Initializes by program input params.
     *
     * @param args program input params.
     */
    public ArgZip(String[] args) {
        if (this.checkParams(args)) {
            this.isValidParams = true;
            for (int index = 0; index < args.length - 1; index++) {
                if (index % 2 == 0) {
                    this.params.put(args[index], args[index + 1]);
                }
            }
        }
    }

    /**
     * Checks input params. Checks params quantity, checks keys, etc.
     *
     * @param args input params array.
     * @return true if params fine, false either.
     */
    private boolean checkParams(String[] args) {
        boolean result = false;
        if (args.length == ArgZip.PARAMS_QNT) { // checks params qnt
            List<String> params = List.of(args);
            if (params.containsAll(List.of(ArgZip.PARAM_DIRECTORY, ArgZip.PARAM_EXCLUDE, ArgZip.PARAM_OUTPUT))) { // checks expected keys existence
                // todo: write params validator (only expected key with specified params)
                result = true;
            }
        }
        return result;
    }

    /**
     * Checks program input params. If they are valid the result will be true. False either. In fact params check during
     * ArgZip creation and result of this check puts into isValidParam value.
     *
     * @return the result of input params validation.
     */
    public boolean valid() {
        return this.isValidParams;
    }

    /**
     * Gets the directory param value.
     *
     * @return the directory param value.
     */
    public String directory() {
        return this.params.get(ArgZip.PARAM_DIRECTORY);
    }

    /**
     * Gets the exclusion param value.
     *
     * @return the exclusion param value.
     */
    public String exclude() {
        return this.params.get(ArgZip.PARAM_EXCLUDE);
    }

    /**
     * Gets the output param value.
     *
     * @return the output param value.
     */
    public String output() {
        return new StringBuilder(this.params.get(ArgZip.PARAM_DIRECTORY))
                .append("\\")
                .append(this.params.get(ArgZip.PARAM_OUTPUT)).toString();
    }
}